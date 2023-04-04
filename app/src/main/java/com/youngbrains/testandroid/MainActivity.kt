package com.youngbrains.testandroid
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

import com.example.mvvmdemomaster.utils.addReplaceFragment
import com.youngbrains.testandroid.R.id.fNameEt
import com.youngbrains.testandroid.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addReplaceFragment(
            UserListFragment(),
            addFragment = true,
            backAllow = false,
            frameLayout = R.id.frameLayout
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {


                val builder = AlertDialog.Builder(this)
                builder.setTitle("Add User Data")
                builder.setMessage("Please enter below user details")
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
                builder.setView(view)
                val alertDialog = builder.create()

                val fNameEt = view.findViewById<EditText>(R.id.fNameEt)
                val lNameEt = view.findViewById<EditText>(R.id.lNameEt)
                val emailEt = view.findViewById<EditText>(R.id.emailEt)
                val userDao = AppDatabase.getInstance(this@MainActivity).userDao()

                builder.setPositiveButton("OK") { dialog, which ->

                    CoroutineScope(Dispatchers.IO).launch {

                        val userItem: List<User> = userDao.getAll()
                        var userItemLength: Int = userItem.size
                        userItemLength++
                        userDao.insertUser(User(first_name = fNameEt.text.toString(), last_name = lNameEt.text.toString(), email = emailEt.text.toString(),
                            avatar = "",
                            id = userItemLength
                        ))
                        alertDialog.dismiss()
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->

                }

                alertDialog.show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}