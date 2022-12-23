package com.jobcard.applicatoion.Service;

import java.io.IOException;

import com.google.zxing.WriterException;

public interface IQRCodeService {
    byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException;
    void generateQRCodeImage(String text, int width, int height, String filePath);
}
