package com.softcodes.gabbagecollector;

import static com.softcodes.gabbagecollector.constants.Links.ADD_USER;

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

import com.softcodes.gabbagecollector.constants.RequestHandler;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText fname, lname, address, phone, pass, checkmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.new_user_fname);
        lname = findViewById(R.id.new_user_lname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.inputt_phone);
        pass = findViewById(R.id.new_user_password);
        checkmail = findViewById(R.id.inputt_email);
    }

    public void user_create_account(View view) {

        final String firstname = fname.getText().toString();
        final String lastname = lname.getText().toString();
        final String useraddress = address.getText().toString();
        final String phoneNo = phone.getText().toString();
        final String password = pass.getText().toString();
        final String email = checkmail.getText().toString();
        // validations now

        if (TextUtils.isEmpty(firstname)) {

            fname.requestFocus();
            fname.setError("First Name is Required!");
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            lname.setError("Given name is required!");
            lname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(useraddress)) {
            address.requestFocus();
            address.setError("Address is Required!");
            return;
        }
        if (TextUtils.isEmpty(phoneNo)) {
            phone.setError("Email is Required!");
            phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            pass.requestFocus();
            pass.setError("Password is Required!");
            return;
        }


        if (TextUtils.isEmpty(email)) {
            checkmail.setError("Email Address is Required");
            checkmail.requestFocus();
            return;
        }
        // checking email validity and refer to what is stored in the db
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            checkmail.setError("Enter a valid email");
            checkmail.requestFocus();
            return;
        }


        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Register_User extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(Register.this);

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

                params.put("fname", firstname);
                params.put("lname", lastname);
//                params.put("adddress", useraddress);
                params.put("userPassword", password);
                params.put("phone", phoneNo);
                params.put("userEmail", email);

                return requestHandler.sendPostRequest(ADD_USER, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                if(s.equalsIgnoreCase("")){
                    phone.setError("Phone already Registered");
                    phone.requestFocus();
                    return;
                }
                else {

                    Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Register.this, Login.class));
                    fname.setText("");
                    lname.setText("");
                    address.setText("");
                    pass.setText("");
                    phone.setText("");
                    checkmail.setText("");
                }

            }


        }

        Register_User register_user = new Register_User();
        register_user.execute();


    }
}

