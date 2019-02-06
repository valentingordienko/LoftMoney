package ru.valentin_gordienko.loftmoney;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment {

    private TransactionListItemAdapter adapter;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new TransactionListItemAdapter();
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

        adapter.setTransactionItems(this.createTemporaryTransactios(15));
    }

    private List<TransactionListItem> createTemporaryTransactios(int count){

        List<TransactionListItem> items = new ArrayList<>();

        for(int i = 1; i <= count ; i++ ) {
            items.add(new TransactionListItem("Транзакция №" + i, 49 + i + "руб."));
        }

        return  items;
    }
}
