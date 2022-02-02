import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class Bouquets {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //from last
        ArrayDeque<Integer> tulips = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split(", ")).mapToInt(Integer::parseInt)
                .forEach(tulips::push);

        //from first
        ArrayDeque<Integer> daffodils = new ArrayDeque<>();
        Arrays.stream(scanner.nextLine().split(", ")).mapToInt(Integer::parseInt)
                .forEach(daffodils::offer);

        int storedFlowers = 0;
        int numberOfBouquets = 0;

        while (!tulips.isEmpty() && !daffodils.isEmpty()){
            int currentTulip = tulips.pop();
            int currentDaffodil = daffodils.poll();

            if (currentDaffodil + currentTulip == 15){
                numberOfBouquets++;

            }else {
                while (currentDaffodil + currentTulip > 15){
                    currentTulip -= 2;
                }
                if (currentDaffodil + currentTulip == 15) {
                    numberOfBouquets++;
                }else {
                    storedFlowers += currentDaffodil + currentTulip;
                }
            }
        }
        numberOfBouquets += storedFlowers / 15;

        if (numberOfBouquets >= 5){
            System.out.printf("You made it! You go to the competition with %d bouquets!%n",numberOfBouquets);
        }else {
            int numberNeeded = 5 - numberOfBouquets;
            System.out.printf("You failed... You need more %d bouquets.",numberNeeded);
        }
    }
}
