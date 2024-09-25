package com.example.st_nobrainerquizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DBConnect (context: Context) {} /* : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    //DB CONTEXT
    companion object
    {
        //variables
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NoBrainerQuizApp.db"

        private const val TABLE_USERS = "User"
        private const val COLUMN_USER_EMAIL = "emailID"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_BIO = "bio"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_IMAGE = "image"

        private const val TABLE_SCORES = "Score"
        private const val COLUMN_PK = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_SCORE = "score"


    }

    //creates SQLite DB and tables
    override fun onCreate(db: SQLiteDatabase?)
    {
        val createUserTable = "CREATE TABLE IF NOT EXISTS $TABLE_USERS (" +
                "$COLUMN_USER_EMAIL TEXT PRIMARY KEY," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_BIO TEXT," +
                "$COLUMN_GENDER TEXT," +
                "$COLUMN_AGE INTEGER," +
                "$COLUMN_IMAGE BLOB)"


        val createScoreTable = "CREATE TABLE IF NOT EXISTS $TABLE_SCORES (" +
                "$COLUMN_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_SCORE INTEGER)"


        db?.execSQL(createUserTable)
        db?.execSQL(createScoreTable)

    }

    //upgrade override for tables
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }


    //CREATES ENTRY INTO DB SCORES TABLE
    fun insertScores(email: String, score: Int)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_SCORE, score)
        db.insert(TABLE_SCORES, null, values)
        db.close()

    }

    //CREATES ENTRY INTO DB USERS TABLE
    fun insertUser(emailID: String, username: String, bio: String, gender: String, age: String, image: ByteArray)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_EMAIL, emailID)
        contentValues.put(COLUMN_USERNAME, username)
        contentValues.put(COLUMN_BIO, bio)
        contentValues.put(COLUMN_GENDER, gender)
        contentValues.put(COLUMN_AGE, age)
        contentValues.put(COLUMN_IMAGE, image)
        db.insert(TABLE_USERS, null, contentValues)
        db.close()
    }



    //RETRIEVES DB ENTRIES TO LIST FOR USER PROFILE
    fun retrieveUser(emailID: String): UserSQLiteDB //EMAIL TO ADD
    {
        val user = UserSQLiteDB()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE  $COLUMN_USER_EMAIL = '$emailID'"
        val cursor = db.rawQuery(query, null)

        cursor.use{
            if (it.moveToFirst())
            {
                do
                {
                    user.emailID = it.getString(0)
                    user.username = it.getString(1)
                    user.bio = it.getString(2)
                    user.gender = it.getString(3)
                    user.age = it.getString(4)
                    //user.image= it.getBlob(5)

                }
                while (it.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return user
    }

    //RETRIEVE ALL RECORDS MATCHING USER AND GETS LATEST SCORE
    fun retrieveScore(email: String): Int
    {
        var scores = 0
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_SCORES WHERE $COLUMN_EMAIL = '$email'"
        val cursor = db.rawQuery(query, null)

        cursor.use{
            if (it.moveToFirst())
            {
                do
                {
                    val log = it.getInt(2)
                    scores = log


                }
                while (it.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return scores
    }
}
*/



