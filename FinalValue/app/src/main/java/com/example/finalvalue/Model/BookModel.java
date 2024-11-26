package com.example.finalvalue.Model;

import android.graphics.Bitmap;

public class BookModel {
    public int idBook;
    public String nameBook;
    public String descriptionBook;
    public String authorBook;
    public int numberChapter;
    public Bitmap imageBook;
    public String dateRelease;

    public BookModel(int idBook, String nameBook, String descriptionBook, String authorBook, int numberChapter, Bitmap imageBook) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.descriptionBook = descriptionBook;
        this.authorBook = authorBook;
        this.numberChapter = numberChapter;
        this.imageBook = imageBook;
    }

    public BookModel(String nameBook, String descriptionBook, String authorBook, int numberChapter, Bitmap imageBook) {
        this.nameBook = nameBook;
        this.descriptionBook = descriptionBook;
        this.authorBook = authorBook;
        this.numberChapter = numberChapter;
        this.imageBook = imageBook;
    }

    public BookModel(int idBook, String nameBook, String descriptionBook, String authorBook, int numberChapter, Bitmap imageBook, String dateRelease) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.descriptionBook = descriptionBook;
        this.authorBook = authorBook;
        this.numberChapter = numberChapter;
        this.imageBook = imageBook;
        this.dateRelease = dateRelease;
    }

    public BookModel(String nameBook, String descriptionBook, String authorBook, int numberChapter, Bitmap imageBook, String dateRelease) {
        this.nameBook = nameBook;
        this.descriptionBook = descriptionBook;
        this.authorBook = authorBook;
        this.numberChapter = numberChapter;
        this.imageBook = imageBook;
        this.dateRelease = dateRelease;
    }
}
