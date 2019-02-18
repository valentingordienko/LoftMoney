package ru.valentin_gordienko.loftmoney;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionListItemAdapter extends RecyclerView.Adapter<TransactionListItemAdapter.ItemViewHolder> {

    private List<TransactionListItem> transactionItems = Collections.emptyList();
    private TransactionListItemAdapterListener listener = null;
    private SparseBooleanArray selectedTransactions = new SparseBooleanArray();

    void setTransactionItems(List<TransactionListItem> transactionItems) {
        this.transactionItems = transactionItems;
        notifyDataSetChanged();
    }

    void setListener(TransactionListItemAdapterListener listener) {
        this.listener = listener;
    }

    void toggleSelectedTransaction(int position){
        if(selectedTransactions.get(position, false)){
            selectedTransactions.put(position, false);
        } else {
            selectedTransactions.put(position, true);
        }
        notifyItemChanged(position);
    }

    void clearSelectedTransactions(){
        selectedTransactions.clear();
        notifyDataSetChanged();
    }

    List<Integer> getSelectedTransactions(){
        List<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < selectedTransactions.size(); i++){
            selectedIndexes.add(selectedTransactions.keyAt(i));
        }

        return selectedIndexes;
    }

    TransactionListItem removeTransaction(int position){
        TransactionListItem transactionListItem = this.transactionItems.get(position);
        this.transactionItems.remove(position);
        notifyItemRemoved(position);
        return transactionListItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.transactions_list_item, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        TransactionListItem item = transactionItems.get(position);
        itemViewHolder.bindItem(item, selectedTransactions.get(position));
        itemViewHolder.setListener(item, listener, position);
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

        public void bindItem(TransactionListItem item, boolean selected){
            this.transactionName.setText(item.getName());
            this.transactionPrice.setText(String.valueOf(item.getPrice()));
            itemView.setSelected(selected);
        }

        void setListener(TransactionListItem item, TransactionListItemAdapterListener listener, int position){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onClickItem(item, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null) {
                        listener.onLongClickItem(item, position);
                    }
                    return true;
                }
            });
        }
    }
}
