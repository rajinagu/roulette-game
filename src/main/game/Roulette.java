package main.game;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Roulette {

    // Create a list of product objects, currently empty
    public static List<BetDetails> betList = new ArrayList<>();

    //Create a List of Players Objects
    public static List<Player> players = new ArrayList<Player>();


    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/Players.txt");

        try {

            List<String> playerDetails = Files.readAllLines(path);

            for (String line : playerDetails) {
                //String[] tokens = line.split(" ");
                Player p = new Player(line);
                players.add(p);
            }

            Roulette re = new Roulette();
            re.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void readBets() {
        betList = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your bets :");
        while (true) {
            String s = in.nextLine();

            // Split this string into separate fields
            String[] fields = s.split(" ");

            // Build a product object from the fields
            BetDetails betDetails = new BetDetails(fields[0], fields[1], Double.parseDouble(fields[2]));

            betList.add(betDetails);
        }


    }

    private void rollBall() {

            // Obtain a number between [0 - 36].
            int betnumber = (int) (Math.random() * 37);

            //Print the bet board
            System.out.println();
            System.out.println("Number : " + betnumber);
            System.out.println("Player\t Bet \t Outcome \t Winnings");

            for (BetDetails b : betList) {
                if (b.getBetType().equals("EVEN")) {
                    if (betnumber % 2 == 0) {
                        double winningAmount = 2 * b.getBetAmount();

                        System.out.println(b.getBettingPerson() + "\t EVEN \t WIN \t" + winningAmount);

                    } else {
                        System.out.println(b.getBettingPerson() + "  " + b.getBetType() + "   " + "LOSE" + "0.0");
                    }

                } else if (b.getBetType().equals("ODD")) {

                    if (betnumber % 2 == 1) {
                        double winningAmount = 2 * b.getBetAmount();

                        System.out.println(b.getBettingPerson() + "\t ODD \t WIN \t" + winningAmount);
                    } else {
                        System.out.println(b.getBettingPerson() + "  " + b.getBetType() + "   " + "LOSE" + "0.0");
                    }


                } else {
                    if (betnumber == Integer.parseInt(b.getBetType())) {
                        double winningAmount = 36 * b.getBetAmount();
                        System.out.println(b.getBettingPerson() + "  " + b.getBetType() + "   " + "WIN" + winningAmount);

                    } else {
                        System.out.println(b.getBettingPerson() + "  " + b.getBetType() + "   " + "LOSE" + "0.0");
                    }

                }

            }

            //clear the betList
            betList = Collections.emptyList();

        readBets();


    }

    public void execute() {



        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);


           executorService.submit(this::readBets);

            executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    // This should be in a try-catch because any error here
                    // will stop the recurrence
                    try {
                        // The timer will only repeat if the last run is finished. So
                        // we put each new process in a different thread than the timer
                        // itself, so the last timer call "finishes" as soon as the process
                        // leaves the timer's thread.
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    rollBall();
                                } catch (Exception erTimerThread) {
                                    System.out.println("Error" + erTimerThread.toString());
                                }
                            }
                        });
                        t.start();

                    } catch (Exception erTimer) {
                        System.out.println("Error" + erTimer.toString());
                    }
                }
            }, 30, 30, java.util.concurrent.TimeUnit.SECONDS);


    }


}
