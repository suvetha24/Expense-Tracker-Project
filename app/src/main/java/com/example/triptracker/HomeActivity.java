// HomeActivity.java
package com.example.triptracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.view.View;



public class HomeActivity extends AppCompatActivity {
    private Spinner categoryFilter;
    private RecyclerView expenseRecyclerView;
    private ExpenseListAdapter expenseAdapter;
    private List<Expense> expenseList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        categoryFilter = findViewById(R.id.categoryFilter);
        expenseRecyclerView = findViewById(R.id.expenseRecyclerView);
        db = FirebaseFirestore.getInstance();

        setupCategoryFilter();
        setupRecyclerView();
        loadExpenses();
    }

    private void setupCategoryFilter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(adapter);

        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                expenseAdapter.filter(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupRecyclerView() {
        expenseAdapter = new ExpenseListAdapter(expenseList);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setAdapter(expenseAdapter);
    }

    private void loadExpenses() {
        db.collection("expenses").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Expense expense = document.toObject(Expense.class);
                expense.setId(document.getId());
                expenseList.add(expense);
            }
            expenseAdapter.notifyDataSetChanged();
        });
    }
}
