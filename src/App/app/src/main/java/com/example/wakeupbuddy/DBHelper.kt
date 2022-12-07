package com.example.wakeupbuddy

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.com.example.wakeupbuddy.models.*
import java.security.MessageDigest


private val DATABASE_NAME = "DB_SQLite/identifier.sqlite"



abstract class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    fun insertUser(user : UserModel) : Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_ID, user.id)
        contentValues.put(USER_USERNAME, user.username)
        contentValues.put(USER_PASSWORD, hashPassword(user.password))
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_EMAIL, user.email)

        val success = db.insert(USER_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun updateUser(user : UserModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(USER_USERNAME, user.username)
        contentValues.put(USER_PASSWORD, hashPassword(user.password))
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_EMAIL, user.email)

        val success = db.update(USER_TABLE, contentValues, "id="+user.id, null)
        db.close()
        return success
    }


    fun getUserById(userId: String): UserModel? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USER_TABLE WHERE $USER_ID=$userId", null)

        //ToDo: Not sure if needed
        cursor.moveToFirst()
        return mockUser(cursor)
    }

    fun getAlarmsByUserId(userId: String): List<AlarmModel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USER_TABLE WHERE $USER_ID=$userId", null)
        val listAlarms = ArrayList<AlarmModel>()

        if(cursor.moveToFirst()){
            do{
                listAlarms.add(mockAlarm(cursor))
            }while (cursor.moveToNext())
        }

        return listAlarms
    }

    fun getUserByNameAndPassword(username: String, password: String): UserModel {
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM " + USER_TABLE +" WHERE " +
                        USER_USERNAME + "=" + username + " AND " +
                        USER_PASSWORD + "=" + hashPassword(password), null)

         return mockUser(cursor)
    }

    fun deleteUserById(user: UserModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(USER_ID, user.id)
        val success = db.delete(USER_TABLE, "id="+user.id, null)
        db.close()
        return success
    }

    private fun mockUser(cursor : Cursor):UserModel{
        val user = UserModel()
        user.id = cursor.getString(cursor.getColumnIndex(USER_USERNAME))
        user.password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD))
        user.username = cursor.getString(cursor.getColumnIndex(USER_USERNAME))
        user.email = cursor.getString(cursor.getColumnIndex(USER_EMAIL))
        user.name = cursor.getString(cursor.getColumnIndex(USER_NAME))

        return user
    }

    private fun mockGroupList(cursor : Cursor):GroupModel{
        val groupList = GroupModel()
        groupList.id = cursor.getString(cursor.getColumnIndex(GROUP_LIST_ID))
        groupList.groupAdmin = cursor.getString(cursor.getColumnIndex(GROUP_LIST_GROUPADMIN))
        groupList.name = cursor.getString(cursor.getColumnIndex(GROUP_LIST_NAME))

        return groupList
    }

    private fun mockGroupMember(cursor : Cursor):GroupMemberModel{
        val groupMember = GroupMemberModel()
        groupMember.userId = cursor.getString(cursor.getColumnIndex(GROUP_MEMBER_USER_ID))
        groupMember.groupId = cursor.getString(cursor.getColumnIndex(GROUP_MEMBER_GROUP_ID))

        return groupMember
    }

    private fun mockMessage(cursor : Cursor):MessageModel{
        val message = MessageModel()
        message.id = cursor.getString(cursor.getColumnIndex(MESSAGE_ID))
        message.groupId = cursor.getString(cursor.getColumnIndex(MESSAGE_GROUP_ID))
        message.userId = cursor.getString(cursor.getColumnIndex(MESSAGE_USER_ID))
        message.message = cursor.getString(cursor.getColumnIndex(MESSAGE_MESSAGE))
        message.time = cursor.getString(cursor.getColumnIndex(MESSAGE_TIME))

        return message
    }

    private fun mockAlarm(cursor : Cursor):AlarmModel{
        val alarm = AlarmModel()
        alarm.id = cursor.getString(cursor.getColumnIndex(ALARM_ID))
        alarm.time = cursor.getString(cursor.getColumnIndex(ALARM_TIME))
        alarm.active = cursor.getInt(cursor.getColumnIndex(ALARM_ACTIVE))
        alarm.name = cursor.getString(cursor.getColumnIndex(ALARM_NAME))
        alarm.groupID = cursor.getString(cursor.getColumnIndex(GROUP_ID))

        return alarm
    }

    private fun hashPassword(input: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
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
        const val USER_NAME = "name"
        const val USER_EMAIL = "email"

        const val ALARM_TABLE = "alarm"
        const val ALARM_ID = "id"
        const val GROUP_ID = "group_id"
        const val ALARM_TIME = "time"
        const val ALARM_ACTIVE = "active"
        const val ALARM_NAME = "name"

        const val GROUP_LIST_TABLE = "group_list"
        const val GROUP_LIST_ID = "id"
        const val GROUP_LIST_GROUPADMIN = "group_admin"
        const val GROUP_LIST_NAME = "name"

        const val GROUP_MEMBER_TABLE = "group_member"
        const val GROUP_MEMBER_USER_ID = "user_id"
        const val GROUP_MEMBER_GROUP_ID = "group_id"

        const val MESSAGE_TABLE = "message"
        const val MESSAGE_ID = "id"
        const val MESSAGE_GROUP_ID = "group_id"
        const val MESSAGE_USER_ID = "user_id"
        const val MESSAGE_MESSAGE = "message"
        const val MESSAGE_TIME = "time"
    }
}