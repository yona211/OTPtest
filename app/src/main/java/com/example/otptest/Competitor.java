package com.example.otptest;

import android.graphics.Bitmap;

public class Competitor implements Human{
    private String FirstName;
    private String LastName;
    private String ClassName;
    private String VotesNumber;
    private String Id;

    private Bitmap Bitmap;

    public Competitor(String FirstName, String LastName, String ClassName, String VotesNumber, String Id, Bitmap Bitmap)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ClassName = ClassName;
        this.Id = Id;
        this.VotesNumber = VotesNumber;
        this.Bitmap = Bitmap;
    }

    @Override
    public String getVotesNumber() { return VotesNumber; }
    @Override
    public String getFirstName() { return FirstName; }
    @Override
    public String getLastName() { return LastName; }
    @Override
    public String getClassName() { return ClassName; }
    @Override
    public Bitmap getBitmap() { return Bitmap; }

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }

    @Override
    public String print() { return "FN: " + this.FirstName + ", LN: " + this.LastName + ", C: " + this.ClassName + ", I: " + this.Id + ", VN: " + this.VotesNumber;}
}

