package com.softcodes.gabbagecollector;

import static com.softcodes.gabbagecollector.constants.Links.URL_ADD_GARBAGE;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.softcodes.gabbagecollector.constants.RequestHandler;
import com.softcodes.gabbagecollector.constants.SharedPrefManager;
import com.softcodes.gabbagecollector.constants.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PostGabbage extends AppCompatActivity {
    EditText tname,tdescription;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Uri imageUri;
    Bitmap bitmap;
    private ImageView imageView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_gabbage);

        tname = findViewById(R.id.tip_n);
        tdescription = findViewById(R.id.tip_decription);
        imageView = findViewById(R.id.tip_imaged);
        progressDialog = new ProgressDialog(this);
    }
    public void get_post_image(View view) {
        SelectImage();
    }

    public void post_gabage(View view) {
        final String name = tname.getText().toString();
        final String descr = tdescription.getText().toString();

        User user = SharedPrefManager.getInstance(this).getUser();
        final String logged_id = String.valueOf(user.getUserID());
        if(TextUtils.isEmpty(name)){
            tname.requestFocus();
            tname.setError("Enter Location!");
            return;
        }

        if(TextUtils.isEmpty(descr)){
            tdescription.setError("Enter Description");
            tdescription.requestFocus();
            return;
        }
        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class AddingTips extends AsyncTask<Void, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Posting Garbage collection Center....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {


                ByteArrayOutputStream byteArrayOutputStreamObject;

                byteArrayOutputStreamObject = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

                byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

                final String tip_image = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("areaname", name);
                params.put("garbage_photo", tip_image);
                params.put("areaDescription", descr);
                params.put("userId",logged_id);

                return requestHandler.sendPostRequest(URL_ADD_GARBAGE, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if(s.equalsIgnoreCase("Garbage allocated!. Thank you!")){
                    tname.setText("");
                    tdescription.setText("");
                    imageView.setImageDrawable(getDrawable(R.drawable.logo));
                    Toast.makeText(PostGabbage.this, s, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(PostGabbage.this, s, Toast.LENGTH_LONG).show();
                }


            }


        }

        AddingTips addingTips = new AddingTips();
        addingTips.execute();


    }



    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PostGabbage.this);
        builder.setTitle("Choose Garbage Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);


                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Attach Image"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }

        });

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {

            Bundle bundle = data.getExtras();
            assert bundle != null;
            bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}