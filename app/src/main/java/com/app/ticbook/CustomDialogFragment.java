package com.app.ticbook;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    // Interface untuk callback
    public interface DialogListener {
        void onContinueClicked(String selection);
    }

    private DialogListener listener;

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk dialog
        View view = inflater.inflate(R.layout.dialog_booking, container, false);

        // Inisialisasi elemen UI
        Spinner spinner = view.findViewById(R.id.spinnerSelection);
        Button btnContinue = view.findViewById(R.id.btnDialogContinue);

        // Listener untuk tombol "Continue"
        btnContinue.setOnClickListener(v -> {
            if (listener != null && spinner.getSelectedItem() != null) {
                String selection = spinner.getSelectedItem().toString();
                listener.onContinueClicked(selection);
            }
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Atur ukuran dialog agar memenuhi layar
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
