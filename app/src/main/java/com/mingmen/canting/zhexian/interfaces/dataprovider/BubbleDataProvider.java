package com.mingmen.canting.zhexian.interfaces.dataprovider;

import com.mingmen.canting.zhexian.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
