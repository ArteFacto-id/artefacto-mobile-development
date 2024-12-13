package com.example.artefacto001.ui.auth.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.artefacto001.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

    }
}