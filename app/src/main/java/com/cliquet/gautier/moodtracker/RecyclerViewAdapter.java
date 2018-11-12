package com.cliquet.gautier.moodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<String> mSinceWhen;
    private ArrayList<Integer> mBackgroundColor;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mSinceWhen, ArrayList<Integer> mBackgroundColor) {
        this.mContext = mContext;
        this.mSinceWhen = mSinceWhen;
        this.mBackgroundColor = mBackgroundColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_historic_listitem, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.historicText.setText(mSinceWhen.get(i));
        viewHolder.historicText.setBackgroundColor(mBackgroundColor.get(i));
    }

    @Override
    public int getItemCount() {
        return mBackgroundColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout historicParentLayout;
        TextView historicText;
        ImageView historicCommentImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            historicText = itemView.findViewById(R.id.layout_historic_listitem_sincewhen_text);
            historicText = itemView.findViewById(R.id.layout_historic_listitem_sincewhen_text);
            historicCommentImage = itemView.findViewById(R.id.layout_historic_listitem_attachedcomment_image);
        }
    }
}
