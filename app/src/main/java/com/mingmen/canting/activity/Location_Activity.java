package com.mingmen.canting.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.mingmen.canting.R;
import com.mingmen.canting.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高精度定位模式功能演示
 *
 * @创建时间： 2015年11月24日 下午5:22:42
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: Hight_Accuracy_Activity.java
 * @类型名称: Hight_Accuracy_Activity
 */
public class Location_Activity extends CheckPermissionsActivity implements AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener{

	private MapView mapView = null;
	private AMap aMap;
	private UiSettings uiSettings;
	//存放所有点的经纬度
	LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
	//当前点击的marker
	private Marker clickMaker;
	//自定义窗体
	View infoWindow = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		setTitle("定位");

		mapView = findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapView.getMap();
			uiSettings = aMap.getUiSettings();
			//设置地图属性
			setMapAttribute();
		}

		//模拟数据源
		List<Map<String, String>> list = getData();

		//循坏在地图上添加自定义marker
		for (int i = 0; i < list.size(); i++) {
			LatLng latLng = new LatLng(Double.parseDouble(list.get(i).get("latitude")), Double.parseDouble(list.get(i).get("longitude")));
			MarkerOptions markerOption = new MarkerOptions();
			markerOption.position(latLng);
			markerOption.title(list.get(i).get("title"));
			markerOption.snippet(list.get(i).get("content"));
			//自定义点标记的icon图标为drawable文件下图片gps_point
			markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
//			markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gps_point)));
			markerOption.draggable(false);
			aMap.addMarker(markerOption);
			//将所有marker经纬度include到boundsBuilder中
			boundsBuilder.include(latLng);
		}
		//更新地图状态
		aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 10));
	}

	/**
	 * 设置地图属性
	 */
	private void setMapAttribute() {
		//设置默认缩放级别
		aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
		//隐藏的右下角缩放按钮
		uiSettings.setZoomControlsEnabled(false);
		//设置marker点击事件监听
		aMap.setOnMarkerClickListener(this);
		//设置自定义信息窗口
		aMap.setInfoWindowAdapter(this);
		//设置地图点击事件监听
		aMap.setOnMapClickListener(this);
	}

	/**
	 * 模拟数据源
	 */
	private List<Map<String, String>> getData() {
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("title", "标题NO." + i);
			map.put("content", "这是第" + i + "个marker的内容");
			map.put("latitude", 43 + Math.random() + "");
			map.put("longitude", 125 + Math.random() + "");
			list.add(map);
		}
		return list;
	}

	/**
	 * marker点击事件
	 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		clickMaker = marker;
		//点击当前marker展示自定义窗体
		marker.showInfoWindow();
		//返回true 表示接口已响应事,无需继续传递
		return true;
	}

	/**
	 * 监听自定义窗口infoWindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker) {
		if (infoWindow == null) {
			infoWindow = LayoutInflater.from(this).inflate(R.layout.amap_info_window, null);
		}
		render(marker, infoWindow);
		return infoWindow;
	}

	/**
	 * 自定义infoWindow窗口
	 */
	private void render(Marker marker, View infoWindow) {
		TextView title = infoWindow.findViewById(R.id.info_window_title);
		TextView content = infoWindow.findViewById(R.id.info_window_content);
		title.setText(marker.getTitle());
		content.setText(marker.getSnippet());
	}

	/**
	 * 不能修改整个InfoWindow的背景和边框，返回null
	 */
	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	/**
	 * 地图点击事件
	 * 点击地图区域让当前展示的窗体隐藏
	 */
	@Override
	public void onMapClick(LatLng latLng) {
		//判断当前marker信息窗口是否显示
		if (clickMaker != null && clickMaker.isInfoWindowShown()) {
			clickMaker.hideInfoWindow();
		}

	}
}
