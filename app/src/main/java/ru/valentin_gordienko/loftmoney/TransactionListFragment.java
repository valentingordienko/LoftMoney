package ru.valentin_gordienko.loftmoney;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment {

    private static final String TAG = "TransactionListFragment";
    private static final int ADD_TRANSACTION_REQUEST_CODE = 1;

    public static final String KEY_NAME = "TYPE";

    private TransactionListItemAdapter adapter;
    private String fragmentType;
    private Api api;
    private SwipeRefreshLayout preLoader;

    public static TransactionListFragment newInstance(String type) {
        TransactionListFragment instance = new TransactionListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_NAME, type);
        instance.setArguments(arguments);
        return instance;
    }

    public TransactionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new TransactionListItemAdapter();

        if (this.getArguments() == null) {
            throw new IllegalStateException("Fragment arguments are NULL");
        }

        this.fragmentType = this.getArguments().getString(KEY_NAME);

        App app = (App) Objects.requireNonNull(this.getActivity()).getApplication();
        api = app.getApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.preLoader = view.findViewById(R.id.preLoader);
        int preLoaderColor = requireContext().getResources().getColor(R.color.colorAccent);
        preLoader.setColorSchemeColors(preLoaderColor);
        preLoader.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTransactions();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Context context = this.requireContext();

        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        DividerItemDecoration transactionItemDivider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        transactionItemDivider.setDrawable(context.getDrawable(R.drawable.transactions_list_divider));
        recyclerView.addItemDecoration(transactionItemDivider);

        this.getTransactions();
    }

    private void getTransactions() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString(AuthActivity.AUTH_PROPERTY, null);

        if (token == null) return;

        Call call = this.api.getTransactions(fragmentType, token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                preLoader.setRefreshing(false);
                List<TransactionListItem> transactions = (List<TransactionListItem>) response.body();
                adapter.setTransactionItems(transactions);
            }

            @Override
            public void onFailure(Call call, Throwable error) {
                preLoader.setRefreshing(false);
                Log.e(TAG, "getTransactions: ", error);
            }
        });
    }

    public void onClickFloatActionButton() {
        Intent intent = new Intent(requireContext(), AddTransactionActivity.class);
        startActivityForResult(intent, ADD_TRANSACTION_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_TRANSACTION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String transactionName = data.getStringExtra(AddTransactionActivity.KEY_NAME);
            String transactionPrice = data.getStringExtra(AddTransactionActivity.KEY_PRICE);

            Log.d(TAG, "onActivityResult: transaction name = " + transactionName);
            Log.d(TAG, "onActivityResult: transaction price = " + transactionPrice);

            TransactionListItem transactionListItem = new TransactionListItem(
                    transactionName, Double.valueOf(transactionPrice), fragmentType
            );

            adapter.addTransactionItem(transactionListItem);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
