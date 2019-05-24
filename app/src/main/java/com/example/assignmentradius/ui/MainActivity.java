package com.example.assignmentradius.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.assignmentradius.R;
import com.example.assignmentradius.adapters.FacilitiesAdapter;
import com.example.assignmentradius.databinding.ActivityMainBinding;
import com.example.assignmentradius.models.FacilityModel;
import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.viewmodel.ApiViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ApiViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBinding();
        initViewModel();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        viewModel.init();

        viewModel.getServerResponse().observe(this, serverResponse -> {
            if(serverResponse != null) {
                binding.progressBar.setVisibility(View.GONE);
                setResponseToList(serverResponse);
            }
        });
    }

    private void setResponseToList(ServerResponse serverResponse) {
        List<FacilityModel> facilities = serverResponse.getFacilities();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        FacilitiesAdapter adapter = new FacilitiesAdapter(this, facilities);
        adapter.setExclusionsList(serverResponse.getExclusions());
        binding.rvSelectionList.setLayoutManager(layoutManager);
        binding.rvSelectionList.setAdapter(adapter);
    }
}
