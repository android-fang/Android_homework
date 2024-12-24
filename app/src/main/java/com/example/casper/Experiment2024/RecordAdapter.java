package com.example.casper.Experiment2024;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private ArrayList<Record> RecordList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateView;
        private final TextView commentView;
        private final ImageView imageView;

        public ViewHolder(View shopItemView) {
            super(shopItemView);

            dateView = shopItemView.findViewById(R.id.date);
            commentView = shopItemView.findViewById(R.id.comment);
            imageView = shopItemView.findViewById(R.id.image);

            shopItemView.setOnCreateContextMenuListener(this::onCreateContextMenu);
        }

        public TextView getDateView() {
            return dateView;
        }

        public TextView getCommentView() {
            return commentView;
        }

        public ImageView getImageView() {
            return imageView;
        }


        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("具体操作");

            menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
            menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
            menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
        }
    }


    public RecordAdapter(ArrayList<Record> Records) {
        RecordList =Records;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getDateView().setText(RecordList.get(position).getDate());
        viewHolder.getCommentView().setText(RecordList.get(position).getComments() + "");
        viewHolder.getImageView().setImageBitmap(RecordList.get(position).getImage());
    }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return RecordList.size();
    }
}