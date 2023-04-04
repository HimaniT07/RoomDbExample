package com.youngbrains.testandroid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.youngbrains.testandroid.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert
    fun insertUser(vararg users: User)

    @Query("DELETE FROM users")
    fun deleteData()
}