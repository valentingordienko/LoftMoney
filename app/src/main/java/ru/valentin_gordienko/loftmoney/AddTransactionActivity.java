package ru.valentin_gordienko.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    static final String KEY_NAME = "transaction_name";
    static final String KEY_PRICE = "transaction_price";

    private EditText transactionNameInput;
    private EditText transactionPriceInput;
    private Button addTransactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

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
                Intent intent = new Intent();

                String transactionName = transactionNameInput.getText().toString();
                String transactionPrice = transactionPriceInput.getText().toString();

                intent.putExtra(KEY_NAME, transactionName);
                intent.putExtra(KEY_PRICE, transactionPrice);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
