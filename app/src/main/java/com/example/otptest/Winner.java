package com.example.otptest;

import android.graphics.Bitmap;

public class Winner implements Human{
    private String FirstName;
    private String LastName;
    private String ClassName;
    private String VotesNumber;
    private Bitmap Bitmap;

    public Winner(String FirstName, String LastName, String ClassName, String VotesNumber, Bitmap Bitmap)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ClassName = ClassName;
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
    @Override
    public String print() { return "FN: " + this.FirstName + ", LN: " + this.LastName + ", C: " + this.ClassName + ", VN: " + this.VotesNumber;}

}
