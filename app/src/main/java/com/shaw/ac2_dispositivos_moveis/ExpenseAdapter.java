package com.shaw.ac2_dispositivos_moveis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<String> expenseList;
    private Context context;

    public ExpenseAdapter(Context context, List<String> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        String expenseItem = expenseList.get(position);
        holder.expenseNameTextView.setText(expenseItem);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(context, EditExpenseActivity.class);
                editIntent.putExtra("expense", expenseItem);
                editIntent.putExtra("position", position);
                ((Activity) context).startActivityForResult(editIntent, 1);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, expenseItem);
                context.startActivity(Intent.createChooser(shareIntent, "Compartilhar despesa via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expenseNameTextView;
        Button editButton;
        Button shareButton;

        ExpenseViewHolder(View itemView) {
            super(itemView);
            expenseNameTextView = itemView.findViewById(R.id.textViewExpense);
            editButton = itemView.findViewById(R.id.buttonEdit);
            shareButton = itemView.findViewById(R.id.buttonSendText);
        }
    }
}
