

interface Boardgame {
    public int move(int x, int y);
    public gameSquare getStatus(int x, int y);
    public String getMessage();
}
