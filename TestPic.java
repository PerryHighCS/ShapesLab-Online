import java.io.File;
import java.io.IOException;

public class TestPic
{
    public static void main(String[] args)
    {
        // Get a reference to the canvas for this drawing and set its title.
        Canvas pic = Canvas.getCanvas();
        pic.setTitle("Shapes Constructor Test Picture");
        
        // Draw my picture
        Rect r = new Rect();
        r.makeVisible();

        // Save the picture to a file
        String filename = "testpic.png";
        try {
            pic.saveToFile(new File(filename));
            System.out.println("Picture saved to " + filename);
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Could not save file.");
        }
    }
}