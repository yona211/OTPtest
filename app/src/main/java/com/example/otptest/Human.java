package com.example.otptest;

import android.graphics.Bitmap;

import java.util.function.DoubleToIntFunction;

public interface Human {
    String getVotesNumber();
    String getFirstName();
    String getLastName();
    String getClassName();
    Bitmap getBitmap();

    public String print();
}
