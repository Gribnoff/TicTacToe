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

    /**
     * Инициализация поля
     */
    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = CELL_EMPTY;
            }
        }
    }

    /**
     * Вывод поля на экран
     */
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

    /**
     * Ход игрока
     */
    private static void humanTurn() {
        int x, y;

        do{
            System.out.println("Введите координаты:");
            y = scanner.nextInt() - 1; //координаты по вертикали
            x = scanner.nextInt() - 1; //координаты по горизонтали
        } while(!isCellValid(x, y));

        map[y][x] = CELL_X;
    }

    /**
     * Ход компьютера
     */
    private static void computerTurn() {
        int x;
        int y;

        if (SILLY_MODE) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isCellValid(x, y));
        } else {
            int[][] cellRatingMap = new int[SIZE][SIZE];
            int bestMoveRating = -1;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    int thisMoveRating = checkCellRating(i, j);
                    if (bestMoveRating < thisMoveRating) {
                        bestMoveRating = thisMoveRating;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        System.out.printf("Компьтер ходит: %d %d\n", (y + 1), (x + 1));
        map[y][x] = CELL_O;
    }

    /**
     * Проверка правильности хода
     * @param x - координата по горизонтали
     * @param y - координата по вертикали
     * @return boolean валидность хода
     */
    private static boolean isCellValid(int x, int y) {
        boolean result = true;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            result = false;
        else if (!isCellEmpty(x, y))
            result = false;

        return result;
    }

    /**
     * Проверка ценности клетки для ИИ
     * @param x - координата по горизонтали
     * @param y - координата по вертикали
     * @return int количество соседних клеток, занятых компьютером
     */
    private static int checkCellRating(int x, int y) {
        int result = -1; //у занятой клетки минимальный приоритет

        if (isCellEmpty(x, y)) {

        }

        return result;
    }

    /**
     * Проверка на пустоту клетки
     * @param x - координата по горизонтали
     * @param y - координата по вертикали
     * @return boolean свободность клетки
     */
    private static boolean isCellEmpty(int x, int y) {
        boolean result = true;

        if (map[y][x] != CELL_EMPTY)
            result = false;

        return result;
    }

    /**
     * Проверка конца игры
     * @param playerSymbol - текущий игрок
     * @return boolean закончена ли игра
     */
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

    /**
     * Проверка заполненности поля
     * @return boolean заполнено ли поле полностью
     */
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

    /**
     * Проверка победы
     * @param playerSymbol - тещий игрок
     * @return boolean есть ли победитель
     */
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
