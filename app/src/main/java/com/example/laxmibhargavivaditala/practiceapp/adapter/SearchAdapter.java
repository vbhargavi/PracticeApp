package com.example.laxmibhargavivaditala.practiceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laxmibhargavivaditala.practiceapp.BusinessDetailActivity;
import com.example.laxmibhargavivaditala.practiceapp.R;
import com.example.laxmibhargavivaditala.practiceapp.model.Business;
import com.example.laxmibhargavivaditala.practiceapp.service.ServiceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class SearchAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Business> mBusinesses;

    public SearchAdapter(Context context, ArrayList<Business> businesses) {
        mContext = context;
        mBusinesses = businesses;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            final Business business = mBusinesses.get(position);
            holder.update(business);
        }
    }

    @Override
    public int getItemCount() {
        if (mBusinesses != null) {
            return mBusinesses.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView restaurantTextView;
        private TextView restaurantRatingTextView;
        private TextView displayPhoneTextView;
        private Context mContext;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            mContext = context;
            imageView = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurantTextView = (TextView) itemView.findViewById(R.id.restaurant_title);
            restaurantRatingTextView = (TextView) itemView.findViewById(R.id.rating_text);
            displayPhoneTextView = (TextView) itemView.findViewById(R.id.display_phone);

        }

        public void update(final Business business) {
            restaurantRatingTextView.setText(String.valueOf(business.getRating()));
            restaurantTextView.setText(business.getName());
            displayPhoneTextView.setText(business.getDisplayPhone());

            int width = imageView.getLayoutParams().width;
            int height = imageView.getLayoutParams().height;
            String imageUrl = business.getImageUrl();

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BusinessDetailActivity.class);
                    intent.putExtra(BusinessDetailActivity.EXTRA_ID, business.getId());
                    mContext.startActivity(intent);

                }
            });

            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(width, height)
                    .centerCrop()
                    .into(imageView);
        }
    }
}
