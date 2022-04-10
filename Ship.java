public class Ship {
  private int[] vars = new int[4];
  private String[] colors;
  private int sunk;
  public Ship(int x, int y, int len, int dir, String[] colors) {
    vars[0] = x;
    vars[1] = y;
    vars[2] = len;
    vars[3] = dir;
    this.colors = colors;
    sunk = len;
  }
  public String[] getColors() {
    return colors;
  }
  public int get(int var) {
    return vars[var];
  }
  public void set(int var, int obj) {
    vars[var] = obj;
  }
  public void set(int i, String color) {
    colors[i] = color;
    sunk -= 1;
  }
  public int in(int[] coords) {
    int x = coords[0];
    int y = coords[1];
    int len = vars[2];
    if (x != vars[0] && y != vars[1]) {
      return -1;
    }
    if (vars[3] == 0 && y == vars[1]) {
      for (int x1 = vars[0]; x1 < vars[0]+len; x1++) {
        if (x == x1) {
          return x1-vars[0];
        }
      }
    }
    else if (vars[3] == 1 && x == vars[0]) {
      for (int y1 = vars[1]; y1 < vars[1]+len; y1++) {
        if (y == y1) {
          return y1-vars[1];
        }
      }
    }
    else if (vars[3] == 2 && y == vars[1]) {
      for (int x1 = vars[0]; x1 > vars[0]-len; x1--) {
        if (x == x1) {
          return vars[0]-x1;
        }
      }
    }
    else if (vars[3] == 3 && x == vars[0]) {
      for (int y1 = vars[1]; y1 > vars[1]-len; y1--) {
        if (y == y1) {
          return vars[1]-y1;
        }
      }
    }
    return -1;
  }
  public int sunk() {
    if (sunk <= 0) {
      return vars[2];
    }
    return -1;
  }
}