package com.example.finalvalue.Model;

public class ChapterModel {
    public int idChapter;
    public int bookID;
    public int chapNumber;
    public String chapDescription;

    public ChapterModel(int bookID, int chapNumber, String chapDescription) {
        this.bookID = bookID;
        this.chapNumber = chapNumber;
        this.chapDescription = chapDescription;
    }

    public ChapterModel(int idChapter, int bookID, int chapNumber, String chapDescription) {
        this.idChapter = idChapter;
        this.bookID = bookID;
        this.chapNumber = chapNumber;
        this.chapDescription = chapDescription;
    }
}
