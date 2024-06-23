package com.s22010360.musicaa;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.RecordingViewHolder> {
    private List<String> recordingUrls;

    public RecordingsAdapter(List<String> recordingUrls) {
        this.recordingUrls = recordingUrls;
    }

    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recording, parent, false);
        return new RecordingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingViewHolder holder, int position) {
        String recordingUrl = recordingUrls.get(position);
        holder.bind(recordingUrl);
    }

    @Override
    public int getItemCount() {
        return recordingUrls.size();
    }

    public static class RecordingViewHolder extends RecyclerView.ViewHolder {
        private TextView recordingName;

        public RecordingViewHolder(@NonNull View itemView) {
            super(itemView);
            recordingName = itemView.findViewById(R.id.recording_name);
        }

        public void bind(String recordingUrl) {
            recordingName.setText(recordingUrl); // Set this to a proper display name if available
        }
    }
}
