package com.alexlowe.choices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dell on 1/24/2016.
 */
public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {
    private ArrayList<Choice> choices;

    public ChoiceAdapter(ArrayList<Choice> list) {
        this.choices = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View choiceView = inflater.inflate(R.layout.choice_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(choiceView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ChoiceAdapter.ViewHolder holder, final int position) {
        Choice choice = choices.get(position);

        holder.itemText.setText(choice.getChoiceText());
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choices.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), choices.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemText;
        public ImageView close;

        public ViewHolder(View itemView) {
            super(itemView);

            itemText = (TextView) itemView.findViewById(R.id.itemText);
            close = (ImageView) itemView.findViewById(R.id.close);
        }

    }


}
