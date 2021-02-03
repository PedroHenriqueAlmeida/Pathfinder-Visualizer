package com.pedro.pathfindervisualizer.ui.view.spinner.model;

public class SpinnerData<T> {
    private final T data;
    private final String name;
    private final boolean selected;

    public SpinnerData(String name, T data) {
        this(name, data, false);
    }

    public SpinnerData(String name, T data, boolean selected) {
        this.name = name;
        this.data = data;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
