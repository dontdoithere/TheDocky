package ca.group6.it.thedocky;

public class TransactionHistory {

    public float amount;
    public String date;
    public String cityName;
    public String postId;

    public TransactionHistory() {
    }

    public TransactionHistory(float amount, String date, String cityName, String postId) {
        this.amount = amount;
        this.date = date;
        this.cityName = cityName;
        this.postId = postId;
    }

}
