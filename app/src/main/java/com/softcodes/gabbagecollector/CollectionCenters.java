package com.softcodes.gabbagecollector;

import static com.softcodes.gabbagecollector.constants.Links.View_Collection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class CollectionCenters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_centers);
        WebView simpleWebView = findViewById(R.id.view_collection_areas);

        simpleWebView.loadUrl(View_Collection);
    }
}