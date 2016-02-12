package com.alexlowe.choices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;

import java.util.ArrayList;

/**
 * Created by dell on 1/24/2016.
 */
public class ChoiceAdapter extends ArrayAdapter<Choice> {
    private ArrayList<Choice> choices;

    public ChoiceAdapter(Context context, ArrayList<Choice> list) {
        super(context, 0, list);
        this.choices = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Choice choice = choices.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.choice_item, parent, false);

            viewHolder.choiceButton = (CircularProgressButton) convertView.findViewById(R.id.itemChoice);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.choiceButton.setText(choice.getChoiceText());
        viewHolder.choiceButton.setIdleText(choice.getChoiceText());

        return convertView;
    }

    static class ViewHolder {
       CircularProgressButton choiceButton;
    }


}
