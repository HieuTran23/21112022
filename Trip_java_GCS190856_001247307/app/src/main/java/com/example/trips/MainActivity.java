package com.example.trips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.trips.database.BackupEntry;
import com.example.trips.database.DAO;
import com.example.trips.models.Backup;
import com.example.trips.models.Expense;
import com.example.trips.models.Trip;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private NavHostFragment navHostFragment;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private DAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.trip_list, R.id.trip_add
        ).build();

        db = new DAO(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void resetDatabase() {
        db.reset();

        Toast.makeText(this, "Reset database", Toast.LENGTH_LONG).show();
    }

    private void backup() {
        ArrayList<Trip> trip = db.getTripList(null, null, false);
        ArrayList<Expense> expenses = db.getExpenseList(null, null, false);

        if (null != trip && 0 < trip.size() && null != expenses && 0 < expenses.size()) {
            String deviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            Backup backup = new Backup(new Date(), deviceName, trip, expenses);

            FirebaseFirestore.getInstance().collection(BackupEntry.TABLE_NAME)
                    .add(backup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        Log.d("Back up fire store", document.getId());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        } else {
            Toast.makeText(this, "List empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.navMenuSetting_ResetDataBase:
                resetDatabase();

                navController.popBackStack();
                navController.navigate(R.id.home);

                return true;
            case R.id.navMenuSetting_BackupDatabase:
                backup();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}