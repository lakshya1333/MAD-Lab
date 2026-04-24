package com.example.androidmastertemplate;


import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * ValidationHelper.java — Central Validation Utility
 *
 * Usage:
 *   ValidationHelper v = new ValidationHelper(this);
 *   if (!v.isNotEmpty(etName, "Name")) return;
 *   v.checkThreshold(score, 50, "High Score Warning", "Score exceeds 50!", runnable);
 */
public class ValidationHelper {

    private final Context context;

    // TODO: EXAM_MODIFY — Change threshold value as needed
    public static final int DEFAULT_THRESHOLD = 50;

    public ValidationHelper(Context context) {
        this.context = context;
    }

    // ── 1. Empty Field Check ──────────────────────────────────────────────
    /**
     * Returns false and shows a Toast if the EditText is empty.
     * Use: if (!v.isNotEmpty(etUsername, "Username")) return;
     */
    public boolean isNotEmpty(EditText field, String fieldName) {
        if (TextUtils.isEmpty(field.getText().toString().trim())) {
            showToast(fieldName + " cannot be empty.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Validate multiple fields at once. Returns false on first failure.
     * Usage: if (!v.allNotEmpty(new EditText[]{et1, et2}, new String[]{"Name","Email"})) return;
     */
    public boolean allNotEmpty(EditText[] fields, String[] names) {
        for (int i = 0; i < fields.length; i++) {
            if (!isNotEmpty(fields[i], names[i])) return false;
        }
        return true;
    }

    // ── 2. Email Format Check ─────────────────────────────────────────────
    public boolean isValidEmail(EditText field) {
        String email = field.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    // ── 3. Password Length Check ──────────────────────────────────────────
    // TODO: EXAM_MODIFY — Change minLength as required
    public boolean isValidPassword(EditText field, int minLength) {
        String pwd = field.getText().toString();
        if (pwd.length() < minLength) {
            showToast("Password must be at least " + minLength + " characters.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    // ── 4. Numeric Range Check ────────────────────────────────────────────
    public boolean isInRange(EditText field, String fieldName, int min, int max) {
        try {
            int value = Integer.parseInt(field.getText().toString().trim());
            if (value < min || value > max) {
                showToast(fieldName + " must be between " + min + " and " + max + ".");
                field.requestFocus();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showToast(fieldName + " must be a number.");
            field.requestFocus();
            return false;
        }
    }

    // ── 5. Threshold-Based MaterialAlertDialog ────────────────────────────
    /**
     * Shows a MaterialAlertDialog if value exceeds threshold.
     * onConfirm runs when user presses "Continue".
     *
     * TODO: EXAM_MODIFY — Change title, message, button text, threshold logic
     */
    public void checkThreshold(int value, int threshold,
                               String title, String message,
                               Runnable onConfirm) {
        if (value > threshold) { // TODO: EXAM_MODIFY — change operator (>, <, >=, etc.)
            new MaterialAlertDialogBuilder(context)
                    .setTitle(title)           // TODO: EXAM_MODIFY — e.g., "High Score Detected"
                    .setMessage(message)       // TODO: EXAM_MODIFY — e.g., "Score is above 50. Continue?"
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Continue", (dialog, which) -> {
                        if (onConfirm != null) onConfirm.run();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
        } else {
            if (onConfirm != null) onConfirm.run(); // Value OK, proceed immediately
        }
    }

    /**
     * Simple confirmation dialog — no threshold logic.
     * TODO: EXAM_MODIFY — Use for delete confirmations, submit confirmations, etc.
     */
    public void showConfirmDialog(String title, String message,
                                  Runnable onConfirm) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (onConfirm != null) onConfirm.run();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Info/Error dialog — single OK button.
     */
    public void showInfoDialog(String title, String message) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // ── Toast Shortcut ────────────────────────────────────────────────────
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}