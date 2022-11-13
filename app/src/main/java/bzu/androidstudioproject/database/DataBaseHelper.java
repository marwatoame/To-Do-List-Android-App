package bzu.androidstudioproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bzu.androidstudioproject.Tasks.Task;
import bzu.androidstudioproject.adapters.ToDoAdapter;
import bzu.androidstudioproject.loggedinuser.LoggedInUser;
import bzu.androidstudioproject.registration.User;

public class DataBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    public DataBaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USER(FirstName TEXT, LastName TEXT, Email TEXT PRIMARY KEY, Password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE TASK(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Title TEXT, Description TEXT, Date TEXT, Email TEXT, Status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void setStatus(Task task)
    {
        if(task.getStatus() == false)
            sqLiteDatabase.execSQL("UPDATE TASK SET Status = 1 WHERE ID = "+task.getId()+";");
        else
            sqLiteDatabase.execSQL("UPDATE TASK SET Status = 0 WHERE ID = "+task.getId()+";");
    }

    public void deleteTask(Task task)
    {
        sqLiteDatabase.execSQL("DELETE FROM TASK WHERE ID = "+task.getId());
    }

    public void addUser(String firstName, String lastName, String Email, String Password)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("Email", Email);
        contentValues.put("Password", Password);
        sqLiteDatabase.insert("USER", null, contentValues);
    }

    public void addNewTask(String title, String description, String date) throws ParseException
    {
        sqLiteDatabase.execSQL("INSERT INTO TASK(Title, Description, Date, Email, Status) VALUES('"+title+"','"+description+"','"+date+"','"+LoggedInUser.loggedIn.getEmail()+"',0);");
    }

    public Cursor getAllTasks()
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TASK where Email ='"+ LoggedInUser.loggedIn.getEmail()+"';", null);
        return cursor;
    }

    public Cursor getUser(User user){
        String em = "select * from " + "USER" + " where "+ "EMAIL" + " = '" + user.getEmail() +"' AND Password = '"+user.getPassword()+"';" ;
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cu = sql.rawQuery(em, null);
        return cu;
    }


}
