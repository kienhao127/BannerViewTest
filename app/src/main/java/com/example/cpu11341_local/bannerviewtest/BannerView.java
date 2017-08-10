package com.example.cpu11341_local.bannerviewtest;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;


public class BannerView extends LinearLayout {
    CustomViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private int currentPage;
    ViewPagerAdapter viewPagerAdapter;
    boolean isInfinite;
    private Banner[] banners = {new Banner(R.drawable.banner1, 69), new Banner(R.drawable.banner2, "www.google.com"), new Banner(R.drawable.banner3, 96), new Banner(R.drawable.banner4, "www.facebook.com")};

    public BannerView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_layout, this);

        viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        viewPagerAdapter = new ViewPagerAdapter(context, banners, isInfinite);
        viewPager.setOffscreenPageLimit(banners.length);
//        viewPager.setLayerType(ViewPager.LAYER_TYPE_SOFTWARE, null);
        viewPager.setAdapter(viewPagerAdapter);
        //viewPager.setPageTransformer(true, new DepthPageTransformer());

        ToggleButton toggleButtonIsInfinite = (ToggleButton) findViewById(R.id.toggleButtonIsInfinite);
        toggleButtonIsInfinite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isInfinite = buttonView.isChecked();

//                viewPagerAdapter = new ViewPagerAdapter(context, banners, isInfinite);
//                viewPager.setOffscreenPageLimit(1);
//                viewPager.setAdapter(viewPagerAdapter);

                viewPagerAdapter.setInfinite(isInfinite);
                Toast.makeText(getContext(),
                        String.valueOf(isInfinite), Toast.LENGTH_SHORT).show();
            }
        });

        dotscount = banners.length;
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.nonactive_dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.active_dot);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = viewPager.getCurrentItem();

                int pos = currentPage % banners.length;
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageResource(R.drawable.nonactive_dot);
                }
                dots[pos].setImageResource(R.drawable.active_dot);
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = viewPager.getCurrentItem();
                if (!isInfinite && currentPage==banners.length-1 || currentPage==0){
                    viewPager.setPagingEnabled(false);
                }else {
                    viewPager.setPagingEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentPage = viewPager.getCurrentItem();
                if (isInfinite){
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        int pageCount = banners.length + 2;

                        if (currentPage == pageCount - 1){
                            viewPager.setCurrentItem(1, false);
                        } else if (currentPage == 0){
                            viewPager.setCurrentItem(pageCount - 2,false);
                        }
                    }
                }
            }
        });

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2000);
    }
}
