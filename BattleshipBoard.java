public class BattleshipBoard extends Board {
  private static final String reset = "\u001B[0m";
  private static final String red = "\u001B[31m";
  public BattleshipBoard() {
    super();
  }
  public static String getReset() {
    return reset;
  }
  public static String getRed() {
    return red;
  }
  public void print() {
    String chars = "ABCDEFGHIJ";
    System.out.println("    1 2 3 4 5 6 7 8 9 10");
    System.out.print("   ");
    for (int i = 0; i < super.getLength(); i++) {
      System.out.print(" \u0332 ");
    }
    System.out.println("");
    for (int y = 0; y < super.getHeight(); y++) {
      System.out.print(chars.charAt(y)+"  ");
      for (int x = 0; x < super.getLength(); x++) {
        System.out.print("|"+super.getBoard()[x][y]);
      }
      System.out.println("|");
    }
  }
  public void setShip(Ship ship) {
    int x, y, len, dir;
    String[] colors;
    x = ship.get(0);
    y = ship.get(1);
    len = ship.get(2);
    dir = ship.get(3);
    colors = ship.getColors();
    /*
    0 is right
    1 is down
    2 is left
    3 is up
    */
    if (dir == 0) {
      for (int i = x; i < x+len; i++) {
        super.getBoard()[i][y] = colors[i-x]+"\u0332"+len+reset;
      }
    }
    else if (dir == 1) {
      for (int i = y; i < y+len; i++) {
        super.getBoard()[x][i] = colors[i-y]+"\u0332"+len+reset;
      }
    }
    else if (dir == 2) {
      for (int i = x; i > x-len; i--) {
        super.getBoard()[i][y] = colors[x-i]+"\u0332"+len+reset;
      }
    }
    else {
      for (int i = y; i > y-len; i--) {
        super.getBoard()[x][i] = colors[y-i]+"\u0332"+len+reset;
      }
    }
  }
  public String checkCollision(int x, int y, int len, int dir) {
    String ret = "none";
    try {
      if (dir == 0) {
        for (int i = x; i < x+len; i++) {
          if (!super.getBoard()[i][y].equals("\u0332 ")) {
            ret = "ship";
            break;
          }
        }
      }
      else if (dir == 1) {
        for (int i = y; i < y+len; i++) {
          if (!super.getBoard()[x][i].equals("\u0332 ")) {
            ret = "ship";
            break;
          }
        }
      }
      else if (dir == 2) {
        for (int i = x; i > x-len; i--) {
          if (!super.getBoard()[i][y].equals("\u0332 ")) {
            ret = "ship";
            break;
          }
        }
      }
      else {
        for (int i = y; i > y-len; i--) {
          if (!super.getBoard()[x][i].equals("\u0332 ")) {
            ret = "ship";
            break;
          }
        }
      }
    }
    catch (Exception e) {
      return "bounds";
    }
    return ret;
  }
  public void setLocation(int[] coords, String attack) {
    super.getBoard()[coords[0]][coords[1]] = "\u0332"+attack+reset;
  }
  public String getLocation(int[] coords) {
    return super.getBoard()[coords[0]][coords[1]];
  }
}