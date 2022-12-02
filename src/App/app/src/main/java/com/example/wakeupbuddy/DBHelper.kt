package com.example.wakeupbuddy

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// below is variable for database name
private val DATABASE_NAME = "DB_SQLite/identifier.sqlite"


abstract class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    fun hashString(input: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes).toUpperCase()
    }

    // below method is to get
    // all data from our database
    fun getUserIdByUsername(username : String): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_USERNAME + " = " + username,  null)
    }

    fun insertUser(user : UserModel) : Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_ID, user.id)
        contentValues.put(USER_USERNAME, user.username)
        contentValues.put(USER_PASSWORD, hashString(user.password))
        contentValues.put(USER_birthday, user.birthday)
        contentValues.put(USER_email, user.email)

        val success = db.insert(USER_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun updateUser(user : UserModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(USER_USERNAME, user.username)
        contentValues.put(USER_PASSWORD, hashString(user.password))
        contentValues.put(USER_birthday, user.birthday)
        contentValues.put(USER_email, user.email)

        val success = db.update(USER_TABLE, contentValues, "id="+user.id, null)
        db.close()

        return success
    }

    fun getUserById(userId: String): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + USER_TABLE +" WHERE " + USER_ID + "=" + userId, null)
    }


    companion object{
        // below is variable for database name
        private val DATABASE_NAME = "GEEKS_FOR_GEEKS"

        // below is the variable for database version
        private val DATABASE_VERSION = 1


        // below is the variable for table name
        const val USER_TABLE = "user"
        const val USER_ID = "id"
        const val USER_USERNAME = "username"
        const val USER_PASSWORD = "pasasword"
        const val USER_birthday = "birthday"
        const val USER_email = "email"

        const val ALARM_TABLE = "alarm"
        const val ALARM_ID = "id"
        const val ALARM_USER = "alarm_user"
        const val ALARM_group = "alarm_group"

        const val GROUP_LIST_TABLE = "group_list"
        const val GROUP_LIST_ID = "id"
        const val GROUP_LIST_USER_ID = "user_id"
        const val GROUP_LIST_GROUP_ID = "group_id"

        const val MESSAGE_TABLE = "message"
        const val MESSAGE_ID = "id"
        const val MESSAGE_GROUP_ID = "group_id"
        const val MESSAGE_USER_ID = "user_id"
        const val MESSAGE_MESSAGE = "message"

        const val ACTIVE_ALARM_TABLE = "active_alarm"
        const val ACTIVE_ALARM_ID = "id"
        const val ACTIVE_ALARM_USER_ID = "user_id"
        const val ACTIVE_ALARM_ALARM_ID = "alarm_id"
    }
}