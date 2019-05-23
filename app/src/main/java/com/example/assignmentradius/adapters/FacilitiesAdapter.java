package com.example.assignmentradius.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmentradius.R;
import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.ui.widgets.CustomChoiceWidget;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {

    private List<ServerResponse.Facilities> facilities;
    private Context context;

    public FacilitiesAdapter(Context context, List<ServerResponse.Facilities> facilities) {
        this.context = context;
        this.facilities = facilities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_facility, null, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.choiceWidget.setChoiceWidgetTitle(facilities.get(position).getName());
        viewHolder.choiceWidget.setChoiceLabels(context, facilities.get(position).getOptions());
    }

    @Override
    public int getItemCount() {
        return facilities != null ? facilities.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements CustomChoiceWidget.ChoiceSelectionListener {
        private CustomChoiceWidget choiceWidget;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            choiceWidget = itemView.findViewById(R.id.options);
            choiceWidget.setChoiceSelectionListener(this);
        }

        @Override
        public void onChoiceSelected(int optionId) {

        }
    }
}
