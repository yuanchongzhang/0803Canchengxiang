package com.mingmen.canting.zhexian.interfaces.dataprovider;

import com.mingmen.canting.zhexian.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
