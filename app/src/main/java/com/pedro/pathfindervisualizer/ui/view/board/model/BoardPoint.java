package com.pedro.pathfindervisualizer.ui.view.board.model;

import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;

public class BoardPoint extends Point {
    private PointStatusEnum pointStatusEnum;

    public BoardPoint(int x, int y, PointStatusEnum pointStatusEnum) {
        super(x, y);
        this.pointStatusEnum = pointStatusEnum;
    }

    public PointStatusEnum getPointStatusEnum() {
        return pointStatusEnum;
    }

    public void setPointStatusEnum(PointStatusEnum pointStatusEnum) {
        this.pointStatusEnum = pointStatusEnum;
    }
}
