

interface Boardgame {
    public int move(int x, int y);
    public oldSquare getStatus(int x, int y);
    public String getMessage();
}
