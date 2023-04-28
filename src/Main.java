import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.DefaultTextProducer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static CustomTextRenderer prepareCustomTextRenderer() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(0, 0, 0, 127));
        colors.add(new Color(0, 0, 255, 127));

        String[] boldFontTitles = {
                "Arial",
                "Helvetica",
        };

        List<Font> fonts = new ArrayList<>();
        for (String boldFontTitle : boldFontTitles) {
            fonts.add(new Font(boldFontTitle, Font.BOLD, 50));
        }

        String[] italicFontTitles = {
                "Geneva"
        };

        for (String italicFontTitle : italicFontTitles) {
            fonts.add(new Font(italicFontTitle, Font.ITALIC, 50));
        }

        return new CustomTextRenderer(colors, fonts);
    }

    public static Captcha createCaptcha() {
        return new Captcha.Builder(250, 75)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(), prepareCustomTextRenderer())
                .addNoise()
                .build();
    }

    public static void saveCaptcha(Captcha captcha, String filePath) {
        try {
            BufferedImage bi = captcha.getImage();
            File outputfile = new File(filePath + captcha.getAnswer() + ".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String savingPath = "captchas/";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter count of captchas: ");
        int captchasCount = sc.nextInt();
        for (int i = 0; i < captchasCount; i++) {
            saveCaptcha(createCaptcha(), savingPath);
        }
    }
}