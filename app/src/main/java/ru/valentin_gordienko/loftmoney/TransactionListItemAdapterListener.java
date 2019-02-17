package ru.valentin_gordienko.loftmoney;

public interface TransactionListItemAdapterListener {
    void onClickItem(TransactionListItem item, int position);
    void onLongClickItem(TransactionListItem item, int position);
}
