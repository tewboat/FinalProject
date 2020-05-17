package com.example.finalproject.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.finalproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    NavController navController;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().findItem(R.id.navigation_todo).setChecked(true);
        navController = Navigation.findNavController(this, R.id.fragment);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("fragmentChange", "fragmentCall: starting fragment");
        int mItemId = menuItem.getItemId();
        switch (mItemId){
            case R.id.navigation_todo:
                navController.navigate(R.id.todoFragment);
                break;
            case R.id.navigation_habits:
                navController.navigate(R.id.habitsFragment);
                break;
            case R.id.navigation_goals:
                navController.navigate(R.id.goalsFragment);
                break;
        }
        return true;
    }
}

