package com.example.otptest;

import android.graphics.Bitmap;

public class Winner extends Student{

    private String VotesNumber;
    private Bitmap Bitmap;

    public Winner(String FirstName, String LastName, String ClassName, String VotesNumber, Bitmap Bitmap)
    {
        super(FirstName, LastName, ClassName);
        this.VotesNumber = VotesNumber;
        this.Bitmap = Bitmap;
    }

    public String getVotesNumber() { return VotesNumber; }
    public Bitmap getBitmap() { return Bitmap; }

    public void setVotesNumber(String votesNumber) { VotesNumber = votesNumber; }
    public void setBitmap(android.graphics.Bitmap bitmap) { Bitmap = bitmap; }

    @Override
    public String print() {
        return "Winner{" + this.FirstName + this.LastName + ", " + this.ClassName + "}";
    }

}
