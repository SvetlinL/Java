import java.util.*;

public class MagicBox {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //from first
        ArrayDeque<Integer> firstMagicBox = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).forEach(firstMagicBox::offer);
        //from last
        ArrayDeque<Integer> secondMagicBox = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).forEach(secondMagicBox::push);
        int claimedItems = 0;

        while (!firstMagicBox.isEmpty() && !secondMagicBox.isEmpty()){
            int firstMagicBoxCurrentItem = firstMagicBox.peek();
            int secondMagicBoxCurrentItem = secondMagicBox.peek();

            if ((firstMagicBoxCurrentItem + secondMagicBoxCurrentItem) %2 == 0 ){
                claimedItems += firstMagicBox.poll() + secondMagicBox.pop();
            }else {
                secondMagicBoxCurrentItem = secondMagicBox.pop();
                firstMagicBox.offer(secondMagicBoxCurrentItem);
            }
        }

        if (firstMagicBox.isEmpty()){
            System.out.println("First magic box is empty.");
        }else {
            System.out.println("Second magic box is empty.");
        }

        if (claimedItems >= 90){
            System.out.printf("Wow, your prey was epic! Value: %d",claimedItems);
        }else {
            System.out.printf("Poor prey... Value: %d",claimedItems);
        }
    }
}
