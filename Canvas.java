import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Map;
import java.util.LinkedHashMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas. This is
 * a modification of the general purpose Canvas, specially made for the BlueJ
 * "shapes" example.
 *
 * @author: Bruce Quig Michael Kölling Brian Dahlem
 * @version 2018.11.26
 */
public class Canvas {
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     *
     * @return a reference to the applications canvas
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Picture Demo", 800, 600, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----
    private int width;
    private int height;
    private BufferStrategy bs;
    private Color backgroundColor;
    private Image canvasImage;
    private final Map<Object, DrawShape> shapes;
    private String title;
    private boolean paused = false;
    private boolean firstShown = false;
    private boolean drawing = false;

    /**
     * Create a Canvas.
     *
     * @param title title to appear in Canvas Frame
     * @param width the desired width for the canvas
     * @param height the desired height for the canvas
     * @param bgColor the desired background color of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColor) {
        this.title = title;
        this.width = width;
        this.height = height;

        this.backgroundColor = bgColor;

        shapes = new LinkedHashMap<>(1000);
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen when
     * made visible. This method can also be used to bring an already visible
     * canvas to the front of other windows.
     *
     * @param visible boolean value representing the desired visibility of the
     * canvas (true or false)
     */
    public void setVisible(boolean visible) {
        return; // do nothing in headless mode
    }

    /**
     * Determine the width of the canvas
     */
    public int getWidth() {
        return width;
    }

    /**
     * Determine the height of the canvas
     */
    public int getHeight() {
        return height;
    }

    /**
     * Pause automatic redraws
     */
    public void pause(boolean pause) {
        this.paused = pause;
    }

    /**
     * Are automatic redraws paused
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Draw a given shape onto the canvas.
     *
     * @param referenceObject an object to define identity for this shape
     * @param shapeFunction a function that draws the shape on a graphics
     * context
     */
    public void add(Object referenceObject, DrawShape shapeFunction) {
        synchronized (shapes) {
            if (shapes.containsKey(referenceObject)) {
                throw new IllegalArgumentException("Shape already added to canvas");
            }

            shapes.put(referenceObject, shapeFunction);
        }

        if (!paused) {
            redraw();
        }
    }

    /**
     * Erase a given shape's from the screen.
     *
     * @param referenceObject the shape object to be erased
     */
    public void remove(Object referenceObject) {

        synchronized (shapes) {
            if (!shapes.containsKey(referenceObject)) {
                throw new IllegalArgumentException("Shape not added to canvas");
            }

            shapes.remove(referenceObject);
        }

        if (!paused) {
            redraw();
        }
    }

    /**
     * Change the name of this canvas
     *
     * @param title the new name for the canvas
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the canvas's background color
     *
     * @param bgColor the new background color for the canvas.
     */
    public void setBackgroundColor(Color bgColor) {
        this.backgroundColor = bgColor;
    }

    /**
     * Set the canvas's background color
     *
     * @param bgColor the new background color for the canvas.
     */
    public void setBackgroundColor(String bgColor) {
        setBackgroundColor(getColor(bgColor));
    }

    /**
     * Wait for a specified number of milliseconds before finishing. This
     * provides an easy way to specify a small delay which can be used when
     * producing animations.
     *
     * @param milliseconds the number
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // ignoring exceptions at the moment
        }
    }

    /**
     * Redraw all shapes currently on the Canvas.
     */
    public void redraw() {
        return; // do nothing in headless mode
    }

    /**
     * Redraw all shapes onto a graphics context
     */
    private void redraw(Graphics buffer) {
        synchronized (shapes) {
            buffer.setColor(backgroundColor);
            buffer.fillRect(0, 0, this.width, this.height);

            shapes.forEach((i, shape) -> {
                    shape.draw(buffer);
                });
        }
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase() {
        return; // do nothing in headless mode
    }

    /**
     * Transform a color string into a usable color
     *
     * @param colorString the new color for the foreground of the Canvas
     */
    public static Color getColor(String colorString) {
        Color c;

        if (colorString.equals("red")) {
            c = new Color(235, 25, 25);
        } else if (colorString.equals("black")) {
            c = Color.black;
        } else if (colorString.equals("blue")) {
            c = new Color(30, 75, 220);
        } else if (colorString.equals("cyan")) {
            c = new Color(30, 229, 220);
        } else if (colorString.equals("brown")) {
            c = new Color(110, 80, 0);
        } else if (colorString.equals("yellow")) {
            c = new Color(255, 230, 0);
        } else if (colorString.equals("green")) {
            c = new Color(80, 160, 60);
        } else if (colorString.equals("magenta")) {
            c = Color.magenta;
        } else if (colorString.equals("white")) {
            c = Color.white;
        } else if (colorString.startsWith("#") && colorString.length() == 7) {
            int red = Integer.parseInt(colorString.substring(1, 3), 16);
            int green = Integer.parseInt(colorString.substring(3, 5), 16);
            int blue = Integer.parseInt(colorString.substring(5, 7), 16);
            c = new Color(red, green, blue);
        } else {
            c = Color.black;
        }

        return c;
    }

    /**
     * Save the current canvas to the file
     * 
     * @param file the File object to save to.
     * 
     * @return true if the file saved correctly, false if the save failed.
     */
    public void saveToFile(File file) throws IOException {
        Font font = new Font("SansSerif", Font.PLAIN, 20);

        try {
            InputStream fnt_stream = getClass().getResourceAsStream("Caveat.ttf");
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, fnt_stream);
            font = myFont.deriveFont(Font.BOLD, 20f);
        } catch (FontFormatException | IOException ex) {

        }

        BufferedImage buffer = new BufferedImage(1,1,
            BufferedImage.TYPE_INT_RGB);
        Graphics bgc = buffer.getGraphics();

        FontMetrics fm = bgc.getFontMetrics(font);
        int fontheight = fm.getHeight();

        // Create a buffered image from the picture
        buffer = new BufferedImage(width, height + fontheight + 2,
                BufferedImage.TYPE_INT_RGB);
        bgc = buffer.createGraphics();
        bgc.setColor(Color.white);
        bgc.fillRect(0, 0, width, height + fontheight + 2);

        redraw(bgc);

        bgc.setColor(Color.black);
        bgc.setFont(font);
        bgc.drawString(this.title, 0, height + fm.getAscent() + 1);

        // Save the image
        ImageIO.write(buffer, "png", file);
    }

    /**
     * ***********************************************************************
     * Inner interface DrawShape - a functional interface that allows a shape to
     * provide the canvas with a method to draw the shape
     */
    public interface DrawShape {
        public void draw(Graphics g);
    }
}
