package com.example.triptracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.triptracker.model.Expense;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.expenseName.setText(expense.getName());
        holder.expenseAmount.setText(String.valueOf(expense.getAmount()));
        holder.expenseCategory.setText(expense.getCategory());
        holder.expenseDate.setText(expense.getDate());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseName, expenseAmount, expenseCategory, expenseDate;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.expense_name);
            expenseAmount = itemView.findViewById(R.id.expense_amount);
            expenseCategory = itemView.findViewById(R.id.expense_category);
            expenseDate = itemView.findViewById(R.id.expense_date);
        }
    }
}
