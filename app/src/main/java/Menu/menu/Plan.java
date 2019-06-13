package Menu.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


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

        ArrayList<String> theWay=Gps.THEWAY;

        RelativeLayout rl = findViewById(R.id.rl);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageView iv = new ImageView(getApplicationContext());
        iv.setLayoutParams(lp);

        ImageView plan = new ImageView(this);
        String strg="plan";
        int imageResource = getResources().getIdentifier(strg, "drawable", getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        plan.setImageDrawable(image);
        rl.addView(plan);

        if (theWay.size()!=0){
            for(int j=0;j<theWay.size();j++){
                if (j==0){
                    ImageView dep = new ImageView(this);
                    String tstdep=theWay.get(j);
                    String strgdep="departs"+tstdep;
                    int imageResourcedep = getResources().getIdentifier(strgdep, "drawable", getPackageName());
                    Drawable imagedep = getResources().getDrawable(imageResourcedep);
                    dep.setImageDrawable(imagedep);
                    rl.addView(dep);

                }
                else if (j==theWay.size()-1) {
                    ImageView arr = new ImageView(this);
                    String tstarr = theWay.get(j);
                    String strgarr = "arrivees" + tstarr;
                    int imageResourcearr = getResources().getIdentifier(strgarr, "drawable", getPackageName());
                    Drawable imagearr = getResources().getDrawable(imageResourcearr);
                    arr.setImageDrawable(imagearr);
                    rl.addView(arr);
                }
                else {
                    ImageView che = new ImageView(this);
                    String tstche = theWay.get(j);
                    String strgche = "chemins" + tstche;
                    int imageResourceche = getResources().getIdentifier(strgche, "drawable", getPackageName());
                    Drawable imageche = getResources().getDrawable(imageResourceche);
                    che.setImageDrawable(imageche);
                    rl.addView(che);
                }
            }
        }

    }
}