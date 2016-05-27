package com.alexlowe.choices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.transitionseverywhere.Transition;
import com.transitionseverywhere.extra.Scale;

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

        // Inflate the custom layout
        View choiceView = inflater.inflate(R.layout.choice_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(choiceView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ChoiceAdapter.ViewHolder holder, int position) {
        Choice choice = choices.get(position);

        Button button = holder.choiceButton;
        button.setText(choice.getChoiceText());

    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
       public Button choiceButton;

        public ViewHolder(View itemView) {
            super(itemView);

            choiceButton = (Button)itemView.findViewById(R.id.itemChoice);

        }

    }



}
