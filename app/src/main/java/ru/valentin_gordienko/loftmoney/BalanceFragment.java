package ru.valentin_gordienko.loftmoney;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private Api api;


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

        App app = (App) Objects.requireNonNull(this.getActivity()).getApplication();
        api = app.getApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findChildViews(view);
    }

    private void findChildViews(View view){
        availableBalance = view.findViewById(R.id.available_balance);
        allIncome = view.findViewById(R.id.all_income);
        allConsumption = view.findViewById(R.id.all_consumption);
        diagramView = view.findViewById(R.id.balance_diagram);
    }

    private void getBalance(){
        String token = AuthActivity.getToken(this.requireContext());
        if (token == null) return;

        Call<BalanceResponse> call = this.api.balance(token);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();
                int totalIncome = balanceResponse.getTotalIncome();
                int totalConsumption = balanceResponse.getTotalConsumption();

                int balance = totalIncome - totalConsumption;

                availableBalance.setText(String.valueOf(balance));
                allIncome.setText(String.valueOf(totalIncome));
                allConsumption.setText(String.valueOf(totalConsumption));
                diagramView.setData(totalIncome, totalConsumption);

            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable error) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            getBalance();
        }
    }
}
