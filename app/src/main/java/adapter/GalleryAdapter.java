package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.indraneel.imagegallery.ImageDisplayActivity;
import com.indraneel.imagegallery.R;
import model.ImageItem;

/** Adapter for displaying images in a recyclerview */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageHolder> {

    private List<ImageItem> m_imageItems;
    private final LayoutInflater m_layoutInflater;
    private final Activity m_activity;

    public GalleryAdapter(Activity activity, List<ImageItem> imageItems) {
        m_layoutInflater = LayoutInflater.from(activity);
        m_activity = activity;
        m_imageItems = imageItems;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageHolder holder = new ImageHolder(m_layoutInflater.inflate(R.layout.gallery_item, parent, false));
        return holder;
    }

    @Override
    public void onViewRecycled(ImageHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.m_imageView);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Glide
                .with(m_activity)
                .load(m_imageItems.get(position).getThumbnailUrl())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(holder.m_imageView);
    }

    @Override
    public int getItemCount() {
        return m_imageItems.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        ImageView m_imageView ;
        ImageHolder (View itemView) {
            super(itemView);
            m_imageView = (ImageView) itemView.findViewById(R.id.imageItem);
            m_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(m_activity, ImageDisplayActivity.class);
                    intent.putExtra("title", m_imageItems.get(getAdapterPosition()).getTitle());
                    intent.putExtra("url", m_imageItems.get(getAdapterPosition()).getUrl());
                    m_activity.startActivity(intent);
                }
            });
        }
    }

    public void updateResults(List<ImageItem> imageItems) {
        m_imageItems = imageItems;
        notifyDataSetChanged();
    }
}
