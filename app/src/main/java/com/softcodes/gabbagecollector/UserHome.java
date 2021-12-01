package com.softcodes.gabbagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.softcodes.gabbagecollector.constants.SharedPrefManager;
import com.softcodes.gabbagecollector.constants.User;

public class UserHome extends AppCompatActivity  implements View.OnClickListener{
    TextView welcome;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        View card1 = findViewById(R.id.report_gb);
        View card2 = findViewById(R.id.collectio);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        welcome = findViewById(R.id.pname);
        User user = SharedPrefManager.getInstance(this).getUser();
        final String g = user.getFname();
        welcome.setText("Welcome Back"+" "+ g+"!");
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.report_gb:
                Intent intent = new Intent(UserHome.this, PostGabbage.class);
                startActivity(intent);
                break;
            case R.id.collectio:
                Intent i = new Intent(UserHome.this, CollectionCenters.class);
                startActivity(i);
                break;



        }
    }

    public void logout_u(MenuItem item) {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        Intent i = new Intent(UserHome.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }
}