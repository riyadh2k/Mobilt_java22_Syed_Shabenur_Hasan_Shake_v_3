package com.example.shake_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.fragment.app.FragmentTransaction;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button switchButton;
    boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchButton = findViewById(R.id.switch_button);

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFragmentDisplayed) {
                    // Ta bort fragmentet om det redan visas
                    removeFragment();
                } else {
                    // Visa fragmentet om det inte visas
                    displayFragment();
                }
            }
        });
    }

    private void displayFragment() {
        MyFragment myFragment = new MyFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, myFragment).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment).commit();
        }
        isFragmentDisplayed = false;
    }
}