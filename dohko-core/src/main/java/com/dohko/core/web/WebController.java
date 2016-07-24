package com.dohko.core.web;

import com.dohko.core.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by xiangbin on 2016/7/5.
 */
@Controller
public class WebController extends BaseController {

    @Autowired
    private WebConfig webConfig;

    /**
     * 定义欢迎页面
     * @return
     */
    @RequestMapping("/")
    public String welcome() {
        return webConfig.getWelcome();
    }
    /**
     * 定义验证码
     * @param req
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/randomImg")
    public void randomImg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int iWidth = webConfig.getImgWidth(), iHeight = webConfig.getImgHeight();
        BufferedImage image = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, iWidth, iHeight);
        g.setColor(Color.black);
        g.drawRect(0, 0, iWidth - 1, iHeight - 1);
        String randomStr = String.valueOf(Math.random());
        String rand = randomStr.substring(randomStr.indexOf(".") + 1, randomStr.indexOf(".") + 1 + webConfig.getCodeSize());
        //将认证码存入SESSION
        req.getSession().setAttribute("Rand", rand);
        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.drawString(rand, 5, 15);
        Random random = new Random();
        for (int iIndex = 0; iIndex < webConfig.getImgFill(); iIndex++) {
            int x = random.nextInt(iWidth);
            int y = random.nextInt(iHeight);
            g.drawLine(x, y, x, y);
        }
        g.dispose();
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(image, "jpeg", sos);
        sos.close();
    }

    public WebConfig getWebConfig() {
        return webConfig;
    }

    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }
}
