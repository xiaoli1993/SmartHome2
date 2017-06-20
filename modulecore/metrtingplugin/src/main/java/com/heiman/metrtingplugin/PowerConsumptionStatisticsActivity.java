package com.heiman.metrtingplugin;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class PowerConsumptionStatisticsActivity extends GwBaseActivity implements RadioGroup.OnCheckedChangeListener{

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private RadioGroup rgpPowerElectStatistics;
    private RadioButton rdoWeek;
    private RadioButton rdoMonth;
    private RadioButton rdoYear;
    private RelativeLayout rlTodayPowerContainer;
    private RelativeLayout rlWeekPowerContainer;
    private RelativeLayout rlMonthPowerContainer;
    private RelativeLayout rlYearPowerContainer;
    private TextView txtTodayPowerElectStatistics;
    private TextView txtWeekPowerElectStatistics;
    private TextView txtMonthPowerElectStatistics;
    private TextView txtYearPowerElectStatistics;
    private LineChartView chartPowerElectStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metrtingplugin_activity_power_consumption_statistics);
        StatusBarUtil.setTranslucent(this, 100);
        XlinkUtils.StatusBarLightMode(this);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        rgpPowerElectStatistics = (RadioGroup) findViewById(R.id.rgp_power_elect_statistics);
        rdoWeek = (RadioButton) findViewById(R.id.rdo_week);
        rdoMonth = (RadioButton) findViewById(R.id.rdo_month);
        rdoYear = (RadioButton) findViewById(R.id.rdo_year);
        rlTodayPowerContainer = (RelativeLayout) findViewById(R.id.rl_today_power_container);
        rlWeekPowerContainer = (RelativeLayout) findViewById(R.id.rl_week_power_container);
        rlMonthPowerContainer = (RelativeLayout) findViewById(R.id.rl_month_power_container);
        rlYearPowerContainer = (RelativeLayout) findViewById(R.id.rl_year_power_container);
        txtTodayPowerElectStatistics = (TextView) findViewById(R.id.txt_today_power_elect_statistics);
        txtWeekPowerElectStatistics = (TextView) findViewById(R.id.txt_week_power_elect_statistics);
        txtMonthPowerElectStatistics = (TextView) findViewById(R.id.txt_month_power_elect_statistics);
        txtYearPowerElectStatistics = (TextView) findViewById(R.id.txt_year_power_elect_statistics);
        chartPowerElectStatistics = (LineChartView) findViewById(R.id.chart_power_elect_statistics);

        titleBarReturn.setOnClickListener(this);
        rgpPowerElectStatistics.setOnCheckedChangeListener(this);

        titleBarMore.setVisibility(View.GONE);
        titleBarShare.setVisibility(View.GONE);

        titleBarTitle.setText(R.string.txt_metrting_plugin_elect);

        titleBar.setBackgroundColor(getResources().getColor(R.color.metrting_plugin_bg_color2));
        titleBarTitle.setTextColor(getResources().getColor(R.color.white));
        titleBarReturn.setImageResource(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (subDevice != null) {

        }

        if (rgpPowerElectStatistics.getCheckedRadioButtonId() == rdoWeek.getId()) {
            setData(initWeedkData());
        } else if (rgpPowerElectStatistics.getCheckedRadioButtonId() == rdoMonth.getId()) {
            setData(initMonthData());
        } else if (rgpPowerElectStatistics.getCheckedRadioButtonId() == rdoYear.getId()) {
            setData(initYearData());
        }
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.metrtingplugin_activity_power_consumption_statistics, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == titleBarReturn.getId()) {
            finish();
            return;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == rdoWeek.getId()) {
            LogUtil.e("点击查看7天使用电量曲线图！");
            setData(initWeedkData());
            return;
        }

        if (checkedId == rdoMonth.getId()) {
            LogUtil.e("点击查看30天使用电量曲线图！");
            setData(initMonthData());
            return;
        }

        if (checkedId == rdoYear.getId()) {
            LogUtil.e("点击查看1年使用电量曲线图！");
            setData(initYearData());
            return;
        }
    }

    /**
     * 产生7天的用电量数据
     * @return
     */
    private List<PointValue> initWeedkData() {
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            values.add(new PointValue(i, (float) (Math.random() * 100)));
        }
        return values;
    }

    /**
     * 产生30天的用电量数据
     * @return
     */
    private List<PointValue> initMonthData() {
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values.add(new PointValue(i, (float) (Math.random() * 100)));
        }
        return values;
    }

    /**
     * 产生一年的用电量数据
     * @return
     */
    private List<PointValue> initYearData() {
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < 365; i++) {
            values.add(new PointValue(i, (float) (Math.random() * 100)));
        }
        return values;
    }

    /**
     * 给图表设置数据以便显示
     * @param values
     */
    private void setData(List<PointValue> values) {
        List<Line> lines = new ArrayList<>();
        Line line = new Line(values).setColor(getResources().getColor(R.color.white));
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setFilled(true);//是否填充曲线的面积
//		line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示
        line.setFormatter(new SimpleLineChartValueFormatter(2));
        line.setShaderColors(new int[] {getResources().getColor(R.color.metrting_chart_bg_color1), getResources().getColor(R.color.metrting_chart_bg_color1),
                getResources().getColor(R.color.metrting_chart_bg_color1), getResources().getColor(R.color.metrting_chart_bg_color1),
                getResources().getColor(R.color.metrting_chart_bg_color1), getResources().getColor(R.color.metrting_chart_bg_color1),
                getResources().getColor(R.color.metrting_chart_bg_color2), getResources().getColor(R.color.metrting_chart_bg_color3),
                getResources().getColor(R.color.metrting_chart_bg_color4), getResources().getColor(R.color.metrting_chart_bg_color5)});
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        chartPowerElectStatistics.setInteractive(true);
        chartPowerElectStatistics.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        chartPowerElectStatistics.setMaxZoom((float) 3);//缩放比例
        chartPowerElectStatistics.setLineChartData(data);
        chartPowerElectStatistics.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(chartPowerElectStatistics.getMaximumViewport());
        v.left = 0;
        v.right= values.size() - 1;
        v.top = getMaxPower(values) + getMaxPower(values) / 3;
        v.bottom = 0;
        chartPowerElectStatistics.setMaximumViewport(v);
        chartPowerElectStatistics.setCurrentViewport(v);
    }

    private float getMaxPower(List<PointValue> values) {
        float max = Float.MIN_VALUE;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getY() > max) {
                max = values.get(i).getY();
            }
        }
        return max;
    }

    private float getMinPower(List<PointValue> values) {
        float min = Float.MAX_VALUE;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getY() < min) {
                min = values.get(i).getY();
            }
        }
        return min;
    }
}
