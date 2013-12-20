/*
 * MACandleStickChart.java
 * Android-Charts
 *
 * Created by limc on 2011/05/29.
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

package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.List;

import cn.limc.androidcharts.entity.LineEntity;
import cn.limc.androidcharts.entity.OHLCEntity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

/**
 * 
 * <p>
 * MACandleStickChart is inherits from CandleStickChart which can display moving
 * average lines on this graph.
 * </p>
 * <p>
 * MACandleStickChartはグラフの一種です、移動平均線など分析線がこのグラフで表示は可能です。
 * </p>
 * <p>
 * MACandleStickChart继承于CandleStickChart的，可以在CandleStickChart基础上
 * 显示移动平均等各种分析指标数据。
 * </p>
 * 
 * @author limc
 * @version v1.0 2011/05/30 14:49:02
 * @see CandleStickChart
 * @see StickChart
 * 
 */
public class MACandleStickChart extends CandleStickChart {

	/**
	 * <p>
	 * data to draw lines
	 * </p>
	 * <p>
	 * ラインを書く用データ
	 * </p>
	 * <p>
	 * 绘制线条用的数据
	 * </p>
	 */
	private List<LineEntity> lineData;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context)
	 */
	public MACandleStickChart(Context context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @param defStyle
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context,
	 * AttributeSet, int)
	 */
	public MACandleStickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * 
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context,
	 * AttributeSet)
	 */
	public MACandleStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
	 * <p>绘制图表时调用<p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		// draw lines
		if (null != this.lineData) {
			if (0 != this.lineData.size()) {
				drawLines(canvas);
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * <p>
	 * draw lines
	 * </p>
	 * <p>
	 * ラインを書く
	 * </p>
	 * <p>
	 * 绘制线条
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLines(Canvas canvas) {
		// distance between two points
		// float lineLength = ((super.getWidth() - super.getAxisMarginLeft() -
		// super
		// .getAxisMarginRight()) / super.getMaxSticksNum()) - 1;
		// start point‘s X
		float lineLength = stickWidth;
		float startX;

		// draw MA lines
		for (int i = 0; i < lineData.size(); i++) {
			LineEntity line = (LineEntity) lineData.get(i);
			if (line.isDisplay()) {
				Paint mPaint = new Paint();
				mPaint.setColor(line.getLineColor());
				mPaint.setAntiAlias(true);
				List<Float> lineData = line.getLineData();
				// set start point’s X
				startX = super.getAxisMarginLeft() + lineLength / 2f;
				// start point
				PointF ptFirst = null;
				if (lineData != null) {
					for (int j = 0; j < lineData.size(); j++) {
						float value = lineData.get(j).floatValue();
						// calculate Y
						float valueY = (float) ((1f - (value - super
								.getMinValue())
								/ (super.getMaxValue() - super.getMinValue())) * (super
								.getHeight() - super.getAxisMarginBottom()));

						// if is not last point connect to previous point
						if (j > 0) {
							canvas.drawLine(xAxisOffset + ptFirst.x, ptFirst.y,
									xAxisOffset + startX, valueY, mPaint);
						}
						// reset
						ptFirst = new PointF(startX, valueY);
						startX = startX + 1 + lineLength;
					}
				}
			}
		}
	}

	/**
	 * @return the lineData
	 */
	public List<LineEntity> getLineData() {
		return lineData;
	}

	/**
	 * @param lineData
	 *            the lineData to set
	 */
	public void setLineData(List<LineEntity> lineData) {
		this.lineData = lineData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param data
	 * 
	 * @see
	 * cn.limc.androidcharts.view.CandleStickChart#notifyDatachange(java.util
	 * .List)
	 */
	@Override
	public void notifyDatachange(List<OHLCEntity> data) {
		// TODO Auto-generated method stub
		xAxisOffset = 0;
		for (int i = 0; i < 10; i++) {
			double ran = Math.random();
			int max = (int) (ran * (50)) + 150;
			int min = (int) (ran * (150))+100;
			data = new ArrayList<OHLCEntity>();
			data.add(new OHLCEntity(min, max, min, max, 20110503));
			getOHLCData().addAll(0, data);
		}
		List<LineEntity> lines = new ArrayList<LineEntity>();
		// 计算5日均线
		LineEntity MA5 = new LineEntity();
		MA5.setTitle("MA5");
		MA5.setLineColor(Color.WHITE);
		MA5.setLineData(Util.initMA(5, getOHLCData()));
		lines.add(MA5);

		// 计算10日均线
		LineEntity MA10 = new LineEntity();
		MA10.setTitle("MA10");
		MA10.setLineColor(Color.RED);
		MA10.setLineData(Util.initMA(10, getOHLCData()));
		lines.add(MA10);

		// 计算25日均线
		LineEntity MA25 = new LineEntity();
		MA25.setTitle("MA25");
		MA25.setLineColor(Color.GREEN);
		MA25.setLineData(Util.initMA(25, getOHLCData()));
		lines.add(MA25);
		setLineData(lines);
		postInvalidate();
	}
}
