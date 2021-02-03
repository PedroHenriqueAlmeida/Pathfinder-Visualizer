package com.pedro.pathfindervisualizer.ui.view.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;
import com.pedro.pathfindervisualizer.utils.ThreadUtils;
import com.pedro.pathfindervisualizer.ui.view.board.model.BoardPoint;

public class PathfinderBoardView extends View {
    private final int baseAnimationTime = 40;
    private final int rectSize = 40;
    private PointStatusEnum optionStatus = null;
    private BoardPoint[][] boardPoints = null;
    private boolean blocked = false;
    private int animationTime = 40;
    private BoardPoint begin;
    private BoardPoint end;

    public PathfinderBoardView(Context context) {
        super(context);
    }

    public PathfinderBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAnimationTime(double animationTime) {
        this.animationTime = (int) (baseAnimationTime / animationTime);
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public void clear() {
        boardPoints = null;
        invalidate();
    }

    public void updatePoint(Point point, PointStatusEnum pointStatusEnum) {
        ThreadUtils.sleep(animationTime);
        if (!onLimit(point.getX(), point.getY())) {
            return;
        }
        boardPoints[point.getY()][point.getX()].setPointStatusEnum(pointStatusEnum);
        invalidate();
    }

    public Point boardLimit() {
        if (boardPoints == null) {
            return new Point(-1, -1);
        }
        return new Point(boardPoints[0].length - 1, boardPoints.length - 1);
    }

    public PointStatusEnum pointStatus(Point point) {
        if (boardPoints == null) {
            return PointStatusEnum.EMPTY;
        }
        return boardPoints[point.getY()][point.getX()].getPointStatusEnum();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() / rectSize);
        int y = (int) (event.getY() / rectSize);
        if (blocked || !onLimit(x, y)) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            loadOptionStatus(x, y);
            if (!isObstacle(x, y)) {
                if (PointStatusEnum.BEGIN.equals(optionStatus)) {
                    begin.setPointStatusEnum(PointStatusEnum.EMPTY);
                    begin = boardPoints[y][x];
                } else if (PointStatusEnum.END.equals(optionStatus)) {
                    end.setPointStatusEnum(PointStatusEnum.EMPTY);
                    end = boardPoints[y][x];
                }
                boardPoints[y][x].setPointStatusEnum(optionStatus);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            optionStatus = null;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView();
        int x, y;
        for (int i = 0; i < boardPoints.length; i++) {
            y = i * rectSize;
            for (int j = 0; j < boardPoints[i].length; j++) {
                x = j * rectSize;
                drawRect(canvas, x, y, boardPoints[i][j].getPointStatusEnum().getColor(), Paint.Style.FILL);
                drawRect(canvas, x, y, Color.BLACK, Paint.Style.STROKE);
            }
        }
    }

    private void initView() {
        if (boardPoints != null) {
            return;
        }
        boardPoints = new BoardPoint[getMeasuredHeight() / rectSize][getMeasuredWidth() / rectSize];
        for (int i = 0; i < boardPoints.length; i++) {
            for (int j = 0; j < boardPoints[i].length; j++) {
                boardPoints[i][j] = new BoardPoint(j, i, PointStatusEnum.EMPTY);
            }
        }
        begin = boardPoints[0][0];
        begin.setPointStatusEnum(PointStatusEnum.BEGIN);
        end = boardPoints[boardPoints.length - 1][boardPoints[0].length - 1];
        end.setPointStatusEnum(PointStatusEnum.END);
        invalidate();
    }

    private void drawRect(Canvas canvas, int x, int y, int color, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(5);
        canvas.drawRect(x, y, x + rectSize, y + rectSize, paint);
    }

    private void loadOptionStatus(int x, int y) {
        if (optionStatus != null) {
            return;
        }
        switch (boardPoints[y][x].getPointStatusEnum()) {
            case BEGIN:
                optionStatus = PointStatusEnum.BEGIN;
                break;
            case END:
                optionStatus = PointStatusEnum.END;
                break;
            default:
                optionStatus = PointStatusEnum.WALL;
        }
    }

    private boolean onLimit(int x, int y) {
        if (boardPoints == null || x < 0 || y < 0) {
            return false;
        }
        return x < boardPoints[0].length && y < boardPoints.length;
    }

    private boolean isObstacle(int x, int y) {
        if (!onLimit(x, y)) {
            return true;
        }
        PointStatusEnum pointStatusEnum = boardPoints[y][x].getPointStatusEnum();
        switch (pointStatusEnum) {
            case BEGIN:
            case END:
            case WALL:
                return true;
            default:
                return false;
        }
    }
}
