

public class Color {
    boolean black;

    Color(String color) {
        if (color.toLowerCase().equals("white")) {
            this.black = false;
        }
        else if (color.toLowerCase().equals("black")) {
            this.black = true;
        }
    }
}
