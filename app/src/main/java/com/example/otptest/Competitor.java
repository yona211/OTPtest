package com.example.otptest;

import android.graphics.Bitmap;

public class Competitor {
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

    public String getVotesNumber() { return VotesNumber; }
    public String getFirstName() { return FirstName; }
    public String getLastName() { return LastName; }
    public String getId() { return Id; }
    public String getClassName() { return ClassName; }
    public Bitmap getBitmap() { return Bitmap; }

    public void setId(String id) { Id = id; }

    public String printCompetitor() { return "FN: " + this.FirstName + ", LN: " + this.LastName + ", C: " + this.ClassName + ", I: " + this.Id + ", VN: " + this.VotesNumber;}
}

