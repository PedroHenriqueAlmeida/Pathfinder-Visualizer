package com.pedro.pathfindervisualizer.pathfinder;

import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DijkstraPathfinder extends AbstractPathfinder {
    public DijkstraPathfinder(IPathfinderInfo pathfinderInfo) {
        super(pathfinderInfo);
    }

    @Override
    protected void findPath(Point begin, Point end) {
        Queue<Point> dijkstra = new ArrayDeque<>();
        dijkstra.add(begin);
        pointsParent.put(getKey(begin), begin);
        while (!dijkstra.isEmpty()) {
            Point point = dijkstra.poll();
            if (point.equals(end)) {
                break;
            }
            if (isEmptyPoint(point)) {
                pathfinderInfo.updatePosition(point, PointStatusEnum.CHECKED);
            }
            dijkstra.addAll(getChildren(point));
        }
    }

    private List<Point> getChildren(Point point) {
        int x = point.getX();
        int y = point.getY();

        List<Point> points = new ArrayList<>();

        Point up = new Point(x, y - 1);
        Point left = new Point(x - 1, y);
        Point down = new Point(x, y + 1);
        Point right = new Point(x + 1, y);

        addParent(points, up, point);
        addParent(points, left, point);
        addParent(points, down, point);
        addParent(points, right, point);

        return points;
    }

    private void addParent(List<Point> points, Point point, Point parent) {
        if (!pathfinderInfo.validPosition(point)) {
            return;
        }
        String key = getKey(point);
        if (canPutPoint(key, parent)) {
            points.add(point);
            pointsParent.put(key, parent);
        }
    }
}
