package ru.valentin_gordienko.loftmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_list);

        List<TransactionListItem> items = new ArrayList<>();

        for(short i = 1; i <= 15 ; i++ ) {
            items.add(new TransactionListItem("Транзакция №" + i, 49 + i + "руб."));
        }

        adapter = new TransactionListItemAdapter();
        adapter.setTransactionItems(items);

        DividerItemDecoration transactionItemDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        transactionItemDivider.setDrawable(getDrawable(R.drawable.transactions_list_divider));

        recyclerView = this.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(transactionItemDivider);
    }
}
