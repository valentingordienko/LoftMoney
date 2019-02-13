package ru.valentin_gordienko.loftmoney;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_CONSUMPTION = 0;
    static final int PAGE_INCOME = 1;
    static final int PAGE_BALANCE = 2;

    private static final int PAGE_COUNT = 3;
    private Context context;

    public MainViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case PAGE_CONSUMPTION:
                return TransactionListFragment.newInstance(TransactionListItem.TYPE_CONSUMPTION);
            case PAGE_INCOME:
                return TransactionListFragment.newInstance(TransactionListItem.TYPE_INCOME);
            case PAGE_BALANCE:
                return BalanceFragment.newInstance();
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
