import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
public class Battleship { 
  public static void main(String[] args) {
    clear();
    ArrayList<int[]> next = new ArrayList<int[]>();
    Scanner in = new Scanner(System.in);
    String[] yourUnguessed = new String[100];
    String letters = "ABCDEFGHIJ";
    for (int i = 0; i < 10; i++) {
      for (int x = 1; x < 11; x++) {
        yourUnguessed[i*10+x-1] = letters.charAt(i)+Integer.toString(x);
      }
    }
    String[] oppUnguessed = yourUnguessed.clone();
    BattleshipBoard yourBoard = new BattleshipBoard();
    Ship[] yourShips = new Ship[5];
    Ship[] oppShips = new Ship[5];
    int[] lens = {2, 3, 3, 4, 5};
    for (int i = 0; i < 5; i++) {
      yourBoard.print();
      yourShips[i] = startShip(lens[i], yourBoard, true, in, yourUnguessed);
      yourBoard.setShip(yourShips[i]);
      clear();
    }
    yourBoard.print();
    System.out.println("This is your board for the game. Press the enter key to start");
    BattleshipBoard oppBoard = new BattleshipBoard();
    for (int i = 0; i < 5; i++) {
      oppShips[i] = startShip(lens[i], oppBoard, false, in, oppUnguessed);
      oppBoard.setShip(oppShips[i]);
    }
    BattleshipBoard yourGuesses = new BattleshipBoard();
    BattleshipBoard oppGuesses = new BattleshipBoard();
    in.nextLine();
    clear();
    String guess = "";
    int[] coords = {-1, -1};
    int index = -1;
    int len = 0;
    boolean clear = true;
    int done = 0;
    boolean cont = true;
    boolean hit = false;
    int hits = 0;
    int dir = -1;
    int[] prevHit = {-1, -1};
    int ind = -1;
    int order = -1;
    while (true) {
      //Player Guesses
      yourGuesses.print();
      guess = input("What is your guess?\n", in, yourUnguessed, yourGuesses);
      coords = getCoords(guess);
      yourUnguessed[getIndex(guess, yourUnguessed)] = null;
      if (!oppBoard.getLocation(coords).equals("\u0332 ")) {
        for (Ship i : oppShips) {
          try {
            index = i.in(coords);
          }
          catch (Exception e) {
            continue;
          }
          if (index != -1) {
            i.set(index, BattleshipBoard.getRed());
            len = i.sunk();
            yourGuesses.setLocation(coords, BattleshipBoard.getRed()+"X");
            hit = true;
            if (len != -1) {
              hit = false;
              clear();
              i = null;
              clear = false;
            }
            break;
          }
        }
      }
      else {
        yourGuesses.setLocation(coords, "O");
      }
      clear();
      yourGuesses.print();
      System.out.print("You fired at "+guess.toUpperCase()+".");
      if (!clear) {
        System.out.println("\nThe ship has sunk. It was "+len+" long.");
        clear = true;
      }
      else if (hit) {
        System.out.println(" One of the computer's ships was HIT!");
        hit = false;
      }
      else {
        System.out.println(" You did not hit the computer's ships.");
      }
      System.out.println("Press enter to continue");
      in.nextLine();
      clear();

      //Checks for game end
      for (int i = 0; i < 5; i++) {
        if (yourShips[i].sunk() == -1) {
          break;
        }
        else if (i == 4) {
          done = 2;
        }
      }
      if (done == 0) {
        for (int i = 0; i < 5; i++) {
          if (oppShips[i].sunk() == -1) {
            break;
          }
          else if (i == 4) {
            done = 1;
          }
        }
      }
      if (done != 0) {
        clear();
        break;
      }
      
      //Computer Guesses
      while (true) {
        if (next.size() == 0) {
          int a = (int)(Math.random()*10);
          int b = (int)(Math.random()*10);
          if ((a+b)%2==0) {
            continue;
          }
          guess = Character.toString(letters.charAt(a))+Integer.toString(b+1);
        }
        else {
          for (int i = 0; i < next.size(); i++) {
            ind = i;
            if (next.get(i)[0] == prevHit[0] && dir == 0) {
              guess = letters.substring(next.get(i)[1], next.get(i)[1]+1)+Integer.toString(next.get(i)[0]+1);
              break;
            }
            else if (next.get(i)[1] == prevHit[1] && dir == 1) {
              guess = letters.substring(next.get(i)[1], next.get(i)[1]+1)+Integer.toString(next.get(i)[0]+1);
              break;
            }
            else {
              try {
                guess = letters.substring(next.get(i)[1], next.get(i)[1]+1)+Integer.toString(next.get(i)[0]+1);
              }
              catch (Exception e) {
                System.out.println(Arrays.toString(next.get(i)));
                System.exit(0);
              }
            }
          }
          next.remove(ind);
        }
        for (String g : oppUnguessed) {
          if (g == null) {
            continue;
          }
          else if (guess.equalsIgnoreCase(g)) {
            cont = false;
            break;
          }
        }
        if (cont) {
          continue;
        }
        else {
          cont = true;
        }
        break;
      }
      coords = getCoords(guess);
      oppUnguessed[getIndex(guess, oppUnguessed)] = null;
      if (!yourBoard.getLocation(coords).equals("\u0332 ")) {
        for (Ship i : yourShips) {
          try {
            index = i.in(coords);
          }
          catch (Exception e) {
            continue;
          }
          if (index != -1) {
            i.set(index, BattleshipBoard.getRed());
            len = i.sunk();
            oppGuesses.setLocation(coords, BattleshipBoard.getRed()+"X");
            hit = true;
            if (len != -1) {
              clear();
              clear = false;
              i = null;
            }
            break;
          }
        }
      }
      else {
        oppGuesses.setLocation(coords, "O");
      }
      oppGuesses.print();
      System.out.print("The computer fired at "+guess+".");
      if (!clear) {
        System.out.println("\nYour ship has sunk. It was "+len+" long.");
        clear = true;
        if (len == hits+1) {
          next.clear();
          hits = 0;
        }
        else {
          hits -= len;
        }
      }
      else if (hit) {
        System.out.println(" One of your ships was HIT!");
        hit = false;
        hits += 1;
        if (coords[0] == prevHit[0]) {
          dir = 0;
        }
        else if (coords[1] == prevHit[1]){
          dir = 1;
        }
        else {
          dir = -1;
        }
        order = (int)(Math.random()*2);
        if (order == 0) {
          if (coords[1]+1 <= 9) {
            next.add(new int[] {coords[0], coords[1]+1});
          }
          if (coords[1]-1 >= 0) {
            next.add(new int[] {coords[0], coords[1]-1});
          }
          if (coords[0]+1 <= 9) {
            next.add(new int[] {coords[0]+1, coords[1]});
          }
          if (coords[0]-1 >= 0) {
            next.add(new int[] {coords[0]-1, coords[1]});
          }
        } else if (order == 1) {
          if (coords[0]+1 <= 9) {
            next.add(new int[] {coords[0]+1, coords[1]});
          }
          if (coords[0]-1 >= 0) {
            next.add(new int[] {coords[0]-1, coords[1]});
          }
          if (coords[1]+1 <= 9) {
            next.add(new int[] {coords[0], coords[1]+1});
          }
          if (coords[1]-1 >= 0) {
            next.add(new int[] {coords[0], coords[1]-1});
          }
        }
        prevHit = new int[] {coords[0], coords[1]};
      }
      else {
        System.out.println(" They did not hit your ships.");
      }
      System.out.println("Press enter to start your turn");
      in.nextLine();

      //Checks for game end
      for (int i = 0; i < 5; i++) {
        if (yourShips[i].sunk() == -1) {
          break;
        }
        else if (i == 4) {
          done = 2;
        }
      }
      if (done == 0) {
        for (int i = 0; i < 5; i++) {
          if (oppShips[i].sunk() == -1) {
            break;
          }
          else if (i == 4) {
            done = 1;
          }
        }
      }
      if (done != 0) {
        clear();
        break;
      }

      //Checks to clear screen
      if (clear) {
        clear();
      }
      else {
        clear = true;
      }
    }
    if (done == 1) {
      System.out.println("You Won!");
    }
    else {
      System.out.println(BattleshipBoard.getRed()+"The Computer Won...");
    }
  }
  public static int getIndex(String s, String[] a) {
    for (int i = 0; i < a.length; i++) {
      if (s.equalsIgnoreCase(a[i])) {
        return i;
      }
    }
    return -1;
  }
  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static String input(String prompt, Scanner in, Object[] options, BattleshipBoard board) {
    String var;
    while (true) {
      System.out.print(prompt);
      var = in.nextLine();
      for (Object i : options) {
        if (i == null) {
          continue;
        }
        if (var.equalsIgnoreCase(i.toString())) {
          return var;
        }
      }
      clear();
      System.out.println("'"+var+"' is not a valid input.");
      board.print();
    }
  }
  public static int[] getCoords(String l) {
    int[] coords = new int[2];
    String letters = "ABCDEFGHIJabcdefghij";
    coords[0] = Integer.valueOf(l.substring(1))-1;
    coords[1] = letters.indexOf(l.charAt(0))%10;
    return coords;
  }
  public static Ship startShip(int len, BattleshipBoard board, boolean player, Scanner in, String[] unGuessed) {
    if (player) {
      int[] location;
      int direction;
      while (true) {
        location = getCoords(input("What location do you want to place your length "+len+" ship?\n", in, unGuessed, board));
        clear();
        board.print();
        direction = Integer.valueOf(input("What direction do you want to place this ship?\n0 for right, 1 for down, 2 for left, 3 for up\n", in, new Object[] {0, 1, 2, 3}, board));
        if (board.checkCollision(location[0], location[1], len, direction).equals("none")) {
          break;
        }
        else {
          clear();
          System.out.println("That is not a valid location or direction for your ship.");
          board.print();
        }
      }
      String[] emptyString = new String[len];
      for (int i = 0; i < len; i++) {
        emptyString[i] = "";
      }
      Ship ship = new Ship(location[0], location[1], len, direction, emptyString);
      board.setShip(ship);
      return ship;
    }
    else {
      int[] location = new int[2];
      int direction;
      while (true) {
        location[0] = (int)(Math.random()*10);
        location[1] = (int)(Math.random()*10);
        direction = (int)(Math.random()*4);
        if (board.checkCollision(location[0], location[1], len, direction).equals("none")) {
          break;
        }
      }
      String[] emptyString = new String[len];
      for (int i = 0; i < len; i++) {
        emptyString[i] = "";
      }
      Ship ship = new Ship(location[0], location[1], len, direction, emptyString);
      board.setShip(ship);
      return ship;
    }
  }
}