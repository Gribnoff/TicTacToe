import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    //блок настроек игры

    private static char[][] map; //поле игры
    private static final int SIZE = 4; //размер поля

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
        int x = -1;
        int y = -1;

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
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
        else if (map[y][x] != CELL_EMPTY)
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
        if (checkDiagonals(playerSymbol) || checkLanes(playerSymbol))
            result = true;

        return result;
    }

    /**
     * Проверка столбцов и строк на победу
     * @param playerSymbol - текущий игрок
     * @return boolean есть ли строка или столбец, полностью заполненная игроком
     */
    private static boolean checkLanes(char playerSymbol){
        boolean rows, cols;
        for (int i = 0; i < SIZE; i++) {
            rows = true;
            cols = true;
            for (int j = 0; j < SIZE; j++) {
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
    private static boolean checkDiagonals(char playerSymbol){
        boolean right, left;
        right = true;
        left = true;
        for (int i = 0; i < SIZE; i++) {
            left &= (map[i][i] == playerSymbol);
            right &= (map[SIZE - i - 1][i] == playerSymbol);
        }
        if (left || right)
            return true;

        return false;
    }
}
