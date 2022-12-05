package ca.group6.it.thedocky;

public class User {

    public String username, email;
    public float balance;

    public User(){

    }

    public User(String username, String email,float balance){
        this.email = email;
        this.username = username;
        this.balance = balance;
    }
}
