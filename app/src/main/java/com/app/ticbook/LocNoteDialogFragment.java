package com.app.ticbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LocNoteDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(String loc, String obs);
    }

    private OnDescriptionEnteredListener listener;

    public static LocNoteDialogFragment newInstance(
            OnDescriptionEnteredListener listener
    ) {
        LocNoteDialogFragment fragment = new LocNoteDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loc_obs_exe, container, false);

        EditText loc = view.findViewById(R.id.loc_edt);
        EditText obs = view.findViewById(R.id.obs_edt);

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        buttonContinue.setOnClickListener(v -> {
            String locs = loc.getText().toString();
            String obss = obs.getText().toString();

            if (listener != null) {
                listener.onDescriptionEntered(locs, obss);
            }
            dismiss();
        });

        imgClose.setOnClickListener(v-> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}



