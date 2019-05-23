package com.example.assignmentradius.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.assignmentradius.R;
import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.utils.AppUtils;

import java.util.List;

public class CustomChoiceWidget extends LinearLayout {

    private RadioGroup optionsGroup;
    private TextView tvChoiceWidget;
    private int selectedID;
    private ChoiceSelectionListener choiceSelectionListener;

    public CustomChoiceWidget(Context context) {
        super(context);
        initLayout(context);
    }

    public CustomChoiceWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public CustomChoiceWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View choiceLayout = LayoutInflater.from(context).inflate(R.layout.item_single_choice, new RelativeLayout(context), false);
        optionsGroup = choiceLayout.findViewById(R.id.rg_choices);
        tvChoiceWidget = choiceLayout.findViewById(R.id.tv_choices_title);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        choiceLayout.setLayoutParams(layoutParams);
        addView(choiceLayout);

        initChoiceSelectionListener();
    }

    public void setChoiceSelectionListener(ChoiceSelectionListener choiceSelectionListener) {
        this.choiceSelectionListener = choiceSelectionListener;
    }

    private void initChoiceSelectionListener() {
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(choiceSelectionListener != null) {
                RadioButton radioButton = optionsGroup.findViewById(group.getCheckedRadioButtonId());
                selectedID = radioButton.getId();
                choiceSelectionListener.onChoiceSelected(selectedID);
            }
        });
    }

    public void setChoiceLabels(Context context, List<ServerResponse.Facilities.Options> userOptions) {
        int selectedIndex = -1;
        for(int i = 0; i < userOptions.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getIconResourceId(userOptions.get(i).getIcon()),0,0,0);
            radioButton.setText(userOptions.get(i).getName());
            radioButton.setId(userOptions.get(i).getId());
            optionsGroup.addView(radioButton);
        }
        if(selectedIndex != -1) {
            RadioButton radioButton = (RadioButton) optionsGroup.getChildAt(selectedIndex);
            radioButton.setChecked(true);
        }
    }

    public void setChoiceWidgetTitle(String title) {
        tvChoiceWidget.setText(title);
    }

    public interface ChoiceSelectionListener {
        void onChoiceSelected(int optionId);
    }


}