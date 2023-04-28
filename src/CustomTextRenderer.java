import nl.captcha.text.renderer.WordRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomTextRenderer implements WordRenderer {
    private static final Random RAND = new SecureRandom();
    private static final java.util.List<Color> DEFAULT_COLORS = new ArrayList<>();
    private static final List<Font> DEFAULT_FONTS = new ArrayList<>();
    private final List<Font> _fonts;
    private final List<Color> _colors;


    static {
        DEFAULT_FONTS.add(new Font("Arial", Font.PLAIN, 50));
        DEFAULT_COLORS.add(Color.BLACK);
    }

    public CustomTextRenderer() {
        this(DEFAULT_COLORS, DEFAULT_FONTS);
    }

    public CustomTextRenderer(List<Color> colors, List<Font> fonts) {
        this._colors = colors != null ? colors : DEFAULT_COLORS;
        this._fonts = fonts != null ? fonts : DEFAULT_FONTS;
    }

    public void render(String word, BufferedImage image) {
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        FontRenderContext frc = g2.getFontRenderContext();
        Font f = getRandomFont();
        TextLayout textTl = new TextLayout(word, f, frc);
        int xBaseline = (int) Math.round((double) image.getWidth() * 0.05);
        int yBaseline = image.getHeight() - (int) Math.round((double) image.getHeight() * 0.25);
        Shape outline = textTl.getOutline(AffineTransform.getTranslateInstance(xBaseline, yBaseline));

        g2.setColor(getRandomColor());
        g2.draw(outline);
    }

    private Color getRandomColor() {
        return (Color) this.getRandomObject(this._colors);
    }

    private Font getRandomFont() {
        return (Font) this.getRandomObject(this._fonts);
    }

    private Object getRandomObject(List<?> objs) {
        if (objs.size() == 1) {
            return objs.get(0);
        } else {
            int i = RAND.nextInt(objs.size());
            return objs.get(i);
        }
    }
}
