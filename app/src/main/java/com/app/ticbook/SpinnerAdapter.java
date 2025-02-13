package com.app.ticbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ticbook.databinding.ItemSpinnerBinding;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ViewHolder> {

    public interface Selected {
        void onSelected(List<Integer> data, List<String> s);
    }

    List<SpinnerData> spinnerData;
    List<Integer> selectedData;
    List<String> s;
    Selected selected;
    Context context;

    public SpinnerAdapter(List<SpinnerData> spinnerData, Selected selected) {
        this.spinnerData = spinnerData;
        this.selected = selected;
        selectedData = new ArrayList<>();
        s = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSpinnerBinding binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpinnerData data = spinnerData.get(position);

        if (data.selected) {
            holder.binding.constraintLayout.setBackgroundResource(R.drawable.bg_spinner_selected);
            holder.binding.icons.setImageResource(R.drawable.check);
            holder.binding.valueTxt.setTextColor(context.getResources().getColor(R.color.black, null));
        } else {
            holder.binding.constraintLayout.setBackgroundResource(R.drawable.bg_spinner_unselected);
            holder.binding.icons.setImageResource(R.drawable.plus);
            holder.binding.valueTxt.setTextColor(context.getResources().getColor(R.color.white, null));
        }

        holder.binding.valueTxt.setText(data.name);

        holder.itemView.setOnClickListener(v -> {
            data.selected = !data.selected;
            if (data.selected) {
                selectedData.add(data.id);
                s.add(data.name);
            } else {
                selectedData.remove((Object)data.id);
                s.remove(data.name);
            }
            selected.onSelected(selectedData,s);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return spinnerData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemSpinnerBinding binding;
        public ViewHolder(ItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
