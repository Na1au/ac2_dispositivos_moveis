package com.shaw.ac2_dispositivos_moveis;
// Ana Laura de Oliveira Silva, RA: 223572
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText expenseNameEditText;
    private EditText expenseAmountEditText;
    private EditText expenseDateEditText;
    private Button addExpenseButton;
    private RecyclerView expensesRecyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<String> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseNameEditText = findViewById(R.id.editTextExpenseName);
        expenseAmountEditText = findViewById(R.id.editTextExpenseAmount);
        expenseDateEditText = findViewById(R.id.editTextExpenseDate);
        addExpenseButton = findViewById(R.id.buttonAddExpense);
        expensesRecyclerView = findViewById(R.id.recyclerViewExpenses);

        expenses = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenses);

        expensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expensesRecyclerView.setAdapter(expenseAdapter);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expenseName = expenseNameEditText.getText().toString().trim();
                String expenseAmount = expenseAmountEditText.getText().toString().trim();
                String expenseDate = expenseDateEditText.getText().toString().trim();

                if (!expenseName.isEmpty() && !expenseAmount.isEmpty() && !expenseDate.isEmpty()) {
                    showConfirmationDialog(expenseName, expenseAmount, expenseDate);
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showConfirmationDialog(String name, String amount, String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Adicionar despesa:\nNome: " + name + "\nValor: R$ " + amount + "\nData: " + date)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addExpenseToList(name, amount, date);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void addExpenseToList(String name, String amount, String date) {
        String expense = "Nome: " + name + ", Valor: R$ " + amount + ", Data: " + date;
        expenses.add(expense);
        expenseAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Despesa adicionada", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        expenseNameEditText.setText("");
        expenseAmountEditText.setText("");
        expenseDateEditText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedExpense = data.getStringExtra("updatedExpense");
            int position = data.getIntExtra("position", -1);
            if (position != -1 && updatedExpense != null) {
                expenses.set(position, updatedExpense);
                expenseAdapter.notifyItemChanged(position);
            }
        }
    }
}
