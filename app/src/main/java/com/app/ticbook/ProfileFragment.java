package com.app.ticbook;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ticbook.ui.login.LoginFragment;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;

    RecyclerView recyclerView;
    Button btn1;
    RecyclerView.Adapter recycleViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    ArrayList<ItemModel> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for fragment and save it to a variable
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Hide the ActionBar
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().hide();
            }
        }

        // Configure status bar for the fragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity activity = getActivity();
            if (activity != null) {
                View decor = activity.getWindow().getDecorView();
                activity.getWindow().setStatusBarColor(Color.WHITE);
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        recycleViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recycleViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Populate data for RecyclerView
        data = new ArrayList<>();
        for (int i = 0; i < MyItem.Headline.length; i++) {
            data.add(new ItemModel(MyItem.Headline[i], MyItem.Subheadline[i], MyItem.iconlist[i]));
        }

        recycleViewAdapter = new MyRecycleView(data);
        recyclerView.setAdapter(recycleViewAdapter);

        // Initialize and set up TextView
        final TextView txtView = view.findViewById(R.id.tvView);
        txtView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Sai husi perfil", Toast.LENGTH_SHORT).show();

            // Hapus semua fragment dari back stack
            getParentFragmentManager().popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Ganti ProfileFragment dengan LoginFragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment()) // Ganti fragment container dengan LoginFragment
                    .commit();
        });


        // Configure tvUsername
//        tvUsername = view.findViewById(R.id.tvUsername);
//        if (getArguments() != null) {
//            String username = getArguments().getString("username");
//            tvUsername.setText("Welcome, " + username);
//        }

        return view;
    }
}
