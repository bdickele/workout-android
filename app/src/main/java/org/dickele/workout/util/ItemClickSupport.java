package org.dickele.workout.util;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {

    private final RecyclerView recyclerView;

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private final int itemID;

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (onItemClickListener != null) {
                final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private final View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
            if (onItemLongClickListener != null) {
                final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                return onItemLongClickListener.onItemLongClicked(recyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private final RecyclerView.OnChildAttachStateChangeListener attachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(final View view) {
            if (onItemClickListener != null) {
                view.setOnClickListener(clickListener);
            }
            if (onItemLongClickListener != null) {
                view.setOnLongClickListener(longClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(final View view) {

        }
    };

    private ItemClickSupport(final RecyclerView recyclerView, final int itemID) {
        this.recyclerView = recyclerView;
        this.itemID = itemID;
        this.recyclerView.setTag(itemID, this);
        this.recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    public static ItemClickSupport addTo(final RecyclerView view, final int itemID) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support == null) {
            support = new ItemClickSupport(view, itemID);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(final RecyclerView view, final int itemID) {
        final ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(final OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(final OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        return this;
    }

    private void detach(final RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(attachListener);
        view.setTag(itemID, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
