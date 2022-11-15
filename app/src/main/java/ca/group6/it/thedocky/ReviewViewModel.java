package ca.group6.it.thedocky;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ReviewViewModel extends ViewModel {


    private String Qid;
    private String name;
    private String phonenumber;
    private String email;
    private String address;
    private String feedback;

    private FirebaseAuth auth;


    public ReviewViewModel(String qid, String name, String phonenumber, String email, String address, String feedback, MutableLiveData<String> mText) {
        Qid = qid;
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
        this.feedback = feedback;


    }



    public ReviewViewModel() {

    }

    public String getQid(){
        return Qid;
    }

    public void setQid(String qid){
        Qid= qid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeedback() {
        return feedback;
    }


    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }



    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }
}