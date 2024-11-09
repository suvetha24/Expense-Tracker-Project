// AddEditExpenseActivity.java
package com.example.triptracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditExpenseActivity extends AppCompatActivity {
    private EditText expenseName, amount;
    private Spinner categoryDropdown;
    private DatePicker datePicker;
    private Button saveButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense);

        expenseName = findViewById(R.id.expenseName);
        amount = findViewById(R.id.amount);
        categoryDropdown = findViewById(R.id.categoryDropdown);
        datePicker = findViewById(R.id.datePicker);
        saveButton = findViewById(R.id.saveButton);

        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String name = expenseName.getText().toString();
        double cost = Double.parseDouble(amount.getText().toString());
        String category = categoryDropdown.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        // Save or update expense in Firebase Firestore
        Expense expense = new Expense(name, cost, category, day, month, year);
        db.collection("expenses").add(expense);
        finish();
    }
}
