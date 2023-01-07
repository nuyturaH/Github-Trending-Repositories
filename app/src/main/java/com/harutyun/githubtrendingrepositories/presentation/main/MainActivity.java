package com.harutyun.githubtrendingrepositories.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.harutyun.githubtrendingrepositories.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}