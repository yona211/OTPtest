package com.example.otptest;

import android.graphics.Bitmap;

public class Winner {
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

    public String getVotesNumber() { return VotesNumber; }
    public String getFirstName() { return FirstName; }
    public String getLastName() { return LastName; }
    public String getClassName() { return ClassName; }
    public Bitmap getBitmap() { return Bitmap; }

    public String printCompetitor() { return "FN: " + this.FirstName + ", LN: " + this.LastName + ", C: " + this.ClassName + ", VN: " + this.VotesNumber;}

}
