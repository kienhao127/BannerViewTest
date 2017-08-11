package com.example.cpu11341_local.bannerviewtest;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Banner[] banners;
    private int pos = 0;
    private boolean isInfinite;
    public ViewPagerAdapter(Context context, Banner[] banners, boolean isInfinite) {
        this.context = context;
        this.banners = banners;
        this.isInfinite = isInfinite;
    }

    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
            return banners.length + 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container,final int position) {
        Log.d("Is Infinite", String.valueOf(isInfinite));
        int currentPositon = position % banners.length;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_banner_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if (position == 0) {
            imageView.setImageResource(banners[banners.length-1].getImageID());
        } else {
            if (position == (banners.length + 1)) {
                imageView.setImageResource(banners[0].getImageID());
            }else{
                imageView.setImageResource(banners[position - 1].getImageID());
            }
        }

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentPositon = position - 1;
//                if (banners[currentPositon].getAction_type() == 1){
//                    Intent intent = new Intent(context, OpenRoomActivity.class);
//                    intent.putExtra("RoomID", banners[currentPositon].getRoomID());
//                    context.startActivity(intent);
//                } else {
//                    String url = banners[currentPositon].getLink();
//                    if (!url.startsWith("http://") && !url.startsWith("https://"))
//                        url = "http://" + url;
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    context.startActivity(browserIntent);
//                }
//            }
//        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
