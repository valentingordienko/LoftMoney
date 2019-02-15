package ru.valentin_gordienko.loftmoney;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity {

    static final String KEY_TYPE = "transaction_type";

    private EditText transactionNameInput;
    private EditText transactionPriceInput;
    private Button addTransactionButton;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        this.api = ((App) getApplication()).getApi();

        this.findChildViews();
        this.initEventListeners();
    }

    private void findChildViews() {
        this.transactionNameInput = findViewById(R.id.purchase_name_input);
        this.transactionPriceInput = findViewById(R.id.purchase_price_input);
        this.addTransactionButton = findViewById(R.id.add_transaction_button);
    }

    private void initEventListeners(){
        TextWatcher changeTextListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmptyName = TextUtils.isEmpty(transactionNameInput.getText());
                boolean isEmptyPrice = TextUtils.isEmpty(transactionPriceInput.getText());
                addTransactionButton.setEnabled(!isEmptyName && !isEmptyPrice);
            }
        };

        transactionNameInput.addTextChangedListener(changeTextListener);
        transactionPriceInput.addTextChangedListener(changeTextListener);

        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactionType = getIntent().getStringExtra(KEY_TYPE);
                String transactionName = transactionNameInput.getText().toString();
                String transactionPrice = transactionPriceInput.getText().toString();

                addTransaction(transactionType, transactionName, transactionPrice);
            }
        });
    }

    private void addTransaction(String type, String name, String price){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString(AuthActivity.AUTH_PROPERTY, null);

        AddTransactionRequest request = new AddTransactionRequest(type, name, Double.valueOf(price));

        Call<Object> call = api.addTransaction(request, token);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
