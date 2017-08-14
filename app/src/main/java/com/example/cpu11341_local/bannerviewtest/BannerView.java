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

    public void setBanners(Banner[] banners) {
        this.banners = banners;
    }

    public BannerView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_layout, this);

        viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        viewPagerAdapter = new ViewPagerAdapter(context, banners, isInfinite);
        viewPager.setOffscreenPageLimit(banners.length);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setCurrentItem(1, true);
        ToggleButton toggleButtonIsInfinite = (ToggleButton) findViewById(R.id.toggleButtonIsInfinite);
        toggleButtonIsInfinite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isInfinite = buttonView.isChecked();
                viewPagerAdapter.setInfinite(isInfinite);
                if (isInfinite) {
                    viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                }else {
                    if (viewPager.getCurrentItem() == banners.length+1){
                        viewPager.setCurrentItem(1, false);
                    }
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(banners.length, false);
                    }
                }
                int position = viewPager.getCurrentItem();
                if (!isInfinite && position==banners.length){
                    viewPager.setAllowedSwipeDirection(SwipeDirection.left2right);
                } else {
                    if (!isInfinite && position == 1) {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.right2left);
                    } else {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                    }
                }
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

        viewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = viewPager.getCurrentItem();
                int pageCount = banners.length + 2;
                int pos = 1;
                if (currentPage == pageCount - 1){
                    pos = 1;
                } else if (currentPage == 0){
                    pos = pageCount - 2;
                } else {
                    pos = currentPage;
                }

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageResource(R.drawable.nonactive_dot);
                }
                dots[pos-1].setImageResource(R.drawable.active_dot);

            }

            @Override
            public void onPageSelected(int position) {
                if (!isInfinite && position==banners.length){
                    viewPager.setAllowedSwipeDirection(SwipeDirection.left2right);
                } else {
                    if (!isInfinite && position == 1) {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.right2left);
                    } else {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (isInfinite){
                    int pageCount = viewPagerAdapter.getCount();
                    if (state == ViewPager.SCROLL_STATE_IDLE){
                        if (currentPage == pageCount - 1){
                            viewPager.setCurrentItem(1, false);

                        } else if (currentPage == 0){
                            viewPager.setCurrentItem(pageCount - 2, false);
                        }
                    }
                }
            }
        });

        autoScroll();
    }

    public void autoScroll(){
        currentPage = viewPager.getCurrentItem();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                Log.d("Current page", String.valueOf(currentPage));
                if (currentPage == viewPagerAdapter.getCount()-1) {
                    viewPager.setCurrentItem(1, false);
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
