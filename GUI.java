

public class GUI {

    private static final int size = 8;
    private static Chess game = new Chess();

    public static void main(String[] args) {
        new ViewControl(game, size);
    }
}
