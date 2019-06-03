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
/*
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

        System.out.println("Игра окончена"); */
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
}
