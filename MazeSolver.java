import java.util.Scanner;
import java.util.Random;

public class MazeSolver {

    static final char WALL = '|';
    static final char OPEN_SPACE = 'o';
    static final char START = 'S';
    static final char END = 'E';
    static final char PATH = '0';

    private char[][] maze;
    private int size;
    private int startRow, startCol;
    private int endRow, endCol;

    public MazeSolver(int size) {
        this.size = size;
        this.maze = new char[size][size];
        initializeMaze();
    }

    private void initializeMaze() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = OPEN_SPACE;
            }
        }

        startRow = 0;
        startCol = 0;
        endRow = size - 1;
        endCol = size - 1;

        maze[startRow][startCol] = START;
        maze[endRow][endCol] = END;

        addRandomWalls();
    }

    private void addRandomWalls() {
        Random rand = new Random();
        int maxWalls = (int) (size * size * 0.25);
        int wallCount = 0;

        while (wallCount < maxWalls) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);

            if (!(row == startRow && col == startCol) && !(row == endRow && col == endCol) && maze[row][col] != WALL) {
                maze[row][col] = WALL;
                wallCount++;
            }
        }
    }

    private void printMaze() {
        System.out.println("+---".repeat(size) + "+");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("| " + getCellSymbol(maze[i][j]) + " ");
            }
            System.out.println("|");
            System.out.println("+---".repeat(size) + "+");
        }
    }

    private char getCellSymbol(char cell) {
        switch (cell) {
            case START:
                return 'S';
            case END:
                return 'E';
            case WALL:
                return '|';
            case OPEN_SPACE:
                return 'o';
            case PATH:
                return '0';
            default:
                return cell;
        }
    }

    private boolean findPath(int row, int col) {

        if (row < 0 || row >= size || col < 0 || col >= size || maze[row][col] == WALL || maze[row][col] == PATH) {
            return false;
        }

        if (row == endRow && col == endCol) {
            return true;
        }

        maze[row][col] = PATH;

        if (findPath(row - 1, col) || findPath(row + 1, col) || findPath(row, col - 1) || findPath(row, col + 1)) {
            return true;
        }

        maze[row][col] = OPEN_SPACE;
        return false;
    }

    private void printPath() {
        System.out.println("Path:");
        printMaze();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size;
        do {
            System.out.print("Enter the size of the maze (n x n): ");
            size = scanner.nextInt();
        } while (size <= 0);

        MazeSolver mazeSolver = new MazeSolver(size);
        System.out.println("Generated Maze: ");
        mazeSolver.printMaze();

        int option;
        do {
            System.out.println("1. Print the Path");
            System.out.println("2. Generate Another Puzzle");
            System.out.println("3. Exit the Game ");
            System.out.print("Enter the choice (1/2/3): ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    if (mazeSolver.findPath(mazeSolver.startRow, mazeSolver.startCol)) {
                        mazeSolver.printPath();
                    } else {
                        System.out.println("No path found.");
                    }
                    break;
                case 2:
                    do {
                        System.out.print("Enter the size of the maze (n x n): ");
                        size = scanner.nextInt();
                    } while (size <= 0);
                    mazeSolver = new MazeSolver(size);
                    System.out.println("Generated Maze: ");
                    mazeSolver.printMaze();
                    break;
                case 3:
                    break;
            }

        } while (option != 3);

        scanner.close();
    }
}
