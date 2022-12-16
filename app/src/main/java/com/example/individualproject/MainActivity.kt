package com.example.individualproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.individualproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}