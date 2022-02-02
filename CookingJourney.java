import java.util.Scanner;

public class CookingJourney {

    static int playerRow = 0, playerCol = 0, money=0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        char [][] matrix = new char[n][n];

        for (int i = 0; i < matrix.length; i++) {
            String line = scanner.nextLine();
            matrix[i] = line.toCharArray();
            if (line.contains("S")){
                playerRow = i;
                playerCol = line.indexOf("S");
            }
        }
        boolean isInMatrix = true;
        while (isInMatrix && money < 50){
            String direction = scanner.nextLine();
            if (direction.equals("right")){
                isInMatrix = movePlayer(matrix, 0, +1);
            }else if (direction.equals("left")){
                isInMatrix = movePlayer(matrix,0,-1);
            }else if (direction.equals("up")){
                isInMatrix = movePlayer(matrix,-1,0);
            }else if (direction.equals("down")){
                isInMatrix = movePlayer(matrix, +1,0);
            }
        }
        if (money < 50){
            System.out.println("Bad news! You are out of the pastry shop.");
        }else {
            System.out.println("Good news! You succeeded in collecting enough money!");
        }
        System.out.printf("Money: %d%n",money);
        printMatrix(matrix);
    }

    private static void printMatrix(char[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                System.out.print(matrix[r][c]);
            }
            System.out.println();
        }
    }

    private static boolean movePlayer(char[][] matrix, int rowAddition, int colAddition) {
        matrix[playerRow][playerCol] = '-';
        boolean isInBounds = inBounds(matrix, playerRow+rowAddition, playerCol+colAddition);
        if (isInBounds){
            if (Character.isDigit(matrix[playerRow+rowAddition][playerCol+colAddition])){
                money += Character.getNumericValue(matrix[playerRow+rowAddition][playerCol+colAddition]);
            }else if (matrix[playerRow+rowAddition][playerCol+colAddition] == 'P'){
                matrix[playerRow+rowAddition][playerCol+colAddition] = '-';
                for (int r = 0; r < matrix.length; r++) {
                    for (int c = 0; c < matrix[r].length; c++) {
                        if (matrix[r][c] == 'P'){
                            playerRow = r;
                            playerCol = c;
                            matrix[playerRow][playerCol] = 'S';
                            return true;
                        }
                    }
                }
            }
            playerRow += rowAddition;
            playerCol += colAddition;
            matrix[playerRow][playerCol] = 'S';
        }
        return isInBounds;
    }

    private static boolean inBounds(char[][] matrix, int r, int c) {
        return r>=0 && r < matrix.length && c>=0 && c < matrix[r].length;
    }
}
