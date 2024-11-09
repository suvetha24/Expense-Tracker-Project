package com.example.triptracker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("expenses");
    }

    public void addExpense(Expense expense) {
        String expenseId = databaseReference.push().getKey();
        databaseReference.child(expenseId).setValue(expense);
    }

    public void updateExpense(String expenseId, Expense expense) {
        databaseReference.child(expenseId).setValue(expense);
    }

    public void deleteExpense(String expenseId) {
        databaseReference.child(expenseId).removeValue();
    }

    public DatabaseReference getAllExpenses() {
        return databaseReference;
    }
}
