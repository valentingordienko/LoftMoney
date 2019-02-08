package ru.valentin_gordienko.loftmoney;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
