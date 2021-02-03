package com.pedro.pathfindervisualizer.ui.viewmodel;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pedro.pathfindervisualizer.pathfinder.PathfinderTypeEnum;
import com.pedro.pathfindervisualizer.ui.fragment.MainFragment;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private final MutableLiveData<PathfinderTypeEnum> pathfinderTypeEnum;
    private final MutableLiveData<SelectedOptionEnum> selectedOptionEnum;
    private final MutableLiveData<Double> animationTime;
    private MainFragment mainFragment;

    public MainViewModel() {
        this.animationTime = new MutableLiveData<>(1.00);
        this.selectedOptionEnum = new MutableLiveData<>(SelectedOptionEnum.NONE);
        this.pathfinderTypeEnum = new MutableLiveData<>(PathfinderTypeEnum.DEFAULT);
    }

    public LiveData<PathfinderTypeEnum> getPathfinderTypeEnum() {
        return pathfinderTypeEnum;
    }

    public void setPathfinderTypeEnum(PathfinderTypeEnum pathfinderTypeEnum) {
        this.pathfinderTypeEnum.setValue(pathfinderTypeEnum);
    }

    public LiveData<SelectedOptionEnum> getSelectedOptionEnum() {
        return selectedOptionEnum;
    }

    public void setSelectedOptionEnum(SelectedOptionEnum selectedOptionEnum) {
        this.selectedOptionEnum.setValue(selectedOptionEnum);
    }

    public LiveData<Double> getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(Double animationTime) {
        this.animationTime.setValue(animationTime);
    }

    public MainFragment getMainFragment() {
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
        }
        return mainFragment;
    }

    public enum SelectedOptionEnum {
        NONE,
        START,
        CLEAR
    }
}
