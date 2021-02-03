package com.pedro.pathfindervisualizer.pathfinder;

import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPathfinder {
    protected final Map<String, Point> pointsParent;
    protected final IPathfinderInfo pathfinderInfo;
    private boolean running = false;

    public AbstractPathfinder(IPathfinderInfo pathfinderInfo) {
        this.pointsParent = new HashMap<>();
        this.pathfinderInfo = pathfinderInfo;
    }

    public final void getFinalPath(Point begin, Point end) {
        running = true;
        findPath(begin, end);
        List<Point> finalPath = getFinalPath(new ArrayList<>(), end);
        for (Point point : finalPath) {
            pathfinderInfo.updatePosition(point, PointStatusEnum.FINAL);
        }
        running = false;
        clear();
    }

    public boolean isRunning() {
        return running;
    }

    protected final void clear() {
        pointsParent.clear();
    }

    protected String getKey(Point point) {
        if (point == null) {
            return null;
        }
        return point.getX() + "#" + point.getY();
    }

    protected boolean canPutPoint(String key, Point parent) {
        return !pointsParent.containsKey(key);
    }

    protected final boolean isEmptyPoint(Point point) {
        return PointStatusEnum.EMPTY.equals(pathfinderInfo.pointStatus(point));
    }

    protected abstract void findPath(Point begin, Point end);

    private List<Point> getFinalPath(List<Point> points, Point point) {
        String pointKey = getKey(point);
        Point parent = pointsParent.get(pointKey);
        String parentKey = getKey(parent);
        // if the point points to itself, it'll be considered the begin point
        if (pointKey.equals(parentKey)) {
            points.add(point);
        } else {
            getFinalPath(points, parent);
            points.add(point);
        }
        return points;
    }
}
