package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenseList;
    private FirebaseHelper firebaseHelper;
    private TextView totalExpensesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        totalExpensesText = findViewById(R.id.total_expenses_text);
        firebaseHelper = new FirebaseHelper();
        expenseList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseAdapter = new ExpenseAdapter(expenseList, this, new ExpenseAdapter.OnExpenseClickListener() {
            @Override
            public void onExpenseClick(Expense expense) {
                // Open Edit Activity with the selected expense data
                Intent intent = new Intent(HomeActivity.this, AddEditExpenseActivity.class);
                intent.putExtra("expense", expense);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Expense expense) {
                // Delete the expense from Firebase
                firebaseHelper.deleteExpense(expense.getId());
                Toast.makeText(HomeActivity.this, "Expense deleted", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(expenseAdapter);

        loadExpenses();
    }

    private void loadExpenses() {
        firebaseHelper.getAllExpenses().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList.clear();
                double totalExpenses = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expense expense = snapshot.getValue(Expense.class);
                    expense.setId(snapshot.getKey());
                    expenseList.add(expense);
                    totalExpenses += expense.getAmount();
                }

                expenseAdapter.notifyDataSetChanged();
                totalExpensesText.setText("Total Expenses: " + totalExpenses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to load expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
