// ExpenseListAdapter.java
package com.example.triptracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {
    private List<Expense> expenseList;
    private List<Expense> filteredList;

    public ExpenseListAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
        this.filteredList = new ArrayList<>(expenseList);  // initially display all items
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = filteredList.get(position);
        holder.expenseName.setText(expense.getName());
        holder.amount.setText(String.valueOf(expense.getCost()));
        holder.category.setText(expense.getCategory());
        holder.date.setText(expense.getDay() + "/" + expense.getMonth() + "/" + expense.getYear());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String category) {
        if (category.equals("All")) {
            filteredList = new ArrayList<>(expenseList);
        } else {
            filteredList = new ArrayList<>();
            for (Expense expense : expenseList) {
                if (expense.getCategory().equals(category)) {
                    filteredList.add(expense);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expenseName, amount, category, date;

        ExpenseViewHolder(View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.expenseName);
            amount = itemView.findViewById(R.id.amount);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
        }
    }
}
