import java.util.Scanner;

public class Selling {
    static int playerRow = 0, playerCol = 0, money = 0;
    static boolean flagExit = false;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        char [][] matrix = new char[n][n];
        for (int i = 0; i < matrix.length; i++) {
            String line = scanner.nextLine();
            if (line.contains("S")){
                playerRow = i;
                playerCol = line.indexOf("S");
            }
            matrix[i] = line.toCharArray();
        }

        while (!flagExit && money < 50){
            String command = scanner.nextLine();
            if (command.equals("right")){
                movePlayer(matrix, 0, +1);
            }else if (command.equals("left")){
                movePlayer(matrix, 0, -1);
            }else if (command.equals("up")){
                movePlayer(matrix, -1, 0);
            }else if (command.equals("down")){
                movePlayer(matrix, +1, 0);
            }
        }

        if (flagExit){
            System.out.println("Bad news, you are out of the bakery.");
        }else {
            System.out.println("Good news! You succeeded in collecting enough money!");
        }
        System.out.printf("Money: %d%n",money);
        printMatrix(matrix);
    }

    private static void printMatrix(char[][] matrix) {
        for (char[] chars : matrix) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    private static void movePlayer(char[][] matrix, int rowAddition, int colAddition) {
        matrix[playerRow][playerCol] = '-';
        boolean isInBounds = checkBounds(matrix,playerRow+rowAddition, playerCol+colAddition);
        if (isInBounds){
            playerRow += rowAddition;
            playerCol += colAddition;
            if (Character.isDigit(matrix[playerRow][playerCol])){
                money += Character.getNumericValue(matrix[playerRow][playerCol]);
            }else if (matrix[playerRow][playerCol] == 'O'){
                matrix[playerRow][playerCol] = '-';
                for (int r = 0; r < matrix.length; r++) {
                    for (int c = 0; c < matrix[r].length; c++) {
                        if (matrix[r][c] == 'O'){
                            playerRow = r;
                            playerCol = c;
                            matrix[playerRow][playerCol] = 'S';

                        }
                    }
                }
            }
            matrix[playerRow][playerCol] = 'S';
        }else {
            flagExit = true;
        }
    }

    private static boolean checkBounds(char[][] matrix, int r, int c) {
        return r>=0 && r<matrix.length && c>=0 && c<matrix[r].length;
    }
}
