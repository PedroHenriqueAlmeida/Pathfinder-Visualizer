package com.pedro.pathfindervisualizer.enums;

import android.graphics.Color;

public enum PointStatusEnum {
    END(Color.RED),
    WALL(Color.GRAY),
    EMPTY(Color.WHITE),
    BEGIN(Color.GREEN),
    FINAL(Color.YELLOW),
    CHECKED(Color.BLUE);

    private final int color;

    PointStatusEnum(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
