import java.awt.Graphics;
import java.awt.Color;

/**
 * An arc that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kölling and David J. Barnes and Brian Dahlem
 * @version 2018.11.26
 */

public class Arc
{
    private int diameter;
    private int xPosition;
    private int yPosition;
    private int startAngle;
    private int extent;
    private Color color;
    private boolean isVisible;
    
    /**
     * Create a new arc at default position with default color.
     */
    public Arc()
    {
        diameter = 68;
        xPosition = 130;
        yPosition = 75;
        startAngle = 30;
        extent = 120;
        color = Canvas.getColor("magenta");
    }
    
    /**
     * Create an arc at a given position with specified color and shape
     * @param x the x location of the Arc
     * @param y the y location of the Arc
     * @param diameter the diameter of the Arc
     * @param arcStartAngle the angle the arc sweep starts at
     * @param arcEndAngle the angle the arc sweep ends at
     * @param color the name of the color for the Arc
     * @param visible true displays the Arc on the canvas
     */
    public Arc(int x, int y, int diameter, int arcStartAngle, int arcEndAngle, 
                String color, boolean visible){
        xPosition = x;
        yPosition = y;
        
        this.diameter = diameter;
        this.startAngle = arcStartAngle;
        
        extent = arcEndAngle - arcStartAngle;
        extent %= 360;
        if (extent < 0) {
            extent += 360;
        }
        
        this.color = Canvas.getColor(color);
        
        if (visible) {
            makeVisible();
        }
    }
    
    /**
     * Make this arc visible. If it was already visible, do nothing.
     */
    public void makeVisible()
    {
        if (!isVisible) {
            isVisible = true;
            add();
        }
    }
    
    /**
     * Make this arc invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible()
    {
        if (isVisible){
            remove();
            isVisible = false;
        }
    }
    
    /**
     * Determine if the arc should be showing on the canvas
     * @return true if the shape is not hidden
     */
    public boolean isVisible()
    {
        return isVisible;
    }
    
    /**
     * Move the arc a few pixels to the right.
     */
    public void moveRight()
    {
        moveHorizontal(20);
    }

    /**
     * Move the arc a few pixels to the left.
     */
    public void moveLeft()
    {
        moveHorizontal(-20);
    }

    /**
     * Move the arc a few pixels up.
     */
    public void moveUp()
    {
        moveVertical(-20);
    }

    /**
     * Move the arc a few pixels down.
     */
    public void moveDown()
    {
        moveVertical(20);
    }

    /**
     * Move the arc horizontally by 'distance' pixels.
     * @param distance the distance to move along the x axis,
     *                  positive to the right
     */
    public void moveHorizontal(int distance)
    {
        xPosition += distance;
    }

    /**
     * Move the arc vertically by 'distance' pixels.
     * @param distance the distance to move along the y axis, positive down
     */
    public void moveVertical(int distance)
    {
        yPosition += distance;
    }

    /**
     * Move the arc horizontally to a given X location
     * @param xPosition the new X location to move the arc to
     */
    public void setX(int xPosition)
    {
        this.xPosition = xPosition;
    }
    
    /**
     * Move the arc vertically to a given Y location
     * @param yPosition the new Y location to move the arc to
     */
    public void setY(int yPosition)
    {
        this.yPosition = yPosition;
    }
    
    /**
     * Move the arc to a given (X, Y) coordinate
     * @param x the new X location to move to
     * @param y the new Y location to move to
     */
    public void setPosition(int x, int y)
    {
        xPosition = x;
        yPosition = y;
    }
    
    /**
     * Determine the current X location of the arc
     * @return the current X location of the arc
     */
    public int getX()
    {
        return xPosition;
    }
    
    /**
     * Determine the current Y location of the arc
     * @return the current Y location of the arc
     */
    public int getY()
    {
        return xPosition;
    }
    
    /**
     * Open the arc a bit.
     */
    public void openArc()
    {
        startAngle += 10;
        if (startAngle >= 180) {
            startAngle = 180;
        }
        
        extent = 360 - (2 * startAngle);
    }

    
    /**
     * Close the arc a bit.
     */
    public void closeArc()
    {
        startAngle -= 10;
        if (startAngle < 0) {
            startAngle = 0;
        }
        
        extent = 360 - (2 * startAngle);
    }
    
    /**
     * Change the beginning angle of the arc
     * @param angle the angle at which the arc should start, 0&deg; to the 
     *              right, increasing counterclockwise
     */
    public void changeArcBeginning(int angle)
    {
         startAngle = angle;
    }
    
    /**
     * Change the ending angle of the arc
     * @param angle the angle at which the arc should end, 0&deg; to the right,
     *              increasing counterclockwise
     */
    public void changeArcEnd(int angle)
    {
        extent = angle - startAngle;
        extent %= 360;
        if (extent < 0) {
            extent += 360;
        }
    }
        
    /**
     * Determine the angle at which the arc starts
     * @return the angle (in degrees) of the beginning of the arc, 0&deg; to
     *         the right, increasing counterclockwise
     */
    public int getArcBeginning()
    {
        return startAngle;
    }
    
    /**
     * Determine the angle at which the arc ends
     * @return the angle (in degrees) of the end of the arc, 0&deg; to the
     *         right, increasing counterclockwise
     */
    public int getArcEnd()
    {
        return startAngle + extent;
    }
    
    /**
     * Determine the angle enclosed by the arc
     * @return the angle (in degrees) the arc traces counterclockwise
     */
    public int getArcLength()
    {
        return extent;
    }
    
    /**
     * Change the size of the arc to the new size (in pixels). Size must be &gt;= 0.
     * @param newDiameter the new diameter for the arc
     */
    public void changeSize(int newDiameter)
    {
        diameter = newDiameter;
    }

    /**
     * Determine the current diameter of the arc
     * @return the current diameter of the arc
     */
    public int getDiameter()
    {
        return diameter;
    }
    
    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta", "cyan", "brown", "white", and "black", or rgb hex strings
     * "#rrggbb" where rr, gg, bb are 2-hexit values for red, green, and
     * blue levels.
     * @param newColor a lower case string naming the color for the arc
     */
    public void changeColor(String newColor)
    {
        color = Canvas.getColor(newColor);
    }

    /**
     * Draw an arc with current specifications on the screen.
     */
    private void add()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.add(this, (g) -> {g.setColor(color);
                                     g.fillArc(xPosition, yPosition,
                                                diameter, diameter,
                                                startAngle, extent);});
        }
    }

    /**
     * Remove the arc from the screen.
     */
    private void remove()
    {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.remove(this);
        }
    }

    /**
     * Get a text description of the arc.
     */
    public String toString() {
        String visibility;
        if (isVisible) {
            visibility = "Visible";
        }
        else {
            visibility = "Invisible";
        }

        return visibility + " Arc of diameter " + this.diameter + 
            " at (" + this.xPosition + ", " + this.yPosition + ")" +
            " starting at " + this.startAngle + "degrees, " +
            this.extent + " degrees wide," +
            " with color " + this.color;
    }
}
