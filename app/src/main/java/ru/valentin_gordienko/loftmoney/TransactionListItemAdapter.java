package ru.valentin_gordienko.loftmoney;

import android.annotation.SuppressLint;
import android.content.Context;
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
            selectedTransactions.delete(position);
        } else {
            selectedTransactions.put(position, true);
        }
        notifyItemChanged(position);
    }

    void clearSelectedTransactions(){
        selectedTransactions.clear();
        notifyDataSetChanged();
    }

    int getSelectedTransactionsCount(){
        return selectedTransactions.size();
    }

    List<Integer> getSelectedTransactions(){
        List<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < selectedTransactions.size(); i++){
            selectedIndexes.add(selectedTransactions.keyAt(i));
        }

        return selectedIndexes;
    }

    TransactionListItem removeTransaction(int position){
        TransactionListItem transactionListItem = transactionItems.get(position);
        transactionItems.remove(position);
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
        return transactionItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionName;
        private TextView transactionPrice;
        private Context context;
        private String textPostDecorator;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionName = itemView.findViewById(R.id.transaction_name);
            transactionPrice = itemView.findViewById(R.id.transaction_price);
            context = itemView.getContext();
            textPostDecorator = this.context.getString(R.string.rubleSign);
        }

        @SuppressLint("SetTextI18n")
        void bindItem(TransactionListItem item, boolean selected){
            int textColor = item.getType().equals(TransactionListItem.TYPE_INCOME)
                    ? context.getResources().getColor(R.color.appleGreen)
                    : context.getResources().getColor(R.color.darkSkyBlue);

            transactionName.setText(item.getName());
            transactionPrice.setText(String.valueOf(item.getPrice()) + this.textPostDecorator);
            transactionPrice.setTextColor(textColor);
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
