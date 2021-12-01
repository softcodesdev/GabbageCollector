package com.softcodes.gabbagecollector.admin;

import static com.softcodes.gabbagecollector.constants.Links.LOAD_GARBAGE_CENTERS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.softcodes.gabbagecollector.BuildConfig;
import com.softcodes.gabbagecollector.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softcodes.gabbagecollector.adapters.Tip_Adapter;
import com.softcodes.gabbagecollector.database.Tips_Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class ViewGarbage extends AppCompatActivity implements Tip_Adapter.OnItemClickListenerTips{
    List<Tips_Model> tips_list;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView no_pdcts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_garbage);
        recyclerView = findViewById(R.id.displayed_job_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_pdcts = findViewById(R.id.no_jobs_yet);

        tips_list = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Retrieving Garbage areas Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        load_tips();
    }

    private void load_tips() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOAD_GARBAGE_CENTERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject s = array.getJSONObject(i);

                                    tips_list.add(new Tips_Model(
                                            s.getString("id"),
                                            s.getString("areaname"),
                                            s.getString("areaDescription"),
                                            s.getString("garbage_photo"),
                                            s.getString("fname")

                                    ));
                                    Tip_Adapter adapter = new Tip_Adapter(ViewGarbage.this, tips_list);
                                    recyclerView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                    adapter.setOnClickListenerTips(ViewGarbage.this);

                                }


                            }
                            else {
                                no_pdcts.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                recyclerView.setVisibility(View.INVISIBLE);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onshareTip(int position) {

        Tips_Model clickedItem = tips_list.get(position);
        String jb_name = clickedItem.getTname();
        String jb_ref = clickedItem.getTdescription();
        try {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = jb_name;
            shareMessage = shareMessage + "Read more..." +" "+jb_ref + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}