package ru.valentin_gordienko.loftmoney;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    private TextView availableBalance;
    private TextView allIncome;
    private TextView allConsumption;
    private DiagramView diagramView;
    private SwipeRefreshLayout preLoader;
    private String textPostDecorator;

    public BalanceFragment() {
        // Required empty public constructor
    }

    public static BalanceFragment newInstance() {

        Bundle args = new Bundle();

        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textPostDecorator = getString(R.string.rubleSign);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findChildViews(view);
        settingPreLoader();
        getBalance();
    }

    private void findChildViews(View view) {
        availableBalance = view.findViewById(R.id.available_balance);
        allIncome = view.findViewById(R.id.all_income);
        allConsumption = view.findViewById(R.id.all_consumption);
        diagramView = view.findViewById(R.id.balance_diagram);
        preLoader = view.findViewById(R.id.preLoader);
    }

    private void settingPreLoader(){
        int preLoaderColor = requireContext().getResources().getColor(R.color.colorAccent);
        preLoader.setColorSchemeColors(preLoaderColor);
        preLoader.setOnRefreshListener(this::getBalance);
    }

    private void getBalance() {

        App app = (App) Objects.requireNonNull(this.getActivity()).getApplication();

        if (app == null) return;

        Call<BalanceResponse> call = app.getApi().balance(app.getToken());

        call.enqueue(new Callback<BalanceResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                preLoader.setRefreshing(false);
                BalanceResponse balanceResponse = response.body();
                int totalIncome = balanceResponse.getTotalIncome();
                int totalConsumption = balanceResponse.getTotalConsumption();

                int balance = totalIncome - totalConsumption;

                availableBalance.setText(String.valueOf(balance) + textPostDecorator);
                allIncome.setText(String.valueOf(totalIncome) + textPostDecorator);
                allConsumption.setText(String.valueOf(totalConsumption) + textPostDecorator);
                diagramView.setData(totalIncome, totalConsumption);

            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable error) {
                preLoader.setRefreshing(false);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //Update balance by switch from  income fragment
        if(isVisibleToUser && isVisible()) getBalance();
    }
}
