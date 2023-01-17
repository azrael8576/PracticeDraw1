package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.model.Data;

import java.util.ArrayList;
import java.util.List;

public class Practice10HistogramView extends View {
    private final static String CHAR_TITLE = "直方圖";
    private final static Float CHAR_WIDTH_PROPORTION = 0.8f;
    private final static Float CHAR_HEIGHT_PROPORTION = 0.6f;

    private List<Data> datas;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float startX;
    private float space;
    private float width;
    private float max;

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        datas = new ArrayList<>();
        Data data = new Data("Froyo", 0f, Color.GREEN);
        datas.add(data);
        data = new Data("GB", 5.0f, Color.GREEN);
        datas.add(data);
        data = new Data("ICS", 5.0f, Color.GREEN);
        datas.add(data);
        data = new Data("JB", 27.0f, Color.GREEN);
        datas.add(data);
        data = new Data("KitKat", 40.0f, Color.GREEN);
        datas.add(data);
        data = new Data("L", 60.0f, Color.GREEN);
        datas.add(data);
        data = new Data("M", 22.0f, Color.GREEN);
        datas.add(data);

        max = Float.MIN_VALUE;
        for (Data d : datas) {
            max = Math.max(max, d.getNumber());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        drawCharTitle(canvas);
        translateToZero(canvas);
        drawAxis(canvas);

        width = (canvas.getWidth() * CHAR_WIDTH_PROPORTION - 100) / datas.size() * 0.8f;
        space = (canvas.getWidth() * CHAR_WIDTH_PROPORTION - 100) / datas.size() * 0.2f;

        startX = 0f;

        paint.setTextSize(36);
        paint.setStyle(Paint.Style.FILL);
        for (Data data : datas) {
            paint.setColor(data.getColor());
            canvas.drawRect(
                    startX + space,
                    -(data.getNumber() / max * canvas.getHeight() * CHAR_HEIGHT_PROPORTION),
                    startX + space + width,
                    0,
                    paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(
                    data.getName(),
                    startX + space + (width - paint.measureText(data.getName())) / 2,
                    40,
                    paint);

            startX += width + space;
        }
    }

    private void drawCharTitle(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(72);

        canvas.drawText(
                CHAR_TITLE,
                (canvas.getWidth() - paint.measureText(CHAR_TITLE)) / 2,
                canvas.getHeight() * 0.9f,
                paint
        );
    }

    private void translateToZero(Canvas canvas) {
        canvas.translate(canvas.getWidth() * 0.1f, canvas.getHeight() * 0.7f); // 將 canvas 移置直方圖原點位置
    }

    private void drawAxis(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawLine(
                0,
                0,
                canvas.getWidth() * CHAR_WIDTH_PROPORTION,
                0,
                paint
        );
        canvas.drawLine(
                0,
                0,
                0,
                -canvas.getHeight() * CHAR_HEIGHT_PROPORTION,
                paint
        );
    }
}
