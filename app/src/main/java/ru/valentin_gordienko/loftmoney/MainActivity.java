package ru.valentin_gordienko.loftmoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findChildViews();

        setSupportActionBar(toolbar);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager(), this);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Fragment> fragments = getSupportFragmentManager().getFragments();

                for(Fragment fragment : fragments){
                    if(fragment instanceof TransactionListFragment && fragment.getUserVisibleHint()){
                        ((TransactionListFragment) fragment).onClickFloatActionButton();
                    }
                }
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private void findChildViews(){  toolbar = this.findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        floatingActionButton = findViewById(R.id.float_action_button);
    }

    private class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {

            if(actionMode != null) {
                actionMode.finish();
            }

            switch(position){
                case MainViewPagerAdapter.PAGE_INCOME:
                case MainViewPagerAdapter.PAGE_CONSUMPTION:
                    floatingActionButton.show();
                    break;
                case MainViewPagerAdapter.PAGE_BALANCE:
                    floatingActionButton.hide();
                    break;

            }
        }
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);

        actionMode = mode;
        floatingActionButton.hide();
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGreyBlue));
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        actionMode = null;
        floatingActionButton.show();
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
}
