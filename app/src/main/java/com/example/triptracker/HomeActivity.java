package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpensesText;
    private TextView foodTotalText, transportTotalText, accommodationTotalText;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddExpense;
    private List<Expense> expenseList;
    private ExpenseAdapter adapter;
    private Spinner categoryFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        totalExpensesText = findViewById(R.id.total_expenses);
        foodTotalText = findViewById(R.id.food_total);
        transportTotalText = findViewById(R.id.transport_total);
        accommodationTotalText = findViewById(R.id.accommodation_total);
        recyclerView = findViewById(R.id.expense_recycler_view);
        fabAddExpense = findViewById(R.id.fab_add_expense);
        categoryFilter = findViewById(R.id.category_filter);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseList = new ArrayList<>();
        adapter = new ExpenseAdapter(this, expenseList);
        recyclerView.setAdapter(adapter);

        // Load expenses from Firebase
        loadExpensesFromFirebase();

        // Set up Floating Action Button
        fabAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // Set up category filter (for demonstration purposes)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(adapter);
        categoryFilter.setOnItemSelectedListener((parent, view, position, id) -> {
            String selectedCategory = parent.getItemAtPosition(position).toString();
            filterExpensesByCategory(selectedCategory);
        });
    }

    // Function to load expenses from Firebase
    private void loadExpensesFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference expensesRef = database.getReference("expenses");

        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList.clear();
                double total = 0, foodTotal = 0, transportTotal = 0, accommodationTotal = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expense expense = snapshot.getValue(Expense.class);
                    if (expense != null) {
                        expenseList.add(expense);
                        total += Double.parseDouble(expense.getAmount());
                        if (expense.getCategory().equals("Food")) {
                            foodTotal += Double.parseDouble(expense.getAmount());
                        } else if (expense.getCategory().equals("Transport")) {
                            transportTotal += Double.parseDouble(expense.getAmount());
                        } else if (expense.getCategory().equals("Accommodation")) {
                            accommodationTotal += Double.parseDouble(expense.getAmount());
                        }
                    }
                }

                totalExpensesText.setText("Total Expenses: " + total);
                foodTotalText.setText("Food: " + foodTotal);
                transportTotalText.setText("Transport: " + transportTotal);
                accommodationTotalText.setText("Accommodation: " + accommodationTotal);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    // Function to filter expenses based on category
    private void filterExpensesByCategory(String category) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference expensesRef = database.getReference("expenses");

        expensesRef.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expense expense = snapshot.getValue(Expense.class);
                    if (expense != null) {
                        expenseList.add(expense);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
