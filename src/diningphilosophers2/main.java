package diningphilosophers2;

import java.util.Scanner;

public class main {

    protected static final int numberOfPhilosofers = 5;

    public static void main(String[] args) throws InterruptedException {

        int[] foodAmount = new int[numberOfPhilosofers];
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter number of times each Philosopher will eat : ");

        for (int i = 0; i < numberOfPhilosofers; i++) {
            System.out.print("Philisophor " + (i + 1) + " --> ");
            foodAmount[i] = input.nextInt();

        }

        Philosophers[] phili = new Philosophers[numberOfPhilosofers];

        for (int i = 0; i < numberOfPhilosofers; i++) {

            phili[i] = new Philosophers(i, foodAmount[i]);

            new Thread(phili[i]).start();

        }

    }
}
