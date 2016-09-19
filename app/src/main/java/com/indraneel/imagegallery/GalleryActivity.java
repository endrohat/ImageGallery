package com.indraneel.imagegallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.GalleryAdapter;
import com.indraneel.imagegallery.R;
import model.ImageItem;
import util.VolleySingleton;

/**
 * Activity to display image thumbnails in gallery
 */
public class GalleryActivity extends AppCompatActivity {

    private List<ImageItem> m_imageItems;
    private GalleryAdapter m_adapter;
    private RequestQueue m_RequestQueue;

    private static final String API_URL = "https://jsonplaceholder.typicode.com/photos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        m_imageItems = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery_view);
        m_adapter = new GalleryAdapter(this, m_imageItems);
        recyclerView.setAdapter(m_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        m_RequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        // Request a JSON response
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Result handling
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject item = response.getJSONObject(i);
                                ImageItem imageItem = new ImageItem();
                                imageItem.setThumbnailUrl(item.getString("thumbnailUrl"));
                                imageItem.setUrl(item.getString("url"));
                                imageItem.setTitle(item.getString("title"));
                                m_imageItems.add(imageItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        m_adapter.updateResults(m_imageItems);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GalleryActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        m_RequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        m_RequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
