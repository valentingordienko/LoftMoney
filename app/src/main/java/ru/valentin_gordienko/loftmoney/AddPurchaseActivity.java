package ru.valentin_gordienko.loftmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class AddPurchaseActivity extends AppCompatActivity {

    private EditText PurchaseNameInput;
    private EditText PurchasePriceInput;
    private Button AddPurchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);

        this.findChildViews();
        this.initEventListeners();
    }

    private void findChildViews() {
        PurchaseNameInput = findViewById(R.id.purchase_name_input);
        PurchasePriceInput = findViewById(R.id.purchase_price_input);
        AddPurchaseButton = findViewById(R.id.add_purchase_button);
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
                boolean isEmptyName = TextUtils.isEmpty(PurchaseNameInput.getText());
                boolean isEmptyPrice = TextUtils.isEmpty(PurchasePriceInput.getText());
                AddPurchaseButton.setEnabled(!isEmptyName && !isEmptyPrice);
            }
        };

        PurchaseNameInput.addTextChangedListener(changeTextListener);
        PurchasePriceInput.addTextChangedListener(changeTextListener);
    }
}
