package com.softcodes.gabbagecollector;

import static com.softcodes.gabbagecollector.constants.Links.URL_LOGIN;

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

import com.softcodes.gabbagecollector.admin.AdminHome;
import com.softcodes.gabbagecollector.constants.RequestHandler;
import com.softcodes.gabbagecollector.constants.SharedPrefManager;
import com.softcodes.gabbagecollector.constants.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText userPhone, userPassword;
    private long backPressedTime;
    private Toast backToast;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPhone = findViewById(R.id.input_registered_phone);
        userPassword = findViewById(R.id.input_password);
        progressDialog = new ProgressDialog(this);
    }

    public void forgotpassword(View view) {

    }

    public void register(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    public void signin(View view) {
        final String phone = userPhone.getText().toString().trim();
        final  String pass = userPassword.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            userPhone.requestFocus();
            userPhone.setError("Please Enter Phone!");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            userPassword.requestFocus();
            userPassword.setError("Password Required!");
            return;
        }
        if (phone.length() < 9) {
            userPhone.setError("Valid  Phone number is required");
            userPhone.requestFocus();
            return;
        }
        UserLoginFunction(phone,pass);

    }



    @SuppressLint("StaticFieldLeak")
    public void UserLoginFunction(final String user_name, final String password) {


/**
 *
 */
        @SuppressWarnings("deprecation")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("phone", user_name);
                params.put("userPassword", password);
                return requestHandler.sendPostRequest(URL_LOGIN, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                progressDialog.show();
                progressDialog.setMessage("Checking Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(httpResponseMsg);
//                    Toast.makeText(getApplicationContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("userId"),
                                userJson.getString("userPassword"),
                                userJson.getString("userEmail"),
                                userJson.getString("userRole"),
                                userJson.getString("phone"),
                                userJson.getString("fname"),
                                userJson.getString("lname")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        if (user.getUserRole().equals("clerk")) {
                            startActivity(new Intent(getApplicationContext(), UserHome.class));
                            finish();

                        } else if (user.getUserRole().equals("admin")) {
                            startActivity(new Intent(getApplicationContext(), AdminHome.class));
                            finish();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(user_name, password);
    }





    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    public void report_as_anonymous(View view) {
    }
}