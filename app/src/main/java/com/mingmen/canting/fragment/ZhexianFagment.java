package com.mingmen.canting.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.ShopcatAdapter;
import com.mingmen.canting.defineview.DetailsMarkerView;
import com.mingmen.canting.materialcalendar.DateTimeHelper;
import com.mingmen.canting.materialcalendar.MaterialCalendarDialog;
import com.mingmen.canting.model.GoodsInfo;
import com.mingmen.canting.model.StoreInfo;
import com.mingmen.canting.util.UtilTool;
import com.mingmen.canting.util.UtilsLog;
import com.mingmen.canting.zhexian.charts.LineChart;
import com.mingmen.canting.zhexian.components.XAxis;
import com.mingmen.canting.zhexian.components.YAxis;
import com.mingmen.canting.zhexian.data.Entry;
import com.mingmen.canting.zhexian.data.LineData;
import com.mingmen.canting.zhexian.data.LineDataSet;
import com.mingmen.canting.zhexian.formatter.ValueFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ZhexianFagment extends Fragment {

    Unbinder unbinder;
    private String mTitle;
    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private LineChart lineChart;
    List<Entry> listOne = new ArrayList<>();
    List<Entry> listTwo = new ArrayList<>();

    public static ZhexianFagment getInstance(String title) {
        ZhexianFagment sf = new ZhexianFagment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private EditText edt_starttime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zhexian, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);

        unbinder = ButterKnife.bind(this, v);
        lineChart = (LineChart) v.findViewById(R.id.lineChart);
        initView();//初始化控件
        initData();//初始化数据
        initChart();//数据设置
        initChartBackground();//设置x轴y轴背景
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void lazyLoad() {
        if (!isLoading && isUIVisible) {
//            这里只加载一次数据
            Log.d("SimpleCardFragment", "66666");
            //数据加载完毕,恢复标记,防止重复加载
            isLoading = true;
            isUIVisible = false;
        }

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();//调用下面的方法
        } else {
            isUIVisible = false;
        }
    }


    private void initChartBackground() {
        XAxis xAxis = lineChart.getXAxis();//得到x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的位置，在整个图形的底部
        xAxis.setLabelCount(listOne.size(), false); //设置X轴刻度 第一个参数是想要x轴有多少条刻度,第二个参数true是将刻度数设置为你的第一个参数的数量 ，false是将刻度数设置为你的第一个参数的数量+1（0.0点也要算哦）
        xAxis.setGranularity(1f);//设置x轴坐标间的最小间距
        xAxis.setAxisMaximum(listOne.size() - 1);//设置x轴的最大范围
        xAxis.setAxisMinimum(0f);//设置x轴的最小范围
        xAxis.setGridColor(Color.TRANSPARENT);//设置x轴刻度透明
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return listOne.get((int) value).getX() + "天";
            }
        });

        //Y轴不是有左右两边嘛，这就是获取左右两边的y轴
        YAxis axisRight = lineChart.getAxisRight();
        YAxis axisLeft = lineChart.getAxisLeft();
        axisRight.setEnabled(false);//将右边的y轴隐藏
        //y轴最大值最小值范围
//        axisLeft.setAxisMaximum(550f);
        axisLeft.setAxisMaximum(550f);
        axisLeft.setAxisMinimum(0f);
        //文字颜色
        axisLeft.setTextColor(Color.parseColor("#000000"));//设置左y轴字的颜色
        axisLeft.setAxisLineColor(Color.BLACK);//y轴颜色
        axisLeft.setGridColor(Color.TRANSPARENT);//y轴线颜色

        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) {
                    return 0 + "元";
                }
                return super.getFormattedValue(value);
            }
        });
/*        xAxis.setLabelCount(6, true);
//设置是否绘制刻度
        xAxis.setDrawScale(true);
        axisLeft.setDrawScale(true);*/

//        xAxis.setLabelCount(6, true);
//设置是否绘制刻度
        xAxis.setLabelCount(10, true);
        xAxis.setDrawScale(true);
        axisLeft.setDrawScale(true);





        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(getActivity(), new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value);
            }
        });


        detailsMarkerView.setChartView(lineChart);
        lineChart.setMarker(detailsMarkerView);


    }

    @SuppressLint("ResourceType")
    private void initChart() {
        LineDataSet one = new LineDataSet(listOne, "One");//将数据赋值到你的线条上
        LineDataSet two = new LineDataSet(listTwo, "Two");//将数据赋值到你的线条上

        one.setCircleColor(Color.parseColor("#67BCFF"));//设置点的颜色
        one.setColor(Color.parseColor("#67BCFF"));//设置线的颜色
        one.setDrawValues(false);
        one.setDrawFilled(true); //设置折线图下方颜色填充默认是蓝色
//        setFormLineWidth
//        one.setFormLineWidth(30f);
//        one.setHighlightLineWidth(30f);
        // 填充背景只支持18以上
        Drawable drawableBlue = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
        one.setFillDrawable(drawableBlue);


        two.setDrawValues(false);
        two.setColor(Color.parseColor("#85C400FF"));
        two.setCircleColor(Color.parseColor("#85C400FF"));
        two.setDrawFilled(true);
        Drawable drawableViolet = ContextCompat.getDrawable(getActivity(), R.drawable.fade_violet);
        two.setFillDrawable(drawableViolet);

        LineData lineData = new LineData();// //线的总管理
        lineData.addDataSet(two);
        lineData.addDataSet(one);//每加一条就add一次
        lineChart.setData(lineData);//把线条设置给你的lineChart上
        lineChart.invalidate();//刷新
    }

    private void initData() {
        //循环添加设置x轴和y轴的点
     /*   for (int i = 0; i < 12; i++) {
            //Random().nextInt(300)) 是随机0到300之间的数但并不会取到300
            //new Random().nextInt(1000-800)+800+1)是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1
            listOne.add(new Entry(i,new Random().nextInt(300)));
            listTwo.add(new Entry(i,new Random().nextInt(500-300)+300+1));
        }*/
        for (int i = 0; i < 12; i++) {
            //Random().nextInt(300)) 是随机0到300之间的数但并不会取到300
            //new Random().nextInt(1000-800)+800+1)是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1
            listOne.add(new Entry(i, new Random().nextInt(300)));
            listTwo.add(new Entry(i, new Random().nextInt(500 - 300) + 300 + 1));
        }
    }

    private void initView() {


    }
}