package com.cliquet.gautier.moodtracker;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<String> mSinceWhen;
    private ArrayList<Integer> mBackgroundColor;
    private ArrayList<Integer> mDynamicWeight;
    private ArrayList<String> mComment;

    RecyclerViewAdapter(Context mContext, ArrayList<String> mSinceWhen, ArrayList<Integer> mBackgroundColor, ArrayList<Integer> mDynamicWeight, ArrayList<String> mComment) {
        this.mContext = mContext;
        this.mSinceWhen = mSinceWhen;
        this.mBackgroundColor = mBackgroundColor;
        this.mDynamicWeight = mDynamicWeight;
        this.mComment = mComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_historic_listitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        String test;
        test = mComment.get(i);
        if(test.equals("")) {
            viewHolder.historicCommentImage.setVisibility(View.GONE);
        }
        else {
            viewHolder.historicCommentImage.setVisibility(View.VISIBLE);
        }

        viewHolder.historicText.setText(mSinceWhen.get(i));
        viewHolder.historicText.setBackgroundColor(mBackgroundColor.get(i));

        //param1 and param2 are used to dynamically change the textviews layout in order to reflect correct width
        float textViewWeight = mDynamicWeight.get(i);

        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1+textViewWeight
        );

        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                4-textViewWeight
        );
        viewHolder.mainLayout.setLayoutParams(param1);
        viewHolder.blankText.setLayoutParams(param2);

        viewHolder.historicCommentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mComment.get(i), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBackgroundColor.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView historicText;
        TextView blankText;
        ImageView historicCommentImage;
        RelativeLayout mainLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.layout_historic_listitem_parentlayout_layout);
            historicText = itemView.findViewById(R.id.layout_historic_listitem_sincewhen_text);
            blankText = itemView.findViewById(R.id.layout_historic_listitem_blanktextview_text);
            historicCommentImage = itemView.findViewById(R.id.layout_historic_listitem_attachedcomment_image);
        }
    }
}
