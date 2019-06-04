import java.util.Random;
import java.util.Scanner;

class TicTacToe {
    //блок настроек игры

    private static char[][] map; //поле игры
    private static int size; //размер поля
    private static int winLine; //точек в ряд для победы
    private static boolean sillyMode; //сложность

    private static final char CELL_EMPTY =  '.';
    private static final char CELL_X =  'X';
    private static final char CELL_O =  'O';

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        String mode;
        do {
            System.out.println("Играем с \"умным\" компьютером? y/n");
            mode = scanner.nextLine().toLowerCase();
            if ("y".equals(mode))
                sillyMode = false;
            else if ("n".equals(mode))
                sillyMode = true;
        } while (!"y".equals(mode) && !"n".equals(mode));

        do {
            System.out.println("Введите размер поля(не меньше 3)");
            while (!scanner.hasNextInt()) {
                System.out.println("Это не число!");
                scanner.nextLine();
            }
            size = scanner.nextInt();
        } while (size < 3);

        do {
            System.out.printf("Введите количество фишек в ряд для победы(от 3 до %d)\n", size);
            while (!scanner.hasNextInt()) {
                System.out.println("Это не число!");
                scanner.nextLine();
            }
            winLine = scanner.nextInt();
        } while (winLine > size || winLine < 3);
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
        int x = -1;
        int y = -1;

        do{
            System.out.println("Введите координаты:");
            if (scanner.hasNextInt())
                y = scanner.nextInt() - 1; //координаты по вертикали
            else
                scanner.nextLine();
            if (scanner.hasNextInt())
                x = scanner.nextInt() - 1; //координаты по горизонтали
            else scanner.nextLine();
        } while(!isCellValid(x, y));

        map[y][x] = CELL_X;
    }

    /**
     * Ход компьютера
     */
    private static void computerTurn() {
        int x = -1;
        int y = -1;

        if (sillyMode) {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isCellValid(x, y));
        } else {
            int[][] cellRatingMap = createCellRatingMap();
            int bestMoveRating = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (bestMoveRating < cellRatingMap[j][i]) {
                        bestMoveRating = cellRatingMap[j][i];
                        x = j;
                        y = i;
                    }
                }
            }
        }
        System.out.printf("Компьтер ходит: %d %d\n", (y + 1), (x + 1));
        map[y][x] = CELL_O;
    }

    /**
     * Создаёт карту с ценностью клеток для выбора хода компьютера
     * @return int[][] карту приоритетности ходов
     */
    private static int[][] createCellRatingMap() {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = countCellRating(i, j);
            }
        }

        return result;
    }

    /**
     * Подсчёт ценности клетки
     * @param x - координата по горизонтали
     * @param y - координата по вертикали
     * @return int вероятность хода компьютера на эту клетку
     */
    private static int countCellRating(int x, int y) {
        if (map[y][x] != CELL_EMPTY)
            return Integer.MIN_VALUE;

        int result = 0;

        for (int i = -1; i <= 1; i++) {
            if (x + i >= 0 && x + i < size && y + i >= 0 && y + i < size) {
                if (map[y + i][x + i] == CELL_O)
                    result++;
            }
            if (x + i >= 0 && x + i < size) {
                if (map[y][x + i] == CELL_O)
                    result++;
            }
            if (y + i >= 0 && y + i < size) {
                if (map[y + i][x] == CELL_O)
                    result++;
            }
        }
        return result;
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
        } else if (isMapFull()) {
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

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == CELL_EMPTY)
                    return false;
            }
        }

        return true;
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
     * @param offsetX - сдвиг по горизонтали
     * @param offsetY - сдвиг по вертикали
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
     * @param offsetX - сдвиг по горизонтали
     * @param offsetY - сдвиг по вертикали
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
        return left || right;
    }
}
