import java.util.Scanner;

public class TicTacToe {
    //блок настроек игры

    private static char[][] map; //поле игры
    private static int size = 3; //размер поля

    private static final char CELL_EMPTY =  10242;
    private static final char CELL_X =  'X';
    private static final char CELL_O =  'O';

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMap();
        printMap();

        while(true) {
            humanTurn();
            if (isEndGame(CELL_X)) {
                break;
            }/*
            computerTurn();
            if (isEndGame(CELL_O)) {
                break;
            }*/
        }

        System.out.println("Игра окончена");
    }

    private static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = CELL_EMPTY;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i < size + 1; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
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

    private static boolean isCellValid(int x, int y) {
        boolean result = true;

        if (x < 0 || x >= size || y < 0 || y >= size)
            result = false;

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

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
