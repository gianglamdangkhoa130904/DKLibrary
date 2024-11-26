package com.example.finalvalue.Customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.finalvalue.Admin.AdminActivity;
import com.example.finalvalue.Model.AdminModel;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.Model.CategoryBookModel;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.Model.PageModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DB_Name = "DKLib";
    private static final int DB_Version = 1;
    private final String[] table_name;
    private final String[] customer_columns;
    private final String[] admin_columns;
    private final String[] category_columns;
    private final String[] book_columns;
    private final String[] chapter_colunms;
    private final String[] pages_colunms;
    private final String[] categoryBook_columns;
    private final String[] viewer_colunms;
    private final String[] love_colunms;
    private byte[] imagebytearr;
    private ByteArrayOutputStream byteArrayOutputStream;

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
        this.context = context;
        customer_columns = new String[]{"ID", "Username", "Password", "Name", "Email", "DateOfBirth", "ImageCus"};
        admin_columns = new String[]{"ID", "Username", "Password", "Role"};
        category_columns = new String[]{"IDCategory", "CategoryName"};
        book_columns = new String[]{"IDBook", "NameBook","DescriptionBook","AuthorBook","NumberOfChapter","ImageBook", "ReleaseDate"};
        categoryBook_columns = new String[]{"BookID", "CategoryID"};
        chapter_colunms = new String[]{"IDChap","BookID","ChapNumber","PageDescription"};
        pages_colunms = new String[]{"IDPage","ChapID","PageNumber","PageImage"};
        viewer_colunms = new String[]{"IDViewer", "CusID", "BookID"};
        love_colunms = new String[]{"IDLove", "CusID", "BookID"};
        table_name = new String[]{"Customer","Admin","Category","Book","CategoryBook","Chapter","Pages", "Viewer", "Love"};
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create customer
        String query = "CREATE TABLE "+table_name[0]+" ("+customer_columns[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+customer_columns[1]+" TEXT, "+customer_columns[2]+" TEXT, "+customer_columns[3]+" TEXT, " +customer_columns[4]+" TEXT, "+customer_columns[5]+" TEXT, "+customer_columns[6]+" BLOB);";
        db.execSQL(query);
        //create admin
        query = "CREATE TABLE "+table_name[1]+" ("+admin_columns[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+admin_columns[1]+" TEXT, "+admin_columns[2]+" TEXT, "+admin_columns[3]+" TEXT);";
        db.execSQL(query);
        // create manager --> Admin
        query = "INSERT INTO "+table_name[1]+" VALUES(1,"+ "\"admin\",\"admin\",\"Manager\""+");";
        db.execSQL(query);
        //create Category
        query = "CREATE TABLE "+table_name[2]+" ("+category_columns[0]+" TEXT PRIMARY KEY, "+category_columns[1]+" TEXT);";
        db.execSQL(query);
        //create Book
        query = "CREATE TABLE "+table_name[3]+" ("+book_columns[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+book_columns[1]+" TEXT, "+book_columns[2]+" TEXT, "+book_columns[3]+" TEXT, " +book_columns[4]+" INTERGER, "+book_columns[5]+" BLOB, "+book_columns[6]+" TEXT);";
        db.execSQL(query);
        //create CategoryBook
        query = "CREATE TABLE "+table_name[4]+" ("+categoryBook_columns[0]+" INTEGER, "+categoryBook_columns[1]+" TEXT, constraint PK_BookCategory primary key("+categoryBook_columns[0]+","+categoryBook_columns[1]+"), constraint FK_BookCategory_Book foreign key("+categoryBook_columns[0]+") references "+table_name[3]+"("+book_columns[0]+"), constraint FK_BookCategory_Category foreign key("+categoryBook_columns[1]+") references "+table_name[2]+"("+category_columns[0]+"));";
        db.execSQL(query);
        //create Chapter
        query = "CREATE TABLE "+table_name[5]+" ("+chapter_colunms[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+chapter_colunms[1]+" INTEGER, "+chapter_colunms[2]+" INTEGER, "+chapter_colunms[3]+" TEXT, constraint FK_Chapter_Book foreign key("+chapter_colunms[1]+") references "+table_name[3]+"("+book_columns[0]+"));";
        db.execSQL(query);
        //create Pages
        query = "CREATE TABLE "+table_name[6]+" ("+pages_colunms[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+pages_colunms[1]+" INTEGER, "+pages_colunms[2]+" INTEGER, "+pages_colunms[3]+" BLOB, constraint FK_Pages_Chapter foreign key("+pages_colunms[1]+") references "+table_name[5]+"("+chapter_colunms[0]+"));";
        db.execSQL(query);
        //create Viewer
        query = "CREATE TABLE "+table_name[7]+" ("+viewer_colunms[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+viewer_colunms[1]+" INTEGER, "+viewer_colunms[2]+" INTEGER, constraint FK_Viewer_Customer foreign key("+viewer_colunms[1]+") references "+table_name[0]+"("+customer_columns[0]+"), constraint FK_Viewer_Book foreign key("+viewer_colunms[2]+") references "+table_name[3]+"("+book_columns[0]+"));";
        db.execSQL(query);
        //create Love
        query = "CREATE TABLE "+table_name[8]+" ("+love_colunms[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+love_colunms[1]+" INTEGER, "+love_colunms[2]+" INTEGER, constraint FK_Love_Customer foreign key("+love_colunms[1]+") references "+table_name[0]+"("+customer_columns[0]+"), constraint FK_Love_Book foreign key("+love_colunms[2]+") references "+table_name[3]+"("+book_columns[0]+"));";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name[0]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[1]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[2]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[3]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[4]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[5]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[6]);
        db.execSQL("DROP TABLE IF EXISTS "+table_name[7]);
        onCreate(db);
    }
    void add_customer(CustomerModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = a.imageCus;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(customer_columns[1], a.username);
        cv.put(customer_columns[2], a.password);
        cv.put(customer_columns[3], a.name);
        cv.put(customer_columns[4], a.email);
        cv.put(customer_columns[5], a.date_birth);
        cv.put(customer_columns[6], imagebytearr);

        db.insert(table_name[0],null,cv);
    }
    public void update_customer(CustomerModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = a.imageCus;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(customer_columns[1], a.username);
        cv.put(customer_columns[2], a.password);
        cv.put(customer_columns[3], a.name);
        cv.put(customer_columns[4], a.email);
        cv.put(customer_columns[5], a.date_birth);
        cv.put(customer_columns[6], imagebytearr);
        db.update(table_name[0],cv,customer_columns[0] + "=?", new String[]{String.valueOf(a.id)});
    }
    public Cursor read_customer(String username){
        String query = "SELECT * FROM "+table_name[0]+" WHERE "+customer_columns[1]+" LIKE "+"'"+ username+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public Cursor get_customer_by_ID(int ID){
        String query = "SELECT * FROM "+table_name[0]+" WHERE "+customer_columns[0]+" = " + ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public Cursor readAllDataCustomer(){
        String query = "SELECT * FROM "+table_name[0];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllDataAdmin(){
        String query = "SELECT * FROM "+table_name[1];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllDataAdmin_exceptManager(){
        String query = "SELECT * FROM "+table_name[1]+" WHERE "+admin_columns[3]+" NOT LIKE "+"'Manager'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor read_admin(String username){
        String query = "SELECT * FROM "+table_name[1]+" WHERE "+admin_columns[1]+" LIKE "+"'"+ username+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(query,null);
        return cursor;
    }
    public Cursor get_All_category(){
        String query = "SELECT * FROM "+table_name[2];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void add_category(CategoryModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(category_columns[0], a.categoryID);
        cv.put(category_columns[1], a.categoryName);

        db.insert(table_name[2],null,cv);
    }
    public Cursor get_category_by_ID(String categoryID){
        String query = "SELECT * FROM "+table_name[2]+" WHERE "+category_columns[0]+" LIKE "+"'"+ categoryID+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public void update_category(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(category_columns[1], name);
        db.update(table_name[2],cv,category_columns[0] + "=?", new String[]{id});
    }
    public  void delete_category(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name[2],category_columns[0] + "=?", new String[]{id});
    }
    public void add_admin(AdminModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(admin_columns[1], a.admin_username);
        cv.put(admin_columns[2], a.admin_password);
        cv.put(admin_columns[3], a.admin_role);
        db.insert(table_name[1],null,cv);
    }
    public void update_admin(AdminModel adminModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(admin_columns[1], adminModel.admin_username);
        cv.put(admin_columns[2], adminModel.admin_password);
        cv.put(admin_columns[3], adminModel.admin_role);
        db.update(table_name[1],cv,admin_columns[0] + "=?", new String[]{String.valueOf(adminModel.admin_ID)});
    }
    public  void delete_admin(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name[1],admin_columns[0] + "=?", new String[]{String.valueOf(id)});
    }
    public void add_book(BookModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = a.imageBook;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(book_columns[1], a.nameBook);
        cv.put(book_columns[2], a.descriptionBook);
        cv.put(book_columns[3], a.authorBook);
        cv.put(book_columns[4], a.numberChapter);
        cv.put(book_columns[5], imagebytearr);
        cv.put(book_columns[6], a.dateRelease);
        db.insert(table_name[3],null,cv);
    }
    public Cursor get_All_book(){
        //Lấy tất cả data từ bảng Customer
        String query = "SELECT * FROM "+table_name[3];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor get_Book_by_name(String nameBook){
        String query = "SELECT * FROM "+table_name[3]+" WHERE "+book_columns[1]+" LIKE "+"'"+ nameBook+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(query,null);
        return cursor;
    }
    public void update_book(BookModel bookModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = bookModel.imageBook;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(book_columns[1], bookModel.nameBook);
        cv.put(book_columns[2], bookModel.descriptionBook);
        cv.put(book_columns[3], bookModel.authorBook);
        cv.put(book_columns[4], bookModel.numberChapter);
        cv.put(book_columns[5], imagebytearr);
        db.update(table_name[3],cv,book_columns[0] + "=?", new String[]{String.valueOf(bookModel.idBook)});
    }
    public  void delete_book(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name[3],book_columns[0] + "=?", new String[]{String.valueOf(id)});
    }
    public Cursor get_All_BookCategory(){
        String query = "SELECT "+book_columns[1]+", "+category_columns[1]+" FROM "+table_name[2]+", "+table_name[3]+", "+table_name[4]+" WHERE "+book_columns[0]+" = "+categoryBook_columns[0]+" AND "+"'"+category_columns[0]+"' LIKE '"+categoryBook_columns[1]+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void add_categoryBook(CategoryBookModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(categoryBook_columns[0], a.bookID);
        cv.put(categoryBook_columns[1], a.categoryID);
        db.insert(table_name[4], null, cv);
    }
    public Cursor get_category_by_bookID(int id){
        String query = "SELECT * FROM "+table_name[4]+" WHERE "+categoryBook_columns[0]+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor get_category_of_book(int id){
        String query = "SELECT IDCategory, CategoryName\n" +
                "From CategoryBook, Category\n" +
                "WHERE CategoryID = IDCategory and BookID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor get_book_by_categoryID(String categoryID){
        String query = "SELECT "+book_columns[0]+", "+book_columns[1]+", "+book_columns[2]+", "+book_columns[3]+", "+book_columns[4]+", "+book_columns[5]+" FROM "+table_name[4]+","+table_name[3]+" WHERE "+categoryBook_columns[1]+" LIKE '"+categoryID+"' AND "+categoryBook_columns[0]+" = "+book_columns[0];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void add_chapter(ChapterModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(chapter_colunms[1], a.bookID);
        cv.put(chapter_colunms[2], a.chapNumber);
        cv.put(chapter_colunms[3], a.chapDescription);
        db.insert(table_name[5], null,cv);
    }
    public void update_chapter(ChapterModel a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(chapter_colunms[1], a.bookID);
        cv.put(chapter_colunms[2], a.chapNumber);
        cv.put(chapter_colunms[3], a.chapDescription);
        db.update(table_name[5],cv,chapter_colunms[0] + "=?", new String[]{String.valueOf(a.idChapter)});
    }
    public Cursor get_chapter_by_bookID(int bookID){
        String query = "SELECT * FROM "+table_name[5]+" WHERE "+chapter_colunms[1]+" = "+bookID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void add_pages(PageModel a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = a.pageImage;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(pages_colunms[1], a.chapterID);
        cv.put(pages_colunms[2], a.pageNumber);
        cv.put(pages_colunms[3], imagebytearr);
        db.insert(table_name[6], null, cv);
    }
    public void update_pages(PageModel a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = a.pageImage;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imagebytearr = byteArrayOutputStream.toByteArray();

        cv.put(pages_colunms[1], a.chapterID);
        cv.put(pages_colunms[2], a.pageNumber);
        cv.put(pages_colunms[3], imagebytearr);
        db.update(table_name[6],cv,pages_colunms[0] + "=?", new String[]{String.valueOf(a.idPage)});
    }
    public Cursor get_page_by_chapterID(int chapterID){
        String query = "SELECT * FROM "+table_name[6]+" WHERE "+pages_colunms[1]+" = "+chapterID+" order by "+pages_colunms[0]+" asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void add_viewer(int IDCus, int IDBook){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(viewer_colunms[1], IDCus);
        cv.put(viewer_colunms[2], IDBook);
        db.insert(table_name[7],null,cv);
    }
    public Cursor get_viewer_byIDCus(int IDCus){
        //String query = "SELECT "+viewer_colunms[1]+", "+viewer_colunms[2]+", "+book_columns[1]+", count(*) FROM "+table_name[7]+", "+table_name[3]+" WHERE "+viewer_colunms[1]+" = "+IDCus+" AND "+viewer_colunms[2]+" = "+book_columns[0]+" Group BY "+viewer_colunms[1]+", "+viewer_colunms[2];
        String query = "SELECT CusID, BookID, NameBook, count(*)\n" +
                "from Viewer, Book\n" +
                "WHERE CusID = "+IDCus+" and BookID = IDBook\n" +
                "GROUP BY CusID, BookID";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public Cursor get_viewer_byIDBook(int IDBook){
        //String query = "SELECT "+viewer_colunms[1]+", "+viewer_colunms[2]+", "+book_columns[1]+", count(*) FROM "+table_name[7]+", "+table_name[3]+" WHERE "+viewer_colunms[1]+" = "+IDCus+" AND "+viewer_colunms[2]+" = "+book_columns[0]+" Group BY "+viewer_colunms[1]+", "+viewer_colunms[2];
        String query = "SELECT CusID, BookID, count(*)\n" +
                "from Viewer, Book\n" +
                "WHERE BookID = "+IDBook+" and BookID = IDBook\n" +
                "GROUP BY CusID, BookID";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public Cursor get_All_viewer(String state){
        String query = "SELECT BookID, NameBook, count(*)\n" +
                "from Viewer, Book\n" +
                "WHERE BookID = IDBook\n" +
                "GROUP BY BookID, NameBook\n" +
                "ORDER BY count(*) "+state;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = null;
        if(db != null)
            cursor_customer = db.rawQuery(query,null);
        return cursor_customer;
    }
    public void add_loveBook(int IDCus, int IDBook){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(love_colunms[1], IDCus);
        cv.put(love_colunms[2], IDBook);
        db.insert(table_name[8],null,cv);
    }
    public void delete_loveBook(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name[8], love_colunms[0] +"=?", new String[]{String.valueOf(ID)});
    }
    public Cursor get_ALl_loveBook(int IDCus){
        String query = "SELECT IDLove, NameBook\n" +
                "From love, Customer, Book\n" +
                "where CusID = ID AND BookID = IDBook AND CusID = "+ IDCus;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor get_love_Book(int IDCus, int IDBook){
        String query = "SELECT IDLove, CusID, BookID\n" +
                "FROM Love, Book, Customer\n" +
                "WHERE CusID = ID AND BookID = IDBook AND CusID = "+IDCus+" AND BookID = "+IDBook;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
