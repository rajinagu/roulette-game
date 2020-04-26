package main.game;

public class BetDetails {
    private String bettingPerson;
    private String betType;
    private double betAmount;

    public BetDetails(String bettingPerson, String betType, double betAmount) {
        this.bettingPerson = bettingPerson;
        this.betType = betType;
        this.betAmount = betAmount;
    }

    public String getBettingPerson() {
        return bettingPerson;
    }


    public String getBetType() {
        return betType;
    }


    public double getBetAmount() {
        return betAmount;
    }

}
