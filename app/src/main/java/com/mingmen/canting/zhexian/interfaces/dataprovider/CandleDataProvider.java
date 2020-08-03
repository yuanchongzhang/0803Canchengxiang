package com.mingmen.canting.zhexian.interfaces.dataprovider;

import com.mingmen.canting.zhexian.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
