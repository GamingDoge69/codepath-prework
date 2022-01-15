package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    List<String> model;
    OnLongClickListener onLongClickListener;

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public ItemsAdapter(List<String> model, OnLongClickListener onLongClickListener) {
        this.model = model;
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(
                parent.getContext()).inflate(android.R.layout.simple_list_item_1,
                parent,
                false
        );

        return new ViewHolder(todoView);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(model.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            textItem.setText(item);
            textItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
