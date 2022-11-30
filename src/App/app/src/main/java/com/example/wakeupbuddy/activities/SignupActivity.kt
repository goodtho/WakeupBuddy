package com.example.wakeupbuddy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wakeupbuddy.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater) //2
        setContentView(binding.root)   //3

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        } //4
        binding.haveAcc.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        } //5
    }

}