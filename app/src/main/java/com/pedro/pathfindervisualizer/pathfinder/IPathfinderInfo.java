package com.pedro.pathfindervisualizer.pathfinder;

import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;

public interface IPathfinderInfo {
    boolean validPosition(Point point);

    PointStatusEnum pointStatus(Point point);

    void updatePosition(Point point, PointStatusEnum pointStatusEnum);
}
