package com.harutyun.githubtrendingrepositories.presentation.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.harutyun.githubtrendingrepositories.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}