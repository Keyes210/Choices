package com.alexlowe.choices;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keyes on 5/29/2016.
 */
public class ChoiceListAdapter extends RecyclerView.Adapter<ChoiceListAdapter.ViewHolder> {
    private static final String KEY_CL = "keychoicelist";
    private ArrayList<ChoiceList> mMasterList;
    private Context mContext;


    public ChoiceListAdapter(Context context, ArrayList<ChoiceList> choiceLists) {
        this.mMasterList = choiceLists;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View choiceListView = inflater.inflate(R.layout.save_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(choiceListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ChoiceList choiceList = mMasterList.get(position);

        TextView tvName = holder.itemName;
        tvName.setText(choiceList.getName());

        TextView tvDate = holder.itemDate;
        tvDate.setText(choiceList.getDate());

        ImageView ivClose = holder.close;
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMasterList.remove(holder.getAdapterPosition());
                MasterList.gMasterList.getMasterList().remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), mMasterList.size());
            }
        });

        ImageView ivLoad = holder.load;
        ivLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialog(holder);
            }
        });

    }

    private void launchDialog(final ViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Load List");
        builder.setMessage("Are you sure you want to load this list of choices?");
        builder.setPositiveButton("Load", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadChoice(holder);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void loadChoice(ViewHolder holder) {
        Intent intent = new Intent(mContext, MainActivity.class);
        ChoiceList cl = mMasterList.get(holder.getAdapterPosition());
        intent.putExtra(KEY_CL, cl);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mMasterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public ImageView close;
        public ImageView load;
        public TextView itemDate;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDate = (TextView) itemView.findViewById(R.id.itemDate);
            close = (ImageView) itemView.findViewById(R.id.close);
            load = (ImageView) itemView.findViewById(R.id.load);
        }

    }


}
