import java.awt.Graphics;
import java.awt.Color;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kölling and David J. Barnes and Brian Dahlem
 * @version 2018.11.26
 */

public class Circle
{
    private int diameter;
    private int xPosition;
    private int yPosition;
    private Color color;
    private boolean isVisible;
        
    /**
     * Create a new circle at default position with default color.
     */
    public Circle()
    {
        diameter = 68;
        xPosition = 230;
        yPosition = 90;
        color = Canvas.getColor("blue");
    }    
    
    /**
     * Make this circle visible. If it was already visible, do nothing.
     */
    public void makeVisible()
    {
        if (!isVisible) {
            isVisible = true;
            add();
        }
    }
    
    /**
     * Make this circle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible()
    {
        if (isVisible){
            remove();
            isVisible = false;
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight()
    {
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft()
    {
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp()
    {
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown()
    {
        moveVertical(20);
    }

    /**
     * Move the circle horizontally by 'distance' pixels.
     * @param distance the distance to move the circle along the x axis,
     *                  positive to the right
     */
    public void moveHorizontal(int distance)
    {
        xPosition += distance;
    }

    /**
     * Move the circle vertically by 'distance' pixels.
     * @param distance the distance to move the circle along the y axis,
     *                  positive down
     */
    public void moveVertical(int distance)
    {
        yPosition += distance;
    }

    /**
     * Change the size to the new size (in pixels). Size must be &gt;= 0.
     * @param newDiameter the diameter of the circle
     */
    public void changeSize(int newDiameter)
    {
        diameter = newDiameter;
    }
    
    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta", "cyan", "brown", "white", and "black", or rgb hex strings
     * "#rrggbb" where rr, gg, bb are 2-hexit values for red, green, and
     * blue levels.
     * @param newColor a lower case string naming the color to change to
     */
    public void changeColor(String newColor)
    {
        color = Canvas.getColor(newColor);
    }

    /**
     * Add the circle to the screen.
     */
    private void add()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.add(this,(g) -> {g.setColor(color);
                                    g.fillOval(xPosition, yPosition, diameter, diameter);});
        }
    }

    /**
     * Remove the circle from the screen.
     */
    private void remove()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.remove(this);
        }
    }

    /**
     * Get a text description of the circle.
     */
    public String toString() {
        String visibility;
        if (isVisible) {
            visibility = "Visible";
        }
        else {
            visibility = "Invisible";
        }

        return visibility + " Circle of diameter " + this.diameter + 
            " at (" + this.xPosition + ", " + this.yPosition + ")" +
            " with color " + this.color;
    }
}
