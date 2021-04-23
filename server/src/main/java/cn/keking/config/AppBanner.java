package cn.keking.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/2/8
 */
public class AppBanner implements Banner {
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(
                "*********************************************\n" +
                 "                       _ooOoo_\n" +
                 "                      o8888888o\n" +
                 "                      88\" . \"88\n" +
                 "                      (| -_- |)\n" +
                 "                      O\\  =  /O\n" +
                 "                   ____/`---'\\____ \n" +
                 "                 .'  \\\\|     |//  `.\n" +
                 "                /  \\\\|||  :  |||//  \\\n" +
                 "               /  _||||| -:- |||||-  \\\n" +
                 "               |   | \\\\\\  -  /// |   |\n" +
                 "               | \\_|  ''\\---/''  |   |\n" +
                 "               \\  .-\\__  `-`  ___/-. /\n" +
                 "             ___`. .'  /--.--\\  `. . __\n" +
                 "          .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
                 "         | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                 "         \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
                 "    ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                 "                       `=---='\n" +
                 "    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                 "               佛祖保佑       永无BUG");
    }
}
