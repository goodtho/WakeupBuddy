package com.example.wakeupbuddy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)  //2
        setContentView(binding.root) //3

        binding.btnLogin.setOnClickListener { view: View ->
            val wkbApp = applicationContext as WakeUpBuddyApp

            val tryUsername = binding.root.et_email.text.toString()
            val tryPassword = binding.root.et_password.text.toString()

            val user = wkbApp.getUser(tryUsername)

            if (user != null && tryPassword == user.password) {
                wkbApp.setCurrentUser(user)
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                binding.root.login_error_message.visibility = TextView.VISIBLE
            }

        } //4
        binding.haventAcc.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        } //5
    }
}