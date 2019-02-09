package ru.valentin_gordienko.loftmoney;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findChildViews();

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager(), this);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.viewPager.setAdapter(mainViewPagerAdapter);
    }

    private void findChildViews(){
        this.viewPager = this.findViewById(R.id.view_pager);
        this.tabLayout = this.findViewById(R.id.tab_layout);
    }
}
