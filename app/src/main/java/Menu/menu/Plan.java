package Menu.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;



public class Plan extends FragmentActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homeActivity = new Intent(Plan.this, MainActivity.class);
                    startActivity(homeActivity);
                    return true;
                case R.id.navigation_dashboard:
                    Intent imageActivity = new Intent(Plan.this, Plan.class);
                    //startActivity(imageActivity);
                    return true;
                case R.id.navigation_notifications:
                    Intent gpsActivity = new Intent(Plan.this, Gps.class);
                    startActivity(gpsActivity);
                    return true;
                case R.id.navigation_reality:
                    Intent realityActivity = new Intent(Plan.this, Reality.class);
                    startActivity(realityActivity);
                    return true;

            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}