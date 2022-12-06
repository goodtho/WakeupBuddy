package com.example.wakeupbuddy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.view.*
import java.com.example.wakeupbuddy.models.UserModel


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {

            val name: String = binding.root.et_name.text.toString()
            val username: String = binding.root.et_username.text.toString()
            val email: String = binding.root.et_email.text.toString()
            val password: String = binding.root.et_password.text.toString()
            val confirmPassword: String = binding.root.et_confirm_password.text.toString()

            var signUpCorrect = true

            if (password != confirmPassword) {
                val errorTV = binding.root.signup_error_message as TextView
                errorTV.text = "Error! Passwords don't match! Please try again."
                errorTV.visibility = TextView.VISIBLE
                signUpCorrect = false
            }

            val wkbApp = applicationContext as WakeUpBuddyApp
            if (wkbApp.userExists(email)) {
                val errorTV = binding.root.signup_error_message as TextView
                errorTV.text = "Error! Email already in use! Please choose a different one."
                errorTV.visibility = TextView.VISIBLE
                signUpCorrect = false
            } else if (wkbApp.userExists(username)) {
                val errorTV = binding.root.signup_error_message as TextView
                errorTV.text = "Error! Username already in use! Please choose a different one."
                errorTV.visibility = TextView.VISIBLE
                signUpCorrect = false
            }

            if (signUpCorrect) {
                val newUser = UserModel(username=username, password=password, email=email, name=name)
                wkbApp.createUser(newUser)
                wkbApp.setCurrentUser(newUser)

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

        }
        binding.haveAcc.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}