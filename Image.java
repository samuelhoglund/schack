
import javax.swing.ImageIcon;
import javax.swing.Icon;

public class Image {
    // gör så att alla pjäser har en egen bild
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\proj\\\\images";

    
    public Icon generateImage(String im) {
        Icon image = new ImageIcon(dir + im + ".png");
        return image;
    }
    


    public static void main( String[] args) {
        String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\proj\\\\images";
        System.out.println(dir);
    }
}
