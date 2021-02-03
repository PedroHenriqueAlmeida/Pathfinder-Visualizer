package com.pedro.pathfindervisualizer.ui.view.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.pedro.pathfindervisualizer.databinding.ViewSpinnerBinding;
import com.pedro.pathfindervisualizer.ui.view.spinner.model.SpinnerData;

import java.util.List;

public class SpinnerView<T> extends LinearLayout {
    private ViewSpinnerBinding binding;

    public SpinnerView(Context context) {
        super(context);
        initView(context);
    }

    public SpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        if (binding == null) {
            return;
        }
        binding.spinner.setOnItemSelectedListener(listener);
    }

    public void setSpinnerData(List<SpinnerData<T>> spinnerData) {
        if (binding == null) {
            return;
        }
        ArrayAdapter<SpinnerData<T>> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.addAll(spinnerData);
        binding.spinner.setAdapter(arrayAdapter);

        int selectedItem = 0;
        for (int i = 0; i < spinnerData.size(); i++) {
            if (spinnerData.get(i).isSelected()) {
                selectedItem = i;
            }
        }
        binding.spinner.setSelection(selectedItem);
    }

    public T getSelected() {
        if (binding == null) {
            return null;
        }
        return ((SpinnerData<T>) binding.spinner.getSelectedItem()).getData();
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = ViewSpinnerBinding.inflate(layoutInflater, this, true);
    }
}
