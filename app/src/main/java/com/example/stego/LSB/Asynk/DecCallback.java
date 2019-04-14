package com.example.stego.LSB.Asynk;

import com.example.stego.LSB.scr.Steganography;

/**
 * This the callback interface for TextEncoding AsyncTask.
 */

public interface DecCallback {

    void onStartTextDecoding();

    void onCompleteTextDecoding(Steganography result);

}
