package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMenuBinding
import com.google.android.material.snackbar.Snackbar

class MenuActivity:AppCompatActivity() {
    val binding:ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.movieList -> changeFragment(MenuFragment())
                //R.id.bestMovie -> changeFragment(ReviewFragment())
                //R.id.profile -> changeFragment(LoginFragment())
            }

            return@setOnItemSelectedListener true
        }





    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,fragment)
            .commit()
    }
}