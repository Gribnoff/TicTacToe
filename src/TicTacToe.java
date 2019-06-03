import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    //блок настроек игры

    private static char[][] map; //поле игры
    private static final int SIZE = 3; //размер поля
    private static final boolean SILLY_MODE = true;

    private static final char CELL_EMPTY =  10242;
    private static final char CELL_X =  'X';
    private static final char CELL_O =  'O';

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while(true) {
            humanTurn();
            if (isEndGame(CELL_X)) {
                break;
            }

            computerTurn();
            if (isEndGame(CELL_O)) {
                break;
            }
        }

        System.out.println("Игра окончена");
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = CELL_EMPTY;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i < SIZE + 1; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                if (i == 0)
                    System.out.print((j + 1) + " ");
                else
                    System.out.print(map[i - 1][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void humanTurn() {
        int x, y;

        do{
            System.out.println("Введите координаты:");
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        } while(!isCellValid(x, y));

        map[y][x] = CELL_X;
    }

    private static void computerTurn() {
        int x;
        int y;

        if (SILLY_MODE) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isCellValid(x, y));
        } else {
            int cellRating;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!isCellEmpty(x, y))
                        continue;
                    else {

                    }
                }
            }
        }
        System.out.printf("Компьтер ходит: %d %d\n", (y + 1), (x + 1));
        map[y][x] = CELL_O;
    }

    private static boolean isCellValid(int x, int y) {
        boolean result = true;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            result = false;
        else if (!isCellEmpty(x, y))
            result = false;

        return result;
    }

    private static boolean isCellEmpty(int x, int y) {
        boolean result = true;

        if (map[y][x] != CELL_EMPTY)
            result = false;

        return result;
    }

    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;

        printMap();

        if (checkWin(playerSymbol)) {
            System.out.println("Победили " + playerSymbol);
            result = true;
        }

        if (isMapFull()) {
            System.out.println("Ничья");
            result = true;
        }

        return result;
    }

    private static boolean isMapFull() {
        boolean result = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] != CELL_EMPTY)
                    result = false;
            }
        }

        return result;
    }

    private static boolean checkWin(char playerSymbol) {
        boolean result = false;

        if(
                map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol ||
                map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol ||
                map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol ||
                map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol ||
                map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol ||
                map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol ||
                map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol ||
                map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol) {
            result = true;
        }
        return result;
    }
}
