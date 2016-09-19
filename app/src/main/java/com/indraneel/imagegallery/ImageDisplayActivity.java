package com.indraneel.imagegallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/* Activity to display image title and image */
public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_info);
        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String url = extras.getString("url");
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(title);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Glide
                .with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }
}
