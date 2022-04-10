public class Board {
  private int length;
  private int height;
  String[][] board;
  public Board() {
    length = 10;
    height = 10;
    board = new String[length][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < length; x++) {
        board[x][y] = "\u0332 ";
      }
    }
  }
  public String[][] getBoard() {
    return board;
  }
  public int getHeight() {
    return height;
  }
  public int getLength() {
    return length;
  }
}