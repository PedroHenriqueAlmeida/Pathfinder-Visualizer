package com.pedro.pathfindervisualizer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.pedro.pathfindervisualizer.R;
import com.pedro.pathfindervisualizer.databinding.ActivityMainBinding;
import com.pedro.pathfindervisualizer.pathfinder.PathfinderTypeEnum;
import com.pedro.pathfindervisualizer.ui.view.spinner.model.SpinnerData;
import com.pedro.pathfindervisualizer.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setSpinnerDataPathfinderType(getSpinnerDataPathfinderType());
        binding.setSpinnerDataAnimationTime(getSpinnerDataAnimationTime());
        binding.animationTimeSpinner.setOnItemSelectedListener(getAnimationTimeSpinnerListener());
        binding.pathfinderSpinner.setOnItemSelectedListener(getPathfinderSpinnerListener());
        binding.startButton.setOnClickListener(v -> {
            viewModel.setSelectedOptionEnum(MainViewModel.SelectedOptionEnum.START);
        });
        binding.clearButton.setOnClickListener(v -> {
            viewModel.setSelectedOptionEnum(MainViewModel.SelectedOptionEnum.CLEAR);
        });
        initFragment();
    }

    private AdapterView.OnItemSelectedListener getAnimationTimeSpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setAnimationTime((Double) binding.animationTimeSpinner.getSelected());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private AdapterView.OnItemSelectedListener getPathfinderSpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setPathfinderTypeEnum((PathfinderTypeEnum) binding.pathfinderSpinner.getSelected());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private List<SpinnerData<PathfinderTypeEnum>> getSpinnerDataPathfinderType() {
        List<SpinnerData<PathfinderTypeEnum>> spinnerData = new ArrayList<>();
        spinnerData.add(new SpinnerData<>(PathfinderTypeEnum.DIJKSTRA.name(), PathfinderTypeEnum.DIJKSTRA, true));
        return spinnerData;
    }

    private List<SpinnerData<Double>> getSpinnerDataAnimationTime() {
        List<SpinnerData<Double>> spinnerData = new ArrayList<>();
        spinnerData.add(new SpinnerData<>("0.25x", 0.25));
        spinnerData.add(new SpinnerData<>("0.50x", 0.50));
        spinnerData.add(new SpinnerData<>("1.00x", 1.00, true));
        spinnerData.add(new SpinnerData<>("2.00x", 2.00));
        spinnerData.add(new SpinnerData<>("4.00x", 4.00));
        return spinnerData;
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, viewModel.getMainFragment()).addToBackStack(null);
        fragmentTransaction.commit();
    }
}