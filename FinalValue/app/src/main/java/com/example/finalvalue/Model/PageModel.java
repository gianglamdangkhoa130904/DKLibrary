package com.example.finalvalue.Model;

import android.graphics.Bitmap;

public class PageModel {
    public int idPage;
    public int chapterID;
    public int pageNumber;
    public Bitmap pageImage;

    public PageModel(int idPage, int chapterID, int pageNumber, Bitmap pageImage) {
        this.idPage = idPage;
        this.chapterID = chapterID;
        this.pageNumber = pageNumber;
        this.pageImage = pageImage;
    }

    public PageModel(int chapterID, int pageNumber, Bitmap pageImage) {
        this.chapterID = chapterID;
        this.pageNumber = pageNumber;
        this.pageImage = pageImage;
    }
}
