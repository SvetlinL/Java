import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class Python {
    static int pythonRow = 0, pythonCol = 0, pythonLength = 1, food = 0;
    static boolean flagExit = false;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        char [][] matrix = new char[n][n];
        ArrayDeque<String> commands = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split(", ")).forEach(commands::offer);

        for (int i = 0; i < matrix.length; i++) {
            String line = scanner.nextLine();
            line = line.replace(" ","");
            if (line.contains("s")){
                pythonRow = i;
                pythonCol = line.indexOf("s");
            }
            if (line.contains("f")){
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == 'f'){
                        food++;
                    }
                }
            }
            matrix[i] = line.toCharArray();
        }

        while (!commands.isEmpty() && !flagExit && food != 0){
            String command = commands.poll();
            if (command.equals("right")){
                playerMove(matrix, 0, +1);
            }else if (command.equals("left")){
                playerMove(matrix, 0, -1);
            }else if (command.equals("up")){
                playerMove(matrix, -1, 0);
            }else if (command.equals("down")){
                playerMove(matrix, +1, 0);
            }
        }
        if (food == 0){
            System.out.printf("You win! Final python length is %d",pythonLength);
        }else if (flagExit){
            System.out.println("You lose! Killed by an enemy!");
        }else {
            System.out.printf("You lose! There is still %d food to be eaten.",food);
        }
    }

    private static void playerMove(char[][] matrix, int rowAddition, int colAddition) {
        matrix[pythonRow][pythonCol] = '*';
        boolean isInBounds = checkBounds(matrix, pythonRow+rowAddition, pythonCol+colAddition);
        if (isInBounds){
            pythonRow += rowAddition;
            pythonCol += colAddition;
            if (matrix[pythonRow][pythonCol] == 'f'){
                food --;
                pythonLength ++;
            }
            if (matrix[pythonRow][pythonCol] == 'e'){
                flagExit = true;
            }
            matrix[pythonRow][pythonCol] = 's';
        }else {
            if (pythonCol + colAddition >= matrix[pythonRow].length){
                pythonCol = 0;
            }
            if (pythonCol + colAddition < 0){
                pythonCol = matrix[pythonRow].length-1;
            }
            if (pythonRow + rowAddition < 0){
                pythonRow = matrix.length-1;
            }
            if (pythonRow + rowAddition >= matrix.length){
                pythonRow = 0;
            }
            if (matrix[pythonRow][pythonCol] == 'f'){
                food --;
                pythonLength ++;
            }
            if (matrix[pythonRow][pythonCol] == 'e'){
                flagExit = true;
            }
            matrix[pythonRow][pythonCol] = 's';
        }
    }

    private static boolean checkBounds(char[][] matrix, int r, int c) {
        return r >=0 && r < matrix.length && c>=0 && c < matrix[r].length;
    }
}
