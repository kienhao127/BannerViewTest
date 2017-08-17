package com.example.cpu11341_local.bannerviewtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.bannerview.Banner;
import com.example.bannerview.BannerView;

import java.util.ArrayList;

public class DemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ArrayList<Banner> arrBannerItems = new ArrayList<>();
        arrBannerItems.add(new Banner(R.drawable.banner1, 69));
        arrBannerItems.add(new Banner(R.drawable.banner2, "www.google.com"));
        arrBannerItems.add(new Banner(R.drawable.banner3, 96));
        arrBannerItems.add(new Banner(R.drawable.banner4, "www.facebook.com"));

        ArrayList<Banner> arrBannerItems2 = new ArrayList<>();
        arrBannerItems2.add(new Banner(R.drawable.banner3, 69));
        arrBannerItems2.add(new Banner(R.drawable.banner1, "www.google.com"));
        arrBannerItems2.add(new Banner(R.drawable.banner2, 96));
        arrBannerItems2.add(new Banner(R.drawable.banner4, "www.facebook.com"));
        arrBannerItems2.add(new Banner(R.drawable.banner2, 69));

        BannerView bannerView = (BannerView) findViewById(R.id.bannerView);
        bannerView.addBannerItems(arrBannerItems);

        bannerView.setInfiniteSlide(true);
        //bannerView.addBannerItem(new Banner(R.drawable.banner1, 69));
        //bannerView.removeBannerItemAt(3);
        //bannerView.replaceAllBannerItems(arrBannerItems2);
    }
}