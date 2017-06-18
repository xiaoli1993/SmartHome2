package com.heiman.widget.togglebutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.heiman.widget.R;


/**
 * @author ThinkPad
 */
public class ToggleButton extends View {
    private SpringSystem springSystem;
    private Spring spring;
    /** */
    private float radius;

    public void setOnColor(int onColor) {
        this.onColor = onColor;
    }

    /**
     * 瀵拷閸氼垶顤侀懝锟�?
     */
    private int onColor = Color.parseColor("#4ebb7f");

    /**
     * 閸忔娊妫存０婊嗗�?
     */
    private int offBorderColor = Color.parseColor("#dadbda");
    /**
     * 閻忔媽澹婄敮锕傤杹閼癸拷
     */
    private int offColor = Color.parseColor("#ffffff");
    /**
     * 閹靛鐒烘０婊嗗�?
     */
    private int spotColor = Color.parseColor("#ffffff");
    /**
     * 鏉堣顢嬫０婊嗗�?
     */
    private int borderColor = offBorderColor;
    /**
     * 閻㈣崵鐟�?
     */
    private Paint paint;
    /**
     * 瀵拷閸忓磭濮搁�?锟�
     */
    private boolean toggleOn = false;

    /**
     * 鏉堣顢嬫径褍鐨�?
     */
    private int borderWidth = 2;
    /**
     * 閸ㄥ倻娲挎稉顓炵�?
     */
    private float centerY;
    /**
     * 閹稿鎸抽惃鍕磻婵鎷扮紒鎾存将娴ｅ秶鐤�?
     */
    private float startX, endX;
    /**
     * 閹靛鐒篨娴ｅ秶鐤嗛惃鍕付鐏忓繐鎷伴張锟芥径褍锟斤�?
     */
    private float spotMinX, spotMaxX;
    /**
     * 閹靛鐒烘径褍鐨�?
     */
    private int spotSize;
    /**
     * 閹靛鐒篨娴ｅ秶鐤�
     */
    private float spotX;
    /**
     * 閸忔娊妫撮弮璺哄敶闁劎浼嗛懝鎻掔敨妤傛ê瀹�
     */
    private float offLineWidth;
    /** */
    private RectF rect = new RectF();
    /**
     * 姒涙顓绘担璺ㄦ暏閸斻劎鏁�
     */
    private boolean defaultAnimate = true;

    private OnToggleChanged listener;

    private ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        spring.removeListener(springListener);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        spring.addListener(springListener);
    }

    public void setup(AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Style.FILL);
        paint.setStrokeCap(Cap.ROUND);

        springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig
                .fromOrigamiTensionAndFriction(50, 7));

        this.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                toggle(defaultAnimate);
            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ToggleButton);
        offBorderColor = typedArray.getColor(
                R.styleable.ToggleButton_offBorderColor, offBorderColor);
        onColor = typedArray
                .getColor(R.styleable.ToggleButton_onColor, onColor);
        spotColor = typedArray.getColor(R.styleable.ToggleButton_spotColor,
                spotColor);
        offColor = typedArray.getColor(R.styleable.ToggleButton_offColor,
                offColor);
        borderWidth = typedArray.getDimensionPixelSize(
                R.styleable.ToggleButton_borderWidth, borderWidth);
        defaultAnimate = typedArray.getBoolean(
                R.styleable.ToggleButton_animate, defaultAnimate);
        typedArray.recycle();

        borderColor = offBorderColor;
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean animate) {
        toggleOn = !toggleOn;
        takeEffect(animate);

        if (listener != null) {
            listener.onToggle(this, toggleOn);
        }
    }

    public void toggleOn() {
        setToggleOn();
        if (listener != null) {
            listener.onToggle(this, toggleOn);
        }
    }

    public void toggleOff() {
        setToggleOff();
        if (listener != null) {
            listener.onToggle(this, toggleOn);
        }
    }

    /**
     * 鐠佸墽鐤嗛弰鍓с仛閹存劖澧�?锟介弽宄扮�?閿涘奔绗夋导姘承曢崣鎲坥ggle娴滃娆�?
     */
    public void setToggleOn() {
        setToggleOn(true);
    }

    /**
     * @param animate
     */
    public void setToggleOn(boolean animate) {
        toggleOn = true;
        takeEffect(animate);
    }

    /**
     * 鐠佸墽鐤嗛弰鍓с仛閹存劕鍙ч梻顓熺壉瀵骏绱濇稉宥勭窗鐟欙箑褰�??oggle娴滃娆�?
     */
    public void setToggleOff() {
        setToggleOff(true);
    }

    public void setToggleOff(boolean animate) {
        toggleOn = false;
        takeEffect(animate);
    }

    private void takeEffect(boolean animate) {
        if (animate) {
            spring.setEndValue(toggleOn ? 1 : 0);
        } else {
            // 鏉╂瑩鍣峰▽鈩冩箒鐠嬪啰鏁pring閿涘本澧嶆禒顧筽ring闁插瞼娈戣ぐ鎾冲閸婂吋鐥呴張澶婂綁閺囪揪绱濇潻娆撳櫡鐟曚浇顔曠純顔荤娑撳绱濋崥灞绢劄娑撱�?�绔熼惃鍕秼閸撳秴锟斤�?
            spring.setCurrentValue(toggleOn ? 1 : 0);
            calculateEffect(toggleOn ? 1 : 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED
                || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
                    MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED
                || heightSize == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                    MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();

        radius = Math.min(width, height) * 0.5f;
        centerY = radius;
        startX = radius;
        endX = width - radius;
        spotMinX = startX + borderWidth;
        spotMaxX = endX - borderWidth;
        spotSize = height - 4 * borderWidth;
        spotX = toggleOn ? spotMaxX : spotMinX;
        offLineWidth = 0;
    }

    SimpleSpringListener springListener = new SimpleSpringListener() {
        @Override
        public void onSpringUpdate(Spring spring) {
            final double value = spring.getCurrentValue();
            calculateEffect(value);
        }
    };

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    @Override
    public void draw(Canvas canvas) {
        //
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (offLineWidth > 0) {
            final float cy = offLineWidth * 0.5f;
            rect.set(spotX - cy, centerY - cy, endX + cy, centerY + cy);
            paint.setColor(offColor);
            canvas.drawRoundRect(rect, cy, cy, paint);
        }

        rect.set(spotX - 1 - radius, centerY - radius, spotX + 1.1f + radius,
                centerY + radius);
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        final float spotR = spotSize * 0.5f;
        rect.set(spotX - spotR, centerY - spotR, spotX + spotR, centerY + spotR);
        paint.setColor(spotColor);
        canvas.drawRoundRect(rect, spotR, spotR, paint);

    }

    /**
     * @param value
     */
    private void calculateEffect(final double value) {
        final float mapToggleX = (float) SpringUtil.mapValueFromRangeToRange(
                value, 0, 1, spotMinX, spotMaxX);
        spotX = mapToggleX;

        float mapOffLineWidth = (float) SpringUtil.mapValueFromRangeToRange(
                1 - value, 0, 1, 10, spotSize);

        offLineWidth = mapOffLineWidth;

        final int fb = Color.blue(onColor);
        final int fr = Color.red(onColor);
        final int fg = Color.green(onColor);

        final int tb = Color.blue(offBorderColor);
        final int tr = Color.red(offBorderColor);
        final int tg = Color.green(offBorderColor);

        int sb = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fb,
                tb);
        int sr = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fr,
                tr);
        int sg = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fg,
                tg);

        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        borderColor = Color.rgb(sr, sg, sb);

        postInvalidate();
    }

    /**
     * @author ThinkPad
     */
    public interface OnToggleChanged {
        /**
         * @param on
         */
        public void onToggle(View view, boolean on);
    }

    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }

    public boolean isAnimate() {
        return defaultAnimate;
    }

    public void setAnimate(boolean animate) {
        this.defaultAnimate = animate;
    }

    public boolean isToggleOn() {
        return toggleOn;
    }

}
