package Menu.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;


public abstract class Reality extends FragmentActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    Intent homeActivity = new Intent(Reality.this, MainActivity.class);
                    startActivity(homeActivity);
                    return true;
                case R.id.navigation_dashboard:
                    finish();
                    Intent imageActivity = new Intent(Reality.this, Plan.class);
                    startActivity(imageActivity);
                    return true;
                case R.id.navigation_notifications:
                    finish();
                    Intent gpsActivity = new Intent(Reality.this, Gps.class);
                    startActivity(gpsActivity);
                    return true;
                case R.id.navigation_reality:
                    finish();
                    Intent realityActivity = new Intent(Reality.this, Reality.class);
                    startActivity(realityActivity);
                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reality);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}