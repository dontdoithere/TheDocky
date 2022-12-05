package ca.group6.it.thedocky;

public class TransactionHistory {

    public int amount;
    public String date;
    public String cityName;
    public String postId;

    public TransactionHistory() {
    }

    public TransactionHistory(int amount, String date, String cityName, String postId) {
        this.amount = amount;
        this.date = date;
        this.cityName = cityName;
        this.postId = postId;
    }

}
