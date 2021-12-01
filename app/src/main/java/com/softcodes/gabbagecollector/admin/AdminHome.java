package com.softcodes.gabbagecollector.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.softcodes.gabbagecollector.Login;
import com.softcodes.gabbagecollector.PostGabbage;
import com.softcodes.gabbagecollector.R;
import com.softcodes.gabbagecollector.UserHome;
import com.softcodes.gabbagecollector.constants.SharedPrefManager;
import com.softcodes.gabbagecollector.constants.User;

public class AdminHome extends AppCompatActivity  implements View.OnClickListener{
    TextView welcome;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        View card1 = findViewById(R.id.admviewreport_gb);
        View card2 = findViewById(R.id.addcollectio);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        welcome = findViewById(R.id.adminpname);
        User user = SharedPrefManager.getInstance(this).getUser();
        final String g = user.getFname();
        welcome.setText("Welcome Back"+" "+ g+"!");
    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.admviewreport_gb:
                Intent intent = new Intent(AdminHome.this, ViewGarbage.class);
                startActivity(intent);
                break;
            case R.id.addcollectio:
                Intent i = new Intent(AdminHome.this, AddCollection.class);
                startActivity(i);
                break;



        }
    }

    public void adminlogout_u(MenuItem item) {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        Intent i = new Intent(AdminHome.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }
}