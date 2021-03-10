package com.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;


    public static void main(String[] args) {
	// write your code here
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        AscendingHacker ascendingHacker = new AscendingHacker(vault);
        DescendingHacker descendingHacker = new DescendingHacker(vault);
        PoliceThread policeThread = new PoliceThread();
        List<Thread> threadList = new ArrayList<>();
        threadList.add(ascendingHacker);
        threadList.add(descendingHacker);
        threadList.add(policeThread);

        for(Thread thread: threadList)
            thread.start();

    }

    private static class Vault{
        private int password;

        public Vault(int password) {
//            System.out.println("Password is: "+password);
            this.password = password;
        }

        public boolean isCorrectPassword(int guess){
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.password == guess;
        }
    }


    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }
        @Override
        public void start(){
//            System.out.println("Starting Thread : " + Thread.currentThread().getName() );
            System.out.println("Starting Thread : " + super.getName());
            super.start();
        }

    }

    private static class AscendingHacker extends HackerThread{
        public AscendingHacker(Vault vault) {
            super(vault);
        }
        @Override
        public void run(){

            for(int guess=0;guess<MAX_PASSWORD;guess++)
            {
                if(vault.isCorrectPassword(guess)){
                    System.out.println(super.getName() + " Guessed the Password " + guess);
                    System.exit(0);
                }
            }
        }

    }

    private static class DescendingHacker extends HackerThread{
        public DescendingHacker(Vault vault) {
            super(vault);
        }
        @Override
        public void run(){
            for(int guess=MAX_PASSWORD;guess>=0;guess--)
            {
                if(vault.isCorrectPassword(guess)){
                    System.out.println(super.getName() + " Guessed the Password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run(){
            for(int time=10;time>0;time--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Time Remains :" + time);
            }
            System.out.println("Game over Hackers ");
            System.exit(0);
        }

    }

}
