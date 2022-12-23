

public class GUI {

    private static int size = 8;
    private static Chess game = new Chess();

    public static void main(String[] args) {
        new ViewControl(game, size);
    }
    
}
