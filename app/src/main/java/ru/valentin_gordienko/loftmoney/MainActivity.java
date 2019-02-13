package ru.valentin_gordienko.loftmoney;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findChildViews();

        setSupportActionBar(toolbar);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager(), this);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.viewPager.setAdapter(mainViewPagerAdapter);
    }

    private void findChildViews(){
        this.toolbar = this.findViewById(R.id.toolbar);
        this.viewPager = this.findViewById(R.id.view_pager);
        this.tabLayout = this.findViewById(R.id.tab_layout);
    }
}
