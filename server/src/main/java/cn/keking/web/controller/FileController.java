package cn.keking.web.controller;

import cn.keking.config.ConfigConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.keking.model.ReturnResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author yudian-it
 * @date 2017/12/1
 */
@RestController
@Api(tags = "文件相关内容")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String fileDir = ConfigConstants.getFileDir();
    private final String demoDir = "demo";
    private final String demoPath = demoDir + File.separator;

    @ApiOperation(value = "文件上传", notes = "单文件上传")
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息

        // escaping dangerous characters to prevent XSS
        fileName = HtmlUtils.htmlEscape(fileName, StandardCharsets.UTF_8.name());

        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (Math.max(winSep, unixSep));
        if (pos != -1)  {
            fileName = fileName.substring(pos + 1);
        }
        // 判断是否存在同名文件
        if (existsFile(fileName)) {
            return new ObjectMapper().writeValueAsString(ReturnResponse.failure("存在同名文件，请先删除原有文件再次上传"));
        }
        File outFile = new File(fileDir + demoPath);
        if (!outFile.exists() && !outFile.mkdirs()) {
            logger.error("创建文件夹【{}】失败，请检查目录权限！",fileDir + demoPath);
        }
        logger.info("上传文件：{}", fileDir + demoPath + fileName);
        try(InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(fileDir + demoPath + fileName)) {
            StreamUtils.copy(in, out);
            return new ObjectMapper().writeValueAsString(ReturnResponse.success(null));
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            return new ObjectMapper().writeValueAsString(ReturnResponse.failure());
        }
    }

    @RequestMapping(value = "deleteFile", method = RequestMethod.GET)
    @ApiOperation(value = "文件删除", notes = "删除指定文件")
    public String deleteFile(String fileName) throws JsonProcessingException {
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        File file = new File(fileDir + demoPath + fileName);
        logger.info("删除文件：{}", file.getAbsolutePath());
        if (file.exists() && !file.delete()) {
           logger.error("删除文件【{}】失败，请检查目录权限！",file.getPath());
        }
        return new ObjectMapper().writeValueAsString(ReturnResponse.success());
    }

    @ApiOperation(value = "文件列表", notes = "列出所有文件")
    @RequestMapping(value = "listFiles", method = RequestMethod.GET)
    public String getFiles() throws JsonProcessingException {
        List<Map<String, String>> list = new ArrayList<>();
        File file = new File(fileDir + demoPath);
        if (file.exists()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(file1 -> {
                Map<String, String> fileName = new HashMap<>();
                fileName.put("fileName", demoDir + "/" + file1.getName());
                list.add(fileName);
            });
        }
        return new ObjectMapper().writeValueAsString(list);
    }

    private boolean existsFile(String fileName) {
        File file = new File(fileDir + demoPath + fileName);
        return file.exists();
    }
}
