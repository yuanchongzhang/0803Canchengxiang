package com.mingmen.canting.zhexian.interfaces.dataprovider;

import com.mingmen.canting.zhexian.components.YAxis;
import com.mingmen.canting.zhexian.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
