

interface Boardgame {
    public boolean move(int x, int y);
    public oldSquare getStatus(int x, int y);
    public String getMessage();
}
