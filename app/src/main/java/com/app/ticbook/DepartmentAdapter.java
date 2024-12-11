//package com.app.ticbook;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
//
//    private final List<String> departmentList;
//    private final DepartmentDialogFragment.OnDepartmentSelectedListener listener;
//
//    public DepartmentAdapter(List<String> departmentList, DepartmentDialogFragment.OnDepartmentSelectedListener listener) {
//        this.departmentList = departmentList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//        return new DepartmentViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
//        String department = departmentList.get(position);
//        holder.textView.setText(department);
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onDepartmentSelected(department);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return departmentList.size();
//    }
//
//    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
//        TextView textView;
//
//        public DepartmentViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(android.R.id.text1);
//        }
//    }
//}
