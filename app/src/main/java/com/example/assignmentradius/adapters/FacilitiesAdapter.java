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
import com.example.assignmentradius.viewmodel.ApiViewModel;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {

    private final ApiViewModel viewModel;
    private List<ServerResponse.Facilities> facilities;
    private List<List<ServerResponse.Exclusions>> exclusionsList;
    private Context context;

    public FacilitiesAdapter(Context context, List<ServerResponse.Facilities> facilities, ApiViewModel viewModel) {
        this.context = context;
        this.facilities = facilities;
        this.viewModel = viewModel;
    }

    public void setExclusionsList(List<List<ServerResponse.Exclusions>> exclusionsList) {
        this.exclusionsList = exclusionsList;
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
        viewHolder.choiceWidget.setChoiceLabels(context, facilities.get(position).getOptions(), facilities.get(position).getSelectedOption());
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
            facilities.get(getLayoutPosition()).setSelectedOption(optionId);
            ServerResponse.Exclusions exclusion = new ServerResponse.Exclusions(facilities.get(getLayoutPosition()).getFacilityId(), optionId);
            viewModel.addOptionSelection(facilities.get(getLayoutPosition()).getFacilityId(), optionId);
            checkForExclusions(exclusion);
        }
    }

    private void checkForExclusions(ServerResponse.Exclusions exclusion) {
        for(List<ServerResponse.Exclusions> exclusions : exclusionsList) {
            ServerResponse.Exclusions tempExclusionObj = checkIfFacilityPresentInExclusion(exclusion.getFacilityId(), exclusions);
            if(tempExclusionObj != null) {
                if(tempExclusionObj.getOptionsId() != exclusion.getOptionsId()) {
                    enableAllOptions(tempExclusionObj.getFacilityId(), exclusions);
                } else {
                    disableAllExcludedOptions(tempExclusionObj.getFacilityId(), exclusions);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Disable the options of the other facilities which are included in the exclusion list
     * @param facilityId The id of the current selected facility
     * @param exclusions The exclusion list in which the current selected facility is present
     */
    private void disableAllExcludedOptions(int facilityId, List<ServerResponse.Exclusions> exclusions) {
        for(ServerResponse.Exclusions exclusionObj : exclusions) {
            if(facilityId != exclusionObj.getFacilityId()) {
                ServerResponse.Facilities facility = getFacilityById(exclusionObj.getFacilityId());
                if(facility != null) {
                    facility.disableOption(exclusionObj.getOptionsId());
                    facility.setFacilityIdOfOptionBlockingFacility(facilityId);
                }
            }
        }
    }

    /**
     * Enable All options for the facilities other than the current selected facility
     * @param facilityId The facility id of the current selected facility
     * @param exclusions The exclusion list in which the current selected facility is present
     */
    private void enableAllOptions(int facilityId, List<ServerResponse.Exclusions> exclusions) {
        for(ServerResponse.Exclusions exclusionObj : exclusions) {
            if(facilityId != exclusionObj.getFacilityId()) {
                ServerResponse.Facilities facility = getFacilityById(exclusionObj.getFacilityId());
                if(facility != null && !checkForBlockByOtherFacilities(facilityId, facility)) {
                    facility.enableAllOptions();
                }
            }
        }
    }

    /**
     *
     * @param facilityId
     * @param facility
     * @return
     */
    private boolean checkForBlockByOtherFacilities(int facilityId, ServerResponse.Facilities facility) {
        return facilityId != facility.getFacilityIdOfOptionBlockingFacility();
    }

    /**
     * Check if the facility is in exclusion list
     * @param facilityId
     * @param tempExclusionsList
     * @return
     */
    private ServerResponse.Exclusions checkIfFacilityPresentInExclusion(int facilityId, List<ServerResponse.Exclusions> tempExclusionsList) {
        for(ServerResponse.Exclusions exclusions : tempExclusionsList) {
            if(facilityId == exclusions.getFacilityId()) {
                return exclusions;
            }
        }
        return null;
    }

    /**
     * Get the facility from the facilities list by id
     * @param facilityId
     * @return
     */
    private ServerResponse.Facilities getFacilityById(int facilityId) {
        for(ServerResponse.Facilities facilityObj : facilities) {
            if(facilityObj.getFacilityId() == facilityId) {
                return facilityObj;
            }
        }
        return null;
    }

    private int getDisabledOptionId(ServerResponse.Facilities facility) {
        int optionId = -1;
        for(ServerResponse.Facilities.Options option : facility.getOptions()) {
            if(option.isOptionDisabled()) {
                optionId = option.getId();
                break;
            }
        }
        return optionId;
    }
}
