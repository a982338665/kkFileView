package cn.keking.service;

import cn.keking.model.FileAttribute;
import cn.keking.web.controller.OnlinePreviewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
@Service
public class FilePreviewFactory {

    private final ApplicationContext context;

    private final Logger logger = LoggerFactory.getLogger(OnlinePreviewController.class);

    public FilePreviewFactory(ApplicationContext context) {
        this.context = context;
    }

    public FilePreview get(FileAttribute fileAttribute) {
        Map<String, FilePreview> filePreviewMap = context.getBeansOfType(FilePreview.class);
        FilePreview filePreview = filePreviewMap.get(fileAttribute.getType().getInstanceName());
        logger.info("根据类型找到具体实现类：{}----{}", fileAttribute.getType().getInstanceName(), filePreview.getClass().getName());
        return filePreview;

    }
}
