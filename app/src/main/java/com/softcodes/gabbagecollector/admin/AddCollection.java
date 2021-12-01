package com.softcodes.gabbagecollector.admin;

import static com.softcodes.gabbagecollector.constants.Links.ADD_Collection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softcodes.gabbagecollector.Login;
import com.softcodes.gabbagecollector.R;

import com.softcodes.gabbagecollector.constants.RequestHandler;

import java.util.HashMap;

public class AddCollection extends AppCompatActivity {
    EditText vill,paris, division, cit,pickpoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_collection);
        vill = findViewById(R.id.village);
        paris = findViewById(R.id.parish);
        division = findViewById(R.id.divisionn);
        cit = findViewById(R.id.city);
        pickpoi = findViewById(R.id.exactpick_point);

    }

    public void add_location(View view) {

        final String villae = vill.getText().toString();
        final String parisl = paris.getText().toString();
        final String diviz = division.getText().toString();
        final String cityl = cit.getText().toString();
        final String exact = pickpoi.getText().toString();

        if(TextUtils.isEmpty(villae)){
            vill.setError("Field Required!");
            vill.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(parisl)){
            paris.setError("Field Required!");
            paris.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(diviz)){
            division.setError("Field Required!");
            division.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(cityl)){
            cit.setError("Field Required!");
            cit.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(exact)){
            pickpoi.setError("Field Required!");
            pickpoi.requestFocus();
            return;
        }



        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Register_User extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(AddCollection.this);

            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tSaving...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("village", villae);
                params.put("parish", parisl);
                params.put("division", diviz);
                params.put("city", cityl);
                params.put("exactplace", exact);

                return requestHandler.sendPostRequest(ADD_Collection, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();


                    Toast.makeText(AddCollection.this, s, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(AddCollection.this, AdminHome.class));

                    vill.setText("");
                    paris.setText("");
                    division.setText("");
                    cit.setText("");
                    pickpoi.setText("");



            }


        }

        Register_User register_user = new Register_User();
        register_user.execute();

    }
}