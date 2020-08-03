package com.mingmen.canting.defineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写绘图方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();//创建画笔
        paint.setColor(Color.RED);//为画笔设置颜色
        paint.setStrokeWidth(10);//为画笔设置粗细
        paint.setStyle(Paint.Style.STROKE);//设置空心
        canvas.drawColor(Color.GREEN);//为画布设置颜色
        //设置等腰三角形的三点坐标
        Path path=new Path();//绘制多边形的类
        path.moveTo(100,100);//起始点
        path.lineTo(150,150);//右下角
        path.lineTo(50,150);//左下角
//        path.moveTo(90, 340);
//        path.lineTo(150, 340);
//        path.lineTo(120, 290);
        path.close();//闭合图形
        //绘制三角形
        canvas.drawPath(path,paint);
    }

}
