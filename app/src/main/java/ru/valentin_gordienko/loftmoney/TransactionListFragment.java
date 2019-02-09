package ru.valentin_gordienko.loftmoney;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment {

    public static final String KEY_NAME = "TYPE";
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_CONSUMPTION = 2;

    private TransactionListItemAdapter adapter;
    private int fragmentType;

    public static TransactionListFragment newInstance(int type) {
        TransactionListFragment instance = new TransactionListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_NAME, type);
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

        if(this.getArguments() == null) {
            throw new IllegalStateException("Fragment arguments are NULL");
        }

        this.fragmentType = this.getArguments().getInt(KEY_NAME, TYPE_DEFAULT);

        if(fragmentType == TYPE_DEFAULT) {
            throw new IllegalStateException("Fragment type is UNKNOWN");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Context context = this.requireContext();

        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        DividerItemDecoration transactionItemDivider = new DividerItemDecoration( context, DividerItemDecoration.VERTICAL);
        transactionItemDivider.setDrawable(context.getDrawable(R.drawable.transactions_list_divider));
        recyclerView.addItemDecoration(transactionItemDivider);

        int transactionListSize = this.fragmentType == 1 ? 12 : this.fragmentType == 2 ? 3 : 0;

        adapter.setTransactionItems(this.createTemporaryTransactions(transactionListSize));
    }

    private List<TransactionListItem> createTemporaryTransactions(int count){

        List<TransactionListItem> items = new ArrayList<>();

        for(int i = 1; i <= count ; i++ ) {
            items.add(new TransactionListItem("Транзакция №" + i, 49 + i + "руб."));
        }

        return  items;
    }
}
