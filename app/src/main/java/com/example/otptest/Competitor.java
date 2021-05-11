package com.example.otptest;

import android.graphics.Bitmap;

public class Competitor extends Student{

    private String VotesNumber;
    private Bitmap Bitmap;
    private String Id;

    public Competitor(String FirstName, String LastName, String ClassName, String VotesNumber, String Id, Bitmap Bitmap)
    {
        super(FirstName, LastName, ClassName);
        this.VotesNumber = VotesNumber;
        this.Bitmap = Bitmap;
        this.Id = Id;
    }

    public String getVotesNumber() { return VotesNumber; }
    public Bitmap getBitmap() { return Bitmap; }
    public String getId() { return Id; }

    public void setVotesNumber(String votesNumber) { VotesNumber = votesNumber; }
    public void setBitmap(android.graphics.Bitmap bitmap) { Bitmap = bitmap; }
    public void setId(String id) { Id = id; }

    @Override
    public String print() {
        return "Competitor{" + this.FirstName + this.LastName + ", " + this.ClassName + ", " + this.Id + "} = " + this.VotesNumber;
    }


    //public String print() { return "FN: " + this.FirstName + ", LN: " + this.LastName + ", C: " + this.ClassName + ", I: " + this.Id + ", VN: " + this.VotesNumber;}
}

