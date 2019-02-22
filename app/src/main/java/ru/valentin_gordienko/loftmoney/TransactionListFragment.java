package ru.valentin_gordienko.loftmoney;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private ActionMode actionMode;

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

        adapter = new TransactionListItemAdapter();
        adapter.setListener(new AdapterListener());

        if (getArguments() == null) {
            throw new IllegalStateException("Fragment arguments are NULL");
        }

        fragmentType = getArguments().getString(KEY_NAME);

        App app = (App) Objects.requireNonNull(getActivity()).getApplication();
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

        preLoader = view.findViewById(R.id.preLoader);
        int preLoaderColor = requireContext().getResources().getColor(R.color.colorAccent);
        preLoader.setColorSchemeColors(preLoaderColor);
        preLoader.setOnRefreshListener(this::getTransactions);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Context context = requireContext();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        DividerItemDecoration transactionItemDivider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        transactionItemDivider.setDrawable(context.getDrawable(R.drawable.transactions_list_divider));
        recyclerView.addItemDecoration(transactionItemDivider);

        this.getTransactions();
    }

    private void getTransactions() {

        String token = AuthActivity.getToken(requireContext());
        if (token == null) return;

        Call<List<TransactionListItem>> call = api.getTransactions(fragmentType, token);

        call.enqueue(new Callback<List<TransactionListItem>>() {
            @Override
            public void onResponse(Call<List<TransactionListItem>> call, Response<List<TransactionListItem>> response) {
                preLoader.setRefreshing(false);
                List<TransactionListItem> transactions = response.body();
                adapter.setTransactionItems(transactions);
            }

            @Override
            public void onFailure(Call<List<TransactionListItem>> call, Throwable error) {
                preLoader.setRefreshing(false);
                Log.e(TAG, "getTransactions: ", error);
            }
        });
    }

    private void removeTransaction(Long id){

        String token = AuthActivity.getToken(requireContext());
        if (token == null) return;

        Call<Object> call = api.removeTransaction(id, token);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public void onClickFloatActionButton() {
        Intent intent = new Intent(requireContext(), AddTransactionActivity.class);
        intent.putExtra(AddTransactionActivity.KEY_TYPE, fragmentType);
        startActivityForResult(intent, ADD_TRANSACTION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_TRANSACTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getTransactions();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private String getActionModeTitle(){
        return getString(R.string.actionModeTitle) + ' ' + adapter.getSelectedTransactionsCount();
    }

    private class AdapterListener implements TransactionListItemAdapterListener {

        @Override
        public void onClickItem(TransactionListItem item, int position) {
            if(actionMode == null){
                return;
            }

            toggleSelectedItem(position);
            actionMode.setTitle(getActionModeTitle());
        }

        @Override
        public void onLongClickItem(TransactionListItem item, int position) {
            if (actionMode != null) {
                return;
            }

            getActivity().startActionMode(new ActionModeCallback());
            toggleSelectedItem(position);
            actionMode.setTitle(getActionModeTitle());
        }

        private void toggleSelectedItem(int position){
            adapter.toggleSelectedTransaction(position);
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(requireContext());
            inflater.inflate(R.menu.action_mode, menu);

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.action_bar_item__delete){
                showConfirmDialog();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSelectedTransactions();
        }

        void removeSelectedTransactions(){
            List<Integer> selectedPositions = adapter.getSelectedTransactions();

            for (int i = selectedPositions.size() - 1; i >= 0; i--) {
                TransactionListItem transactionListItem = adapter.removeTransaction(selectedPositions.get(i));
                removeTransaction(transactionListItem.getId());
            }

            actionMode.finish();
        }

        void showConfirmDialog(){
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.confirmDeleteTransactionText))
                    .setPositiveButton(getString(R.string.confirmYesButtonText), (dialog1, which) -> removeSelectedTransactions())
                    .setNegativeButton(getString(R.string.confirmNoButtonText), (dialog12, which) -> { })
                    .create();
            dialog.show();
        }
    }
}
