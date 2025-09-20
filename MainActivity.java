// Other existing code

@Override
public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    // Change return false to return true
    return true;
}

@Override
public boolean isLongPressDragEnabled() {
    return true; // Enable drag on long press
}

// Other existing code