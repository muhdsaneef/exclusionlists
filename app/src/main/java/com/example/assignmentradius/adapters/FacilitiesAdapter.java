package com.example.assignmentradius.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmentradius.R;
import com.example.assignmentradius.models.ExclusionEntityModel;
import com.example.assignmentradius.models.FacilityModel;
import com.example.assignmentradius.ui.widgets.CustomChoiceWidget;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {

    private List<FacilityModel> facilities;
    private List<List<ExclusionEntityModel>> exclusionsList;
    private Context context;

    public FacilitiesAdapter(Context context, List<FacilityModel> facilities) {
        this.context = context;
        this.facilities = facilities;

    }

    public void setExclusionsList(List<List<ExclusionEntityModel>> exclusionsList) {
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
            ExclusionEntityModel exclusion = new ExclusionEntityModel(facilities.get(getLayoutPosition()).getFacilityId(), optionId);
            checkForExclusions(exclusion);
        }
    }

    private void checkForExclusions(ExclusionEntityModel exclusion) {
        if(exclusionsList != null && !exclusionsList.isEmpty()) {
            for(List<ExclusionEntityModel> exclusions : exclusionsList) {
                ExclusionEntityModel tempExclusionObj = checkIfFacilityPresentInExclusion(exclusion.getFacilityId(), exclusions);
                if(tempExclusionObj != null) {
                    if(tempExclusionObj.getOptionsId() != exclusion.getOptionsId()) {
                        changeStatusOfFacilityOptions(tempExclusionObj.getFacilityId(), exclusions, true);
                    } else {
                        changeStatusOfFacilityOptions(tempExclusionObj.getFacilityId(), exclusions, false);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * Disable the options of the other facilities which are included in the exclusion list
     * @param facilityId The id of the current selected facility
     * @param exclusions The exclusion list in which the current selected facility is present
     */
    private void changeStatusOfFacilityOptions(int facilityId, List<ExclusionEntityModel> exclusions, boolean enableOption) {
        for(ExclusionEntityModel exclusionObj : exclusions) {
            if(facilityId != exclusionObj.getFacilityId()) {
                FacilityModel facility = getFacilityById(exclusionObj.getFacilityId());
                if(facility != null) {
                    if(enableOption) {
                        if(!checkForBlockByOtherFacilities(facilityId, facility)) {
                            facility.enableAllOptions();
                        }
                    } else {
                        facility.disableOption(exclusionObj.getOptionsId());
                        facility.addBlockingFacilityId(facilityId);
                    }
                }
            }
        }
    }

    /**
     * Check if the options of a facility is blocked by facilities other than the current facility
     * @param facilityId The id of the current facility
     * @param facility The facility of which the options should be enabled
     * @return
     */
    private boolean checkForBlockByOtherFacilities(int facilityId, FacilityModel facility) {
        List<Integer> blockingFacilityIds = facility.getFacilityIdsOfOptionBlockingFacility();
        if(blockingFacilityIds != null) {
            blockingFacilityIds.remove(Integer.valueOf(facilityId));
            return blockingFacilityIds.size() > 0;
        } else {
            return false;
        }
    }

    /**
     * Check if the facility is in exclusion list
     * @param facilityId The id of the current facility
     * @param tempExclusionsList The exclusion list which might contain the facility
     * @return
     */
    private ExclusionEntityModel checkIfFacilityPresentInExclusion(int facilityId, List<ExclusionEntityModel> tempExclusionsList) {
        for(ExclusionEntityModel exclusions : tempExclusionsList) {
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
    private FacilityModel getFacilityById(int facilityId) {
        for(FacilityModel facilityObj : facilities) {
            if(facilityObj.getFacilityId() == facilityId) {
                return facilityObj;
            }
        }
        return null;
    }
}
