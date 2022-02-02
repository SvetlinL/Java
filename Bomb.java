
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class Bomb {
    static int playerRow =0 , playerCol = 0, bombsOnTheField = 0;
    static boolean flagExit = false;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        char[][] matrix = new char[n][n];

        ArrayDeque<String> commandsQueue = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split(",")).forEach(commandsQueue::offer);

        for (int i = 0; i < matrix.length; i++) {
            String line = scanner.nextLine();
            line = line.replace(" ","");
            matrix[i] = line.toCharArray();
            if (line.contains("s")){
                playerRow = i;
                playerCol = line.indexOf("s");
            }
            if (line.contains("B")){
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == 'B'){
                        bombsOnTheField ++;
                    }
                }
            }
        }

        while (!commandsQueue.isEmpty() && bombsOnTheField != 0 && !flagExit){
            String command = commandsQueue.poll();
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

        if (bombsOnTheField == 0){
            System.out.println("Congratulations! You found all bombs!");
        }else if (flagExit){
            System.out.printf("END! %d bombs left on the field",bombsOnTheField);
        }else {
            System.out.printf("%d bombs left on the field. Sapper position: (%d,%d)",bombsOnTheField,
                    playerRow,playerCol);
        }
    }

    private static void movePlayer(char[][] matrix, int rowAddition, int colAddition) {
        matrix[playerRow][playerCol] = '+';
        boolean isInBounds = checkBounds(matrix, playerRow+rowAddition, playerCol + colAddition);
        if (isInBounds){
            playerRow += rowAddition;
            playerCol += colAddition;
            if (matrix[playerRow][playerCol]== 'B'){
                bombsOnTheField --;
                System.out.println("You found a bomb!");
            }
            if(matrix[playerRow][playerCol]== 'e'){
                flagExit = true;
            }
            matrix[playerRow][playerCol] = 's';
        }else {
            matrix[playerRow][playerCol] = 's';
        }

    }

    private static boolean checkBounds(char[][] matrix, int r, int c) {
        return r >= 0 && r < matrix.length && c >=0 && c < matrix[r].length;
    }
}
