package diningphilosophers2;

import static diningphilosophers2.main.numberOfPhilosofers;
import java.util.Random;

class Philosophers implements Runnable {

    private int currentPhilosopher;
    private int temp = 0;
    private int foodAmount;

    private enum State {

        THINKING, HUNGRY, EATING
    }
    private static State[] state = new State[numberOfPhilosofers];

    private Random randomNumber = new Random();

    public Philosophers(int currentPhilosopher, int foodAmount) throws InterruptedException {

        this.currentPhilosopher = currentPhilosopher;
        this.foodAmount = foodAmount;
        Think();

    }

    @Override
    public void run() {

        do {
            temp++;

            try {

                pickUp();

                putDown();

            } catch (InterruptedException ex) {
                System.out.println("Phillisopher was interupted");
            }

        } while (temp < this.foodAmount);

    }

    private void Think() throws InterruptedException {

        state[currentPhilosopher] = State.THINKING;
        printState(currentPhilosopher);
        Thread.sleep(randomNumber.nextInt(10));

    }

    private void Eat(int phili) throws InterruptedException {

        state[phili] = State.EATING;
        printState(phili);
        Thread.sleep(randomNumber.nextInt(10));

    }

    //Try to pickup the chopstickes
    private synchronized void pickUp() throws InterruptedException {

        state[currentPhilosopher] = State.HUNGRY;
        printState(currentPhilosopher);

        //test if one of the nighbours is already eating
        test(this.currentPhilosopher);

        //if one of the nighbours is already eating wait untill he finishes eating
        if (state[currentPhilosopher] != State.EATING) {

            System.out.println("Philosopher " + (this.currentPhilosopher + 1) + " is waiting");

            wait(10);

        }

    }

    // finished eating put down the chopstickes
    private synchronized void putDown() throws InterruptedException {

        state[this.currentPhilosopher] = State.THINKING;
        printState(this.currentPhilosopher);

        //wakeup all the waiting threads
        notifyAll();

        //check if thier was a nighbour waiting to eat 
        test((this.currentPhilosopher + 1) % numberOfPhilosofers);
        test((this.currentPhilosopher + 4) % numberOfPhilosofers);

    }

    //check if a phillisophor can eat
    private synchronized void test(int phili) throws InterruptedException {

        if (state[(phili + 1) % numberOfPhilosofers] != State.EATING
                && state[(phili + 4) % numberOfPhilosofers] != State.EATING
                && state[phili] == State.HUNGRY) {

            Eat(phili);

        }
    }

    private void printState(int phili) {

        System.out.println("Philisophor " + (phili + 1) + " is " + this.state[phili]);

    }
}
