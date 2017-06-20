/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.widget.linechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.widget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/16 13:51
 * @Description : 
 * @Modify record :
 */

public class LineChart extends View{

    //画线条的画笔
    private Paint linePaint;

    //写文字的画笔
    private Paint textPaint;

    //画节点圆点的画笔
    private Paint nodePaint;

    //线条的颜色
    private int lineColor;

    //文字的颜色
    private int textColor;

    //节点圆点的颜色
    private int nodeColor;

    //线条的粗细
    private int lineSize;

    //文字的大小
    private int fontSize;

    //节点圆点的大小
    private int nodeSize;

    //两个节点圆点之间的距离
    private int nodeSpacing;

    //文字和线条之间的距离
    private int textPadding;

    //y轴方向最小值
    private int minValue = Integer.MAX_VALUE;

    //y轴方向最大值
    private int maxValue = Integer.MIN_VALUE;

    //要在图中展示的数据
    private HashMap<String, Integer> data = new HashMap<>();

    private List<String> keyList = new ArrayList<>();


    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChart);
        lineColor = mTypedArray.getColor(R.styleable.LineChart_line_color, 0xff333333);
        textColor = mTypedArray.getColor(R.styleable.LineChart_text_color, 0xff333333);
        nodeColor = mTypedArray.getColor(R.styleable.LineChart_node_color, 0xff333333);
        lineSize = (int) mTypedArray.getDimension(R.styleable.LineChart_line_size, dp2px(context, 1));
        fontSize = (int) mTypedArray.getDimension(R.styleable.LineChart_font_size, dp2px(context, 12));
        nodeSize = (int) mTypedArray.getDimension(R.styleable.LineChart_node_size, dp2px(context, 5));
        nodeSpacing = (int) mTypedArray.getDimension(R.styleable.LineChart_node_spacing, dp2px(context, 50));
        textPadding = (int) mTypedArray.getDimension(R.styleable.LineChart_text_padding, dp2px(context, 10));

        initPaint();
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineSize);
        linePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(fontSize);
        textPaint.setAntiAlias(true);

        nodePaint = new Paint();
        nodePaint.setStyle(Paint.Style.FILL);
        nodePaint.setColor(nodeColor);
        nodePaint.setAntiAlias(true);
    }

    public void setData(HashMap<String, Integer> data, List<String> keyList) {
        this.keyList.clear();
        this.keyList.addAll(keyList);
        this.data.clear();
        this.data.putAll(data);
        ViewGroup.LayoutParams layoutParmas = getLayoutParams();
        layoutParmas.width = (data.size() -1) * nodeSpacing + 2 * textPadding;
        setLayoutParams(layoutParmas);
        invalidate();
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || data.size() <= 0) {
            return;
        }

        int fontHight = getFontHeight(textPaint);
        List<Integer> valueList = new ArrayList<>();
        valueList.addAll(data.values());
        int hight = getHeight();
        int yhight = hight - fontHight * 2 - textPadding * 2;
        int max = getListMax(valueList);
        int min = getListMin(valueList);
        int total = max - min;

        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            int value = data.get(key);
            int keyY = hight - (int) (textPaint.getFontMetrics().bottom + 0.5f);
            int x = nodeSpacing * i + textPadding;
            int valueY = hight - (value - min) * yhight / total - fontHight - textPadding;

            if (i == 0) {
                canvas.drawText(key, x, keyY, textPaint);
                canvas.drawText(value +"", x, valueY - textPadding - nodeSize / 2, textPaint);
            } else if (i == keyList.size() -1) {
                canvas.drawText(key, x - textPaint.measureText(key), keyY, textPaint);
                canvas.drawText(value +"", x - textPaint.measureText(value + ""), valueY - textPadding - nodeSize / 2, textPaint);
            } else {
                canvas.drawText(key, x - textPaint.measureText(key) / 2, keyY, textPaint);
                canvas.drawText(value + "", x - textPaint.measureText(value + "") / 2, valueY - textPadding - nodeSize / 2, textPaint);
            }
            canvas.drawCircle(x, valueY, nodeSize / 2, nodePaint);

            if (i > 0) {
                canvas.drawLine(x - nodeSpacing, hight - (data.get(keyList.get(i -1)) - min) * yhight / total - fontHight - textPadding, x, valueY, linePaint);
            }
        }

        canvas.drawLine(0, hight - fontHight - textPadding, nodeSpacing * (keyList.size() - 1) + 2 * textPadding, hight - fontHight - textPadding, linePaint);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private  int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getListMax(List<Integer> integerList) {

        int max = Integer.MIN_VALUE;

        if (integerList == null) {
            return max;
        }

        for (int i = 0; i < integerList.size(); i++) {
            int value = integerList.get(i);

            if (value > max) {
                max = value;
            }
        }

        if (maxValue > max) {
            return maxValue;
        }

        return max;
    }

    private int getListMin(List<Integer> integerList) {
        int min = Integer.MAX_VALUE;

        if (integerList == null) {
            return min;
        }

        for (int i = 0; i < integerList.size(); i++) {
            int value = integerList.get(i);
            if (value < min) {
                min = value;
            }
        }

        if (minValue < min) {
            return minValue;
        }

        return min;
    }

    public int getFontHeight(Paint paint)
    {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
}
