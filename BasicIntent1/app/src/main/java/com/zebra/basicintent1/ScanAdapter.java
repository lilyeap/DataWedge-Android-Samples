package com.zebra.basicintent1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ScanViewHolder> {

    private List<ScanItem> scanItems;

    public ScanAdapter(List<ScanItem> scanItems) {
        this.scanItems = scanItems;
    }

    @NonNull
    @Override
    public ScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_item, parent, false);
        return new ScanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanViewHolder holder, int position) {
        ScanItem item = scanItems.get(position);
        holder.scanSourceTextView.setText(item.getSource());
        holder.scanDataTextView.setText(item.getData());
        holder.scanLabelTypeTextView.setText(item.getLabelType());
    }

    @Override
    public int getItemCount() {
        return scanItems.size();
    }

    static class ScanViewHolder extends RecyclerView.ViewHolder {
        TextView scanSourceTextView;
        TextView scanDataTextView;
        TextView scanLabelTypeTextView;

        ScanViewHolder(View itemView) {
            super(itemView);
            scanSourceTextView = itemView.findViewById(R.id.scanSourceTextView);
            scanDataTextView = itemView.findViewById(R.id.scanDataTextView);
            scanLabelTypeTextView = itemView.findViewById(R.id.scanLabelTypeTextView);
        }
    }
}
