package koreanlearning.hangul.serokorean.beginnerone.quiz.DBhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.Category;
import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.Question;

public class DBhelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "samplequiz2.db";
    private static final int DB_VER = 1;

    private static DBhelper instance;

    public static synchronized DBhelper getInstance(Context context){
        if(instance == null){
            instance = new DBhelper(context);
        }
        return instance;
    }

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Category> getAllCategories(){
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Category;", null);
        List<Category> categories = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Category category = new Category(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Image")));
                categories.add(category);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return categories;
    }

    //get all question from DB
    public List<Question> getQuestionByCategory(int categoryIndex){
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("Select * From Question Where CategoryID = %d Order by Random() Limit 30", categoryIndex), null);
        List<Question> questions = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Question question = new Question(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("QuestionText")),
                        cursor.getString(cursor.getColumnIndex("QuestionImage")),
                        cursor.getString(cursor.getColumnIndex("AnswerA")),
                        cursor.getString(cursor.getColumnIndex("AnswerB")),
                        cursor.getString(cursor.getColumnIndex("AnswerC")),
                        cursor.getString(cursor.getColumnIndex("AnswerD")),
                        cursor.getString(cursor.getColumnIndex("CorrectAnswer")),
                        cursor.getInt(cursor.getColumnIndex("IsImageQuestion")) == 0?Boolean.FALSE: Boolean.TRUE,
                        cursor.getInt(cursor.getColumnIndex("CategoryID")));

                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return questions;
    }
}
