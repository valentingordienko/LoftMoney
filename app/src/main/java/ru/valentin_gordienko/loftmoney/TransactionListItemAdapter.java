package ru.valentin_gordienko.loftmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class TransactionListItemAdapter extends RecyclerView.Adapter<TransactionListItemAdapter.ItemViewHolder> {

    private List<TransactionListItem> transactionItems = Collections.emptyList();

    public void setTransactionItems(List<TransactionListItem> transactionItems) {
        this.transactionItems = transactionItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.transactions_list_item, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        TransactionListItem item = transactionItems.get(i);
        itemViewHolder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.transactionItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionName;
        private TextView transactionPrice;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.transactionName = itemView.findViewById(R.id.transaction_name);
            this.transactionPrice = itemView.findViewById(R.id.transaction_price);
        }

        public void bindItem(TransactionListItem item){
            this.transactionName.setText(item.getName());
            this.transactionPrice.setText(item.getPrice());
        }
    }
}
