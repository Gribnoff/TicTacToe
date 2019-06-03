import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    //блок настроек игры

    private static char[][] map; //поле игры
    private static int size; //размер поля
    private static int winLine; //точек в ряд для победы
    private static final boolean SILLY_MODE = true;

    private static final char CELL_EMPTY =  '.';
    private static final char CELL_X =  'X';
    private static final char CELL_O =  'O';

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Введите размер поля");
        size = scanner.nextInt();
        System.out.println("Введите количество фишек в ряд для победы");
        winLine = scanner.nextInt();
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
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = CELL_EMPTY;
            }
        }
    }

    /**
     * Вывод поля на экран
     */
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
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isCellValid(x, y));
        } else {
            int[][] cellRatingMap = new int[size][size];
            int bestMoveRating = -1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
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

        if (x < 0 || x >= size || y < 0 || y >= size)
            result = false;
        else if (!isCellEmpty(x, y))
            result = false;

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

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size - winLine + 1; i++) {
            for (int j = 0; j < size - winLine + 1; j++) {
                if (checkDiagonals(playerSymbol, j, i) || checkLanes(playerSymbol, j, i))
                    return true;
            }
        }

        return false;
    }

    /**
     * Проверка столбцов и строк на победу
     * @param playerSymbol - текущий игрок
     * @return boolean есть ли строка или столбец, полностью заполненная игроком
     */
    private static boolean checkLanes(char playerSymbol, int offsetX, int offsetY){
        boolean rows, cols;
        for (int i = offsetY; i < winLine + offsetY; i++) {
            rows = true;
            cols = true;
            for (int j = offsetX; j < winLine + offsetX; j++) {
                cols &= (map[i][j] == playerSymbol);
                rows &= (map[j][i] == playerSymbol);
            }
            if (cols || rows)
                return true;
        }
        return false;
    }
    /**
     * Проверка диагоналей на победу
     * @param playerSymbol - текущий игрок
     * @return boolean есть ли строка или столбец, полностью заполненная игроком
     */
    private static boolean checkDiagonals(char playerSymbol, int offsetX, int offsetY){
        boolean right, left;
        right = true;
        left = true;
        for (int i = 0; i < winLine; i++) {
            left &= (map[i + offsetY][i + offsetX] == playerSymbol);
            right &= (map[winLine - i - 1 + offsetY][i + offsetX] == playerSymbol);
        }
        if (left || right)
            return true;

        return false;
    }
}
