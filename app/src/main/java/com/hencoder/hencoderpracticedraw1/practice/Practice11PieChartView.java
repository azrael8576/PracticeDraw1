package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.model.Data;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {
    private final static Float OFFSET_PROPORTION = 0.1f; //最大數量圓餅位置偏移
    private final static Float GAP_ANGLE = 2.0f; //扇形間距角度
    private final static Float PIE_RADIUS = 300f; //圓餅圖半徑

    private List<Data> datas;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF(-PIE_RADIUS, -PIE_RADIUS, PIE_RADIUS, PIE_RADIUS);
    private float total;
    private float max;
    private boolean isMove = false;

    private float startAngle;      // 開始的角度
    private float sweepAngle;      // 掃過的角度
    private float lineAngle;       // 當前扇形角度

    private float lineStartX;
    private float lineStartY;
    private float lineEndX;
    private float lineEndY;

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        datas = new ArrayList<>();
        Data data = new Data("Gingerbread", 10.0f, Color.WHITE);
        datas.add(data);
        data = new Data("Ice Cream Sandwich", 18.0f, Color.MAGENTA);
        datas.add(data);
        data = new Data("Jelly Bean", 22.0f, Color.GRAY);
        datas.add(data);
        data = new Data("KitKat", 27.0f, Color.GREEN);
        datas.add(data);
        data = new Data("Lollipop", 40.0f, Color.BLUE);
        datas.add(data);
        data = new Data("Marshmallow", 60.0f, Color.RED);
        datas.add(data);
        data = new Data("Nougat", 33.5f, Color.YELLOW);
        datas.add(data);

        total = 0.0f;
        max = Float.MIN_VALUE;
        for (Data d : datas) {
            total += d.getNumber();
            max = Math.max(max, d.getNumber());
        }

        paint.setStrokeWidth(3);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        startAngle = 0f;
        lineStartX = 0f;
        lineStartY = 0f;

        translateToZero(canvas);
        for (Data data : datas) {
            if (isMove) {
                resetOffsetProportion(canvas);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(data.getColor());
            sweepAngle = data.getNumber() / total * 360f;
            lineAngle = startAngle + sweepAngle / 2;
            lineStartX = PIE_RADIUS * (float) Math.cos(lineAngle / 180 * Math.PI);
            lineStartY = PIE_RADIUS * (float) Math.sin(lineAngle / 180 * Math.PI);
            lineEndX = PIE_RADIUS * (float) Math.cos(lineAngle / 180 * Math.PI) * 1.1f;
            lineEndY = PIE_RADIUS * (float) Math.sin(lineAngle / 180 * Math.PI) * 1.1f;
            drawArc(canvas, data);
            drawTitle(canvas, data);
            startAngle += sweepAngle;
        }
    }

    private void translateToZero(Canvas canvas) {
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);  // 將畫布(0, 0)座標點移置 canvas 中心
    }

    private void resetOffsetProportion(Canvas canvas) {
        canvas.translate(-lineStartX * OFFSET_PROPORTION, -lineStartY * OFFSET_PROPORTION);
        isMove = false;
    }

    private void setOffsetProportion(Canvas canvas) {
        canvas.translate(lineStartX * OFFSET_PROPORTION, lineStartY * OFFSET_PROPORTION);
        isMove = true;
    }

    private void drawArc(Canvas canvas, Data data) {
        if (data.getNumber() == max) {
            setOffsetProportion(canvas);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
        } else {
            canvas.drawArc(rectF, startAngle, sweepAngle - GAP_ANGLE, true, paint);
        }
    }

    private void drawTitle(Canvas canvas, Data data) {
        paint.setColor(Color.WHITE);
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);
        if (lineAngle > 90 && lineAngle <= 270) {
            canvas.drawLine(lineEndX, lineEndY, lineEndX - 50, lineEndY, paint);
            canvas.drawText(data.getName(), lineEndX - 50 - 10 - paint.measureText(data.getName()), lineEndY, paint);
        } else {
            canvas.drawLine(lineEndX, lineEndY, lineEndX + 50, lineEndY, paint);
            canvas.drawText(data.getName(), lineEndX + 50 + 10, lineEndY, paint);
        }
    }
}
