package com.shaw.ac2_dispositivos_moveis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText;
    private EditText expenseAmountEditText;
    private EditText expenseDateEditText;
    private Button updateExpenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        expenseNameEditText = findViewById(R.id.editTextExpenseName);
        expenseAmountEditText = findViewById(R.id.editTextExpenseAmount);
        expenseDateEditText = findViewById(R.id.editTextExpenseDate);
        updateExpenseButton = findViewById(R.id.buttonUpdateExpense);

        String expenseData = getIntent().getStringExtra("expense");
        String[] expenseDetails = expenseData.split(", ");
        String expenseName = expenseDetails[0].substring(6);
        String expenseAmount = expenseDetails[1].substring(8);
        String expenseDate = expenseDetails[2].substring(6);

        expenseNameEditText.setText(expenseName);
        expenseAmountEditText.setText(expenseAmount);
        expenseDateEditText.setText(expenseDate);

        updateExpenseButton.setText("Salvar Nova Despesa");

        updateExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = expenseNameEditText.getText().toString().trim();
                String newAmount = expenseAmountEditText.getText().toString().trim();
                String newDate = expenseDateEditText.getText().toString().trim();

                String updatedExpense = "Nome: " + newName + ", Valor: R$ " + newAmount + ", Data: " + newDate;
                Toast.makeText(EditExpenseActivity.this, "Despesa atualizada", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedExpense", updatedExpense);
                resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
