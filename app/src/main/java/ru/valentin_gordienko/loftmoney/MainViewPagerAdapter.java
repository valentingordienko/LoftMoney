package ru.valentin_gordienko.loftmoney;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;
    private static final int PAGE_CONSUMPTION = 0;
    private static final int PAGE_INCOME = 1;
    private static final int PAGE_BALANCE = 2;

    private Context context;

    public MainViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case PAGE_CONSUMPTION:
                return TransactionListFragment.newInstance(TransactionListFragment.TYPE_CONSUMPTION);
            case PAGE_INCOME:
                return TransactionListFragment.newInstance(TransactionListFragment.TYPE_INCOME);
            case PAGE_BALANCE:
                return new BalanceFragment();
            default:
                throw new IllegalStateException("Unknown fragment type");
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case PAGE_CONSUMPTION:
                return context.getString(R.string.tabCaptionConsumption);
            case PAGE_INCOME:
                return context.getString(R.string.tabCaptionIncome);
            case PAGE_BALANCE:
                return context.getString(R.string.tabCaptionBalance);
            default:
                return "";
        }
    }
}
