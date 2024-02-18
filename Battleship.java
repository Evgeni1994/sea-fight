import java.io.IOException;
import java.util.Scanner;

public class Battleship {
    static String playerName1;
    static String playerName2;
    static Scanner scanner = new Scanner(System.in);
    static int[][] battlefield1 = new int[17][17];
    static int[][] battlefield2 = new int[17][17];
    static int[][] monitor1 = new int[17][17];
    static int[][] monitor2 = new int[17][17];


    public static void main(String[] args) {
        System.out.println("Player#1, пожалуйста, в ведите ваше имя");
        playerName1 = scanner.nextLine();
        System.out.println("Привет, " + playerName1 + "!");

        System.out.println("Player#2, пожалуйста, в ведите ваше имя");
        playerName2 = scanner.nextLine();
        System.out.println("Привет, " + playerName2 + "!");
        placeShips(playerName1, battlefield1);
        placeShips(playerName2, battlefield2);
        do {
            makeTurn(playerName1, monitor1, battlefield2);
            makeTurn(playerName2, monitor2, battlefield1);
        } while (!isWinCondition());
    }

    public static void placeShips(String playerName, int[][] battlefield) {
        int deck = 6;
        while (deck >= 1) {
            System.out.println("---------------------------------------------");
            System.out.println(playerName + ", пожалуйста расместити ваш " + deck + "-палубный корабль на поле боя!");
            System.out.println();

            drawField(battlefield);
            System.out.println("Пажалуста, в ведите OX коодинат: ");
            int x = scanner.nextInt();
            System.out.println("Пажалуста, в ведите OY коодинат: ");
            int y = scanner.nextInt();
            System.out.println("Выберите направление: ");
            System.out.println("1. Вертикаль.");
            System.out.println("2. Горизонталь.");
            int direction = scanner.nextInt();
            if (!isAvailable(x, y, deck,direction,battlefield)){
                System.out.println("Неправильные координаты.");
                continue;
            }
            for (int i = 6; i >= 1; i--) {
                for (int j = i; j <= 7 - i; j++) {
                    if (direction == 1) {
                        battlefield[x][y + i] = 1;
                    } else {
                        battlefield[x + i][y] = 1;
                    }
                }
            }
            deck--;
            clearScreen();
        }
    }

    public static void drawField(int[][] battlefield) {
        System.out.println("  A B C D E F G H I J K L M N O P");
        for (int i = 1; i < battlefield.length; i++) {
            System.out.print(i + " ");
            for (int j = 1; j < battlefield[1].length; j++) {
                if (battlefield[j][i] == 0) {
                    System.out.print("- ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }

    public static void makeTurn(String playerName, int[][] monitor, int[][] battlefield) {
        while (true) {
            System.out.println(playerName + ", пожалуйста, сделайте свой ход.");
            System.out.println("  A B C D E F G H I J K L M N O P");
            for (int i = 1; i < monitor.length; i++) {
                System.out.print(i + " ");
                for (int j = 1; j < monitor[1].length; j++) {
                    if (monitor[i][j] == 0) {
                        System.out.print("- ");
                    } else if (monitor[i][j] == 1) {
                        System.out.print("* ");
                    } else {
                        System.out.println("X ");
                    }
                }
                System.out.println();
            }
            System.out.println("Пажалуста, в ведите OX коодинат: ");
            int x = scanner.nextInt();
            System.out.println("Пажалуста, в ведите OY коодинат: ");
            int y = scanner.nextInt();
            if (battlefield[y][x] == 1) {
                System.out.println("Попал! Сделайте ваш ход!");
                monitor[y][x] = 2;
            } else {
                System.out.println("Мимо! Ход противника.");
                monitor[y][x] = 1;
                break;
            }
            clearScreen();
        }
    }

    public static boolean isWinCondition() {
        int counter1 = 0;
        for (int i = 0; i < monitor1.length; i++) {
            for (int j = 0; j < monitor1[i].length; j++) {
                if (monitor1[i][j] == 2) {
                    counter1++;
                }
            }
        }

        int counter2  = 0;
        for (int i = 0; i < monitor2.length; i++) {
            for (int j = 0; j < monitor2[i].length; j++) {
                if (monitor2[i][j] == 2){
                    counter2++;
                }
            }
        }

        if (counter1 >= 16){
            System.out.println(playerName1 + "Победа!");
            return true;
        }
        if (counter2 >= 16){
            System.out.println(playerName1 + "Победа!");
            return true;
        }
        return false;
    }
    private static boolean isAvailable(int x, int y, int deck, int direction, int[][] battlefield){
        if (direction == 1){
            if(y + deck > battlefield.length){
                return false;
            }
        }
        if (direction == 2){
            if (x + deck > battlefield[0].length){
                return false;
            }
        }
        while (deck != 0){
            for (int i = 0; i < deck; i++) {
                int xi = 0;
                int yi = 0;
                if (direction == 1){
                    yi = i;
                }else {
                    xi = i;
                }
                if (x + 1 + xi < battlefield.length && x + 1 + xi >= 0){
                    if (battlefield[x + 1 + xi][y + yi] != 0){
                        return false;
                    }
                }
                if (x - 1 + xi < battlefield.length && x + 1 + xi >= 0){
                    if (battlefield[x - 1 + xi][y + yi] != 0){
                        return false;
                    }
                }
                if (y + 1 + yi < battlefield.length && y + 1 + yi >= 0){
                    if (battlefield[x + xi][y + 1 + yi] != 0){
                        return false;
                    }
                }
                if (y - 1 + yi < battlefield.length && y + 1 + yi >= 0){
                    if (battlefield[x + xi][y - 1 + yi] != 0){
                        return false;
                    }
                }
            }
            deck--;
        }
        return true;
    }
    public static void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
