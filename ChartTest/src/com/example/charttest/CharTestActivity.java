package com.example.charttest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import cn.limc.androidcharts.entity.LineEntity;
import cn.limc.androidcharts.entity.OHLCEntity;
import cn.limc.androidcharts.view.CandleStickChart.DataWatcher;
import cn.limc.androidcharts.view.MACandleStickChart;

/*
 * CharTestActivity.java
 * Android-Charts
 *
 * Created by limc on 2013.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author retryu
 * @version v1.0 2013-12-9 上午10:43:17
 * 
 */
public class CharTestActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @param savedInstanceState
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	MACandleStickChart macandlestickchart;
	List<OHLCEntity> ohlc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		initOHLC();
		initMACandleStickChart();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		macandlestickchart.pointLastIndex = macandlestickchart.getFirstIndex();
		Log.e("debug", "pointLast1:" + macandlestickchart.pointLastIndex
				+"offset   "+ macandlestickchart.xAxisOffset + "  width:"
				+ macandlestickchart.stickWidth);
		macandlestickchart.needResume = true;
		// macandlestickchart.xAxisOffset =
		// -macandlestickchart.getMaxAxiLeft(last);
	}

	private void initMACandleStickChart() {
		this.macandlestickchart = (MACandleStickChart) findViewById(R.id.macandlestickchart);
		List<LineEntity> lines = new ArrayList<LineEntity>();

		// 计算5日均线
		LineEntity MA5 = new LineEntity();
		MA5.setTitle("MA5");
		MA5.setLineColor(Color.WHITE);
		MA5.setLineData(initMA(5));
		lines.add(MA5);

		// 计算10日均线
		LineEntity MA10 = new LineEntity();
		MA10.setTitle("MA10");
		MA10.setLineColor(Color.RED);
		MA10.setLineData(initMA(10));
		lines.add(MA10);

		// 计算25日均线
		LineEntity MA25 = new LineEntity();
		MA25.setTitle("MA25");
		MA25.setLineColor(Color.GREEN);
		MA25.setLineData(initMA(25));
		lines.add(MA25);

		macandlestickchart.setAxisXColor(Color.LTGRAY);
		macandlestickchart.setAxisYColor(Color.LTGRAY);
		macandlestickchart.setLatitudeColor(Color.GRAY);
		macandlestickchart.setLongitudeColor(Color.GRAY);
		macandlestickchart.setBorderColor(Color.LTGRAY);
		macandlestickchart.setLongitudeFontColor(Color.WHITE);
		macandlestickchart.setLatitudeFontColor(Color.WHITE);
		macandlestickchart.setAxisMarginRight(1);

		// 最大显示足数	
		macandlestickchart.setMaxSticksNum(52);
		// 最大纬线数
		macandlestickchart.setLatitudeNum(4);
		// 最大经线数
		macandlestickchart.setLongitudeNum(3);
		// 最大价格
		// macandlestickchart.setMaxValue(1000);
		// 最小价格
		macandlestickchart.setMinValue(100);

		macandlestickchart.setDisplayAxisXTitle(true);
		macandlestickchart.setDisplayAxisYTitle(true);
		macandlestickchart.setDisplayLatitude(true);
		macandlestickchart.setDisplayLongitude(true);
		macandlestickchart.setLatitudeFontSize(15);
		macandlestickchart.setBackgroundColor(Color.BLACK);

		// 为chart2增加均线
		macandlestickchart.setLineData(lines);
		macandlestickchart.setNeedXColor(true);
		// 为chart2增加均线
		macandlestickchart.setOHLCData(ohlc);
		macandlestickchart.setDataWatcher(new DataWatcher() {
			@Override
			public void onDatachanege() {
				macandlestickchart.notifyDatachange(null);
			}
		});
		macandlestickchart.resetMax();
	}

	private List<Float> initMA(int days) {

		if (days < 2) {
			return null;
		}

		List<Float> MA5Values = new ArrayList<Float>();

		float sum = 0;
		float avg = 0;
		for (int i = 0; i < this.ohlc.size(); i++) {
			float close = (float) ohlc.get(i).getClose();
			if (i < days) {
				sum = sum + close;
				avg = sum / (i + 1f);
			} else {
				sum = sum + close - (float) ohlc.get(i - days).getClose();
				avg = sum / days;
			}
			MA5Values.add(avg);
		}

		return MA5Values;
	}

	// public void add(List<OHLCEntity> data) {
	// data = new ArrayList<OHLCEntity>();
	// data.add(new OHLCEntity(220, 230, 220, 230, 20110503));
	// OHLCData.addAll(0, data);
	// xAxisOffset = 0;
	// List<LineEntity> lines = new ArrayList<LineEntity>();
	// // 计算5日均线
	// LineEntity MA5 = new LineEntity();
	// MA5.setTitle("MA5");
	// MA5.setLineColor(Color.WHITE);
	// MA5.setLineData(Util.initMA(5, this.ohlc));
	// lines.add(MA5);
	//
	// // 计算10日均线
	// LineEntity MA10 = new LineEntity();
	// MA10.setTitle("MA10");
	// MA10.setLineColor(Color.RED);
	// MA10.setLineData(Util.initMA(10, OHLCData));
	// lines.add(MA10);
	//
	// // 计算25日均线
	// LineEntity MA25 = new LineEntity();
	// MA25.setTitle("MA25");
	// MA25.setLineColor(Color.GREEN);
	// MA25.setLineData(Util.initMA(25, OHLCData));
	// lines.add(MA25);
	//
	// Log.e("debug", "list:" + data);
	// }

	private void initOHLC() {
		List<OHLCEntity> ohlc = new ArrayList<OHLCEntity>();

		this.ohlc = new ArrayList<OHLCEntity>();

		ohlc.add(new OHLCEntity(265, 268, 265, 267, 20110829));
		ohlc.add(new OHLCEntity(271, 271, 266, 266, 20110828));
		ohlc.add(new OHLCEntity(250, 260, 250, 260, 20110827));
		ohlc.add(new OHLCEntity(220, 240, 220, 240, 20110826));
		ohlc.add(new OHLCEntity(246, 248, 235, 235, 20110825));
		ohlc.add(new OHLCEntity(240, 242, 236, 242, 20110824));
		ohlc.add(new OHLCEntity(236, 240, 235, 240, 20110823));
		ohlc.add(new OHLCEntity(232, 236, 231, 236, 20110822));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110821));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110820));
		ohlc.add(new OHLCEntity(240, 240, 235, 235, 20110819));
		ohlc.add(new OHLCEntity(240, 241, 239, 240, 20110818));
		ohlc.add(new OHLCEntity(242, 243, 240, 240, 20110817));
		ohlc.add(new OHLCEntity(239, 242, 238, 242, 20110816));
		ohlc.add(new OHLCEntity(239, 240, 238, 239, 20110815));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110814));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110813));
		ohlc.add(new OHLCEntity(230, 238, 230, 238, 20110812));
		ohlc.add(new OHLCEntity(236, 237, 234, 234, 20110811));
		ohlc.add(new OHLCEntity(226, 233, 223, 232, 20110810));
		ohlc.add(new OHLCEntity(239, 241, 229, 232, 20110809));
		ohlc.add(new OHLCEntity(242, 244, 240, 242, 20110808));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110807));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110806));
		ohlc.add(new OHLCEntity(248, 249, 247, 248, 20110805));
		ohlc.add(new OHLCEntity(245, 248, 245, 247, 20110804));
		ohlc.add(new OHLCEntity(249, 249, 245, 247, 20110803));
		ohlc.add(new OHLCEntity(249, 251, 248, 250, 20110802));
		ohlc.add(new OHLCEntity(250, 252, 248, 250, 20110801));
		ohlc.add(new OHLCEntity(250, 251, 248, 250, 20110729));
		ohlc.add(new OHLCEntity(249, 252, 248, 252, 20110728));
		ohlc.add(new OHLCEntity(248, 250, 247, 250, 20110727));
		ohlc.add(new OHLCEntity(256, 256, 248, 248, 20110726));
		ohlc.add(new OHLCEntity(257, 258, 256, 257, 20110725));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110724));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110723));
		ohlc.add(new OHLCEntity(259, 260, 256, 256, 20110722));
		ohlc.add(new OHLCEntity(261, 261, 257, 259, 20110721));
		ohlc.add(new OHLCEntity(260, 260, 259, 259, 20110720));
		ohlc.add(new OHLCEntity(262, 262, 260, 261, 20110719));
		ohlc.add(new OHLCEntity(260, 262, 259, 262, 20110718));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110717));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110716));
		ohlc.add(new OHLCEntity(259, 261, 258, 261, 20110715));
		ohlc.add(new OHLCEntity(255, 259, 255, 259, 20110714));
		ohlc.add(new OHLCEntity(258, 258, 255, 255, 20110713));
		ohlc.add(new OHLCEntity(258, 260, 258, 260, 20110712));
		ohlc.add(new OHLCEntity(259, 260, 258, 259, 20110711));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110710));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110709));
		ohlc.add(new OHLCEntity(261, 262, 259, 259, 20110708));
		ohlc.add(new OHLCEntity(261, 261, 258, 261, 20110707));
		ohlc.add(new OHLCEntity(261, 261, 259, 261, 20110706));
		ohlc.add(new OHLCEntity(257, 261, 257, 261, 20110705));
		ohlc.add(new OHLCEntity(256, 257, 255, 255, 20110704));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110703));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110702));
		ohlc.add(new OHLCEntity(253, 257, 253, 256, 20110701));
		ohlc.add(new OHLCEntity(255, 255, 252, 252, 20110630));
		ohlc.add(new OHLCEntity(256, 256, 253, 255, 20110629));
		ohlc.add(new OHLCEntity(254, 256, 254, 255, 20110628));
		ohlc.add(new OHLCEntity(247, 256, 247, 254, 20110627));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110626));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110625));
		ohlc.add(new OHLCEntity(244, 249, 243, 248, 20110624));
		ohlc.add(new OHLCEntity(244, 245, 243, 244, 20110623));
		ohlc.add(new OHLCEntity(242, 244, 241, 244, 20110622));
		ohlc.add(new OHLCEntity(243, 243, 241, 242, 20110621));
		ohlc.add(new OHLCEntity(246, 247, 244, 244, 20110620));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110619));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110618));
		ohlc.add(new OHLCEntity(248, 249, 246, 246, 20110617));
		ohlc.add(new OHLCEntity(251, 253, 250, 250, 20110616));
		ohlc.add(new OHLCEntity(249, 253, 249, 253, 20110615));
		ohlc.add(new OHLCEntity(248, 250, 246, 250, 20110614));
		ohlc.add(new OHLCEntity(249, 250, 247, 250, 20110613));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110612));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110611));
		ohlc.add(new OHLCEntity(254, 254, 250, 250, 20110610));
		ohlc.add(new OHLCEntity(254, 255, 251, 255, 20110609));
		ohlc.add(new OHLCEntity(252, 254, 251, 254, 20110608));
		ohlc.add(new OHLCEntity(250, 253, 250, 252, 20110607));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110606));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110605));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110604));
		ohlc.add(new OHLCEntity(251, 252, 247, 250, 20110603));
		ohlc.add(new OHLCEntity(253, 254, 252, 254, 20110602));
		ohlc.add(new OHLCEntity(250, 254, 250, 254, 20110601)); 
		ohlc.add(new OHLCEntity(250, 252, 248, 250, 20110531));
		ohlc.add(new OHLCEntity(253, 254, 250, 251, 20110530));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110529));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110528));
		ohlc.add(new OHLCEntity(255, 256, 253, 253, 20110527));
		ohlc.add(new OHLCEntity(256, 257, 253, 254, 20110526));
		ohlc.add(new OHLCEntity(256, 257, 254, 256, 20110525));
		ohlc.add(new OHLCEntity(265, 265, 257, 257, 20110524));
		ohlc.add(new OHLCEntity(265, 266, 265, 265, 20110523));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110522));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110521));
		ohlc.add(new OHLCEntity(267, 268, 265, 266, 20110520));
		ohlc.add(new OHLCEntity(264, 267, 264, 267, 20110519));
		ohlc.add(new OHLCEntity(270, 290, 270, 290, 20110518));
		ohlc.add(new OHLCEntity(266, 267, 264, 264, 20110517));
		ohlc.add(new OHLCEntity(264, 267, 263, 267, 20110516));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110515));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110514));
		ohlc.add(new OHLCEntity(266, 267, 264, 264, 20110513));
		ohlc.add(new OHLCEntity(269, 269, 266, 268, 20110512));
		ohlc.add(new OHLCEntity(267, 269, 266, 269, 20110511));
		ohlc.add(new OHLCEntity(266, 268, 266, 267, 20110510));
		ohlc.add(new OHLCEntity(264, 268, 263, 266, 20110509));
		ohlc.add(new OHLCEntity(265, 268, 265, 267, 20110508));
		ohlc.add(new OHLCEntity(265, 268, 265, 267, 20110507));
		ohlc.add(new OHLCEntity(265, 268, 265, 267, 20110506));
		ohlc.add(new OHLCEntity(271, 271, 266, 266, 20110505));
		ohlc.add(new OHLCEntity(250, 260, 250, 260, 20110504));
		ohlc.add(new OHLCEntity(220, 240, 220, 240, 20110503));
		// ohlc.add(new OHLCEntity(220, 240, 220, 240, 20110502));
		// ohlc.add(new OHLCEntity(220, 240, 220, 240, 20110501));

		// ohlc.add(new OHLCEntity(268, 271, 267, 271, 20110503));
		// ohlc.add(new OHLCEntity(273 ,275 ,268 ,268 ,20110429));
		// ohlc.add(new OHLCEntity(274 ,276 ,270 ,272 ,20110428));
		// ohlc.add(new OHLCEntity(275 ,277 ,273 ,273 ,20110427));
		// ohlc.add(new OHLCEntity(280 ,280 ,276 ,276 ,20110426));
		// ohlc.add(new OHLCEntity(282 ,283 ,280 ,281 ,20110425));
		// ohlc.add(new OHLCEntity(282 ,283 ,281 ,282 ,20110422));
		// ohlc.add(new OHLCEntity(280 ,281 ,279 ,280 ,20110421));
		// ohlc.add(new OHLCEntity(283 ,283 ,279 ,279 ,20110420));
		// ohlc.add(new OHLCEntity(284 ,286 ,283 ,285 ,20110419));
		// ohlc.add(new OHLCEntity(283 ,286 ,282 ,285 ,20110418));
		// ohlc.add(new OHLCEntity(285 ,285 ,283 ,284 ,20110415));
		// ohlc.add(new OHLCEntity(280 ,285 ,279 ,285 ,20110414));
		// ohlc.add(new OHLCEntity(281 ,283 ,280 ,282 ,20110413));
		// ohlc.add(new OHLCEntity(283 ,286 ,282 ,282 ,20110412));
		// ohlc.add(new OHLCEntity(280 ,283 ,279 ,283 ,20110411));
		// ohlc.add(new OHLCEntity(280 ,281 ,279 ,280 ,20110408));
		// ohlc.add(new OHLCEntity(276 ,280 ,276 ,280 ,20110407));
		// ohlc.add(new OHLCEntity(273 ,276 ,272 ,276 ,20110406));
		// ohlc.add(new OHLCEntity(275 ,276 ,271 ,272 ,20110404));
		// ohlc.add(new OHLCEntity(275 ,276 ,273 ,275 ,20110401));
		for (int i = ohlc.size(); i > 0; i--) {
			this.ohlc.add(ohlc.get(i - 1));
		}
	}
}
