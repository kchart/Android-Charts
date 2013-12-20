/*
 * CandleStickChart.java
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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import cn.limc.androidcharts.R;
import cn.limc.androidcharts.entity.OHLCEntity;

/**
 * 
 * <p>
 * CandleStickChart is a kind of graph that draw the OHLCs on a GridChart if you
 * want display some moving average lines on this graph, please use see
 * MACandleStickChart for more information
 * </p>
 * <p>
 * CandleStickChartはGridChartの表面でロウソクを書いたラインチャードです。移動平均線など
 * 分析線がお使いしたい場合、MACandleStickChartにご参照ください。
 * </p>
 * <p>
 * CandleStickChart是在GridChart上绘制K线（蜡烛线）的图表、如果需要支持显示均线，请参照 MACandleStickChart。
 * </p>
 * 
 * @author limc
 * @version v1.0 2011/05/30 16:29:41
 * @see CandleStickChart
 * @see MACandleStickChart
 * 
 */
public class CandleStickChart extends GridChart {
	/**
	 * <p>
	 * Default price up stick's border color
	 * </p>
	 * <p>
	 * 値上がりローソクのボーダー色のデフォルト値
	 * </p>
	 * <p>
	 * 默认阳线的边框颜色
	 * </p>
	 */
	public static final int DEFAULT_POSITIVE_STICK_BORDER_COLOR = Color.RED;

	/**
	 * <p>
	 * Default price up stick's fill color
	 * </p>
	 * <p>
	 * 値上がりローソクの色のデフォルト値
	 * </p>
	 * <p>
	 * 默认阳线的填充颜色
	 * </p>
	 */
	public static final int DEFAULT_POSITIVE_STICK_FILL_COLOR = Color.RED;

	/**
	 * <p>
	 * Default price down stick's border color
	 * </p>
	 * <p>
	 * 値下りローソクのボーダー色のデフォルト値
	 * </p>
	 * <p>
	 * 默认阴线的边框颜色
	 * </p>
	 */
	public static final int DEFAULT_NEGATIVE_STICK_BORDER_COLOR = Color.GREEN;

	/**
	 * <p>
	 * Default price down stick's fill color
	 * </p>
	 * <p>
	 * 値下りローソクの色のデフォルト値
	 * </p>
	 * <p>
	 * 默认阴线的填充颜色
	 * </p>
	 */
	public static final int DEFAULT_NEGATIVE_STICK_FILL_COLOR = Color.GREEN;

	/**
	 * <p>
	 * Default price no change stick's color (cross-star,T-like etc.)
	 * </p>
	 * <p>
	 * クローススターの色のデフォルト値
	 * </p>
	 * <p>
	 * 默认十字线显示颜色
	 * </p>
	 */
	public static final int DEFAULT_CROSS_STAR_COLOR = Color.LTGRAY;

	/**
	 * <p>
	 * Price up stick's border color
	 * </p>
	 * <p>
	 * 値上がりローソクのボーダー色
	 * </p>
	 * <p>
	 * 阳线的边框颜色
	 * </p>
	 */
	private int positiveStickBorderColor = DEFAULT_POSITIVE_STICK_BORDER_COLOR;

	/**
	 * <p>
	 * Price up stick's fill color
	 * </p>
	 * <p>
	 * 値上がりローソクの色
	 * </p>
	 * <p>
	 * 阳线的填充颜色
	 * </p>
	 */
	private int positiveStickFillColor = DEFAULT_POSITIVE_STICK_FILL_COLOR;

	/**
	 * <p>
	 * Price down stick's border color
	 * </p>
	 * <p>
	 * 値下りローソクのボーダー色
	 * </p>
	 * <p>
	 * 阴线的边框颜色
	 * </p>
	 */

	private int negativeStickBorderColor = DEFAULT_NEGATIVE_STICK_BORDER_COLOR;

	/**
	 * <p>
	 * Price down stick's fill color
	 * </p>
	 * <p>
	 * 値下りローソクの色
	 * </p>
	 * <p>
	 * 阴线的填充颜色
	 * </p>
	 */
	private int negativeStickFillColor = DEFAULT_NEGATIVE_STICK_FILL_COLOR;

	/**
	 * <p>
	 * Price no change stick's color (cross-star,T-like etc.)
	 * </p>
	 * <p>
	 * クローススターの色（価格変動無し）
	 * </p>
	 * <p>
	 * 十字线显示颜色（十字星,一字平线,T形线的情况）
	 * </p>
	 */
	private int crossStarColor = DEFAULT_CROSS_STAR_COLOR;

	/**
	 * <p>
	 * data to draw sticks
	 * </p>
	 * <p>
	 * スティックを書く用データ
	 * </p>
	 * <p>
	 * 绘制柱条用的数据
	 * </p>
	 */
	private List<OHLCEntity> OHLCData;

	/**
	 * <p>
	 * max number of sticks
	 * </p>
	 * <p>
	 * スティックの最大表示数
	 * </p>
	 * <p>
	 * 柱条的最大表示数
	 * </p>
	 */
	private int maxSticksNum;

	/**
	 * <p>
	 * max value of Y axis
	 * </p>
	 * <p>
	 * Y軸の最大値
	 * </p>
	 * <p>
	 * Y的最大表示值
	 * </p>
	 */
	private float maxValue = 0;

	/**
	 * <p>
	 * min value of Y axis
	 * </p>
	 * <p>
	 * Y軸の最小値
	 * </p>
	 * <p>
	 * Y的最小表示值
	 * </p>
	 */
	private float minValue = 0;

	private float panelWidth = 150;

	private float panelHeight = 300;

	private float rectLeft = getAxisMarginLeft() + 5;

	private float rectTop = getAxisMarginTop() + 10;

	private int panel_alpha = 180;

	private int panel_bgk_color = Color.CYAN;

	private int panel_bound_color = Color.LTGRAY;

	private int panel_text_color = Color.BLACK;

	private float textSize = 20;

	float stickWidth=10;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context)
	 */
	public CandleStickChart(Context context) {
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
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet, int)
	 */
	public CandleStickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet)
	 */
	public CandleStickChart(Context context, AttributeSet attrs) {
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

		drawCandleSticks(canvas);
		initAxisY();
		initAxisX();
		super.onDraw(canvas);
		setDisplayCrossXOnTouch(false);
		setDisplayCrossYOnTouch(false);
		drawInforPanel(canvas);

		// }
	}


	/**
	 * 画出k线信息面板展示k线信息
	 * 
	 * @param canvas
	 */
	protected void drawInforPanel(Canvas canvas) {
		if (getTouchPoint() == null || OHLCData == null || OHLCData.size() <= getSelectedIndex()) {
			return;
		}
		if (panelWidth > getWidth() / 2) {
			panelWidth = getWidth() / 2;
		}
		if (panelHeight > getHeight() - 20) {
			panelHeight = getHeight() - 20;
		}
		OHLCEntity ohlc = OHLCData.get(getSelectedIndex());
		// float density = getResources().getDisplayMetrics().density;
		// float scaleDensity =
		// getResources().getDisplayMetrics().scaledDensity;
		if (getTouchPoint().x <= getAxisMarginLeft() + panelWidth) {
			rectLeft = getWidth() - getAxisMarginRight() - panelWidth - 5;
		}

		if (getTouchPoint().x >= getWidth() - getAxisMarginRight() - panelWidth) {
			rectLeft = getAxisMarginLeft() + 5;
		}
		Paint rectBKG = new Paint();
		rectBKG.setColor(panel_bgk_color);
		rectBKG.setAlpha(panel_alpha);
		canvas.drawRect(rectLeft, rectTop, rectLeft + panelWidth, rectTop + panelHeight, rectBKG);

		Paint rectBound = new Paint();
		rectBound.setColor(panel_bound_color);
		canvas.drawLine(rectLeft, rectTop, rectLeft + panelWidth, rectTop, rectBound);
		canvas.drawLine(rectLeft, rectTop, rectLeft, rectTop + panelHeight, rectBound);
		canvas.drawLine(rectLeft + panelWidth, rectTop, rectLeft + panelWidth, rectTop + panelHeight, rectBound);
		canvas.drawLine(rectLeft, rectTop + panelHeight, rectLeft + panelWidth, rectTop + panelHeight, rectBound);

		Paint textPaint = new Paint(Paint.LINEAR_TEXT_FLAG);

		float lineWidth = 20;
		float tempMarginTop = rectTop + lineWidth * 2;
		textPaint.setColor(panel_text_color);
		textPaint.setTextSize(textSize);
		String tempText;
		//
		tempText = "" + ohlc.getDate();
		canvas.drawText(tempText, rectLeft + (panelWidth - textPaint.measureText(tempText)) / 2, tempMarginTop,
				textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.open_price), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int)(ohlc.getOpen() * 100)/100.0);
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.high_price), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int)(ohlc.getHigh() * 100)/100.0);
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.low_price), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int)(ohlc.getLow() * 100)/100.0);
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.close_price), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int)(ohlc.getClose() * 100)/100.0);
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.cliff_price), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int)((ohlc.getClose() - ohlc.getOpen()) * 100)/100.0);
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.cliff_ratio), rectLeft + 5, tempMarginTop, textPaint);
		tempText = String.valueOf((int) ((ohlc.getClose() - ohlc.getOpen()) / ohlc.getOpen() * 10000) / 100.0) + "%";
		canvas.drawText(tempText, rectLeft + panelWidth - textPaint.measureText(tempText), tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.amount), rectLeft + 5, tempMarginTop, textPaint);
		//
		tempMarginTop += lineWidth;
		canvas.drawText(getResources().getString(R.string.exchange), rectLeft + 5, tempMarginTop, textPaint);

	}

	public int getPanel_alpha() {
		return panel_alpha;
	}

	public void setPanel_alpha(int panel_alpha) {
		this.panel_alpha = panel_alpha;
	}

	public int getPanel_bgk_color() {
		return panel_bgk_color;
	}

	public void setPanel_bgk_color(int panel_bgk_color) {
		this.panel_bgk_color = panel_bgk_color;
	}

	public int getPanel_bound_color() {
		return panel_bound_color;
	}

	public void setPanel_bound_color(int panel_bound_color) {
		this.panel_bound_color = panel_bound_color;
	}

	public int getPanel_text_color() {
		return panel_text_color;
	}

	public void setPanel_text_color(int panel_text_color) {
		this.panel_text_color = panel_text_color;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	protected void drawWithFingerClick(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setColor(Color.CYAN);

		float lineHLength = getWidth() - 2f;
		float lineVLength = getHeight() - 2f;

		// draw text
		if (isDisplayAxisXTitle()) {
			lineVLength = lineVLength - getAxisMarginBottom();

			if (getClickPostX() > 0 && getClickPostY() > 0) {
				// TODO calculate points to draw
				PointF BoxVS = new PointF(getClickPostX() - getLatitudeFontSize() * 5f / 2f, lineVLength + 2f);
				PointF BoxVE = new PointF(getClickPostX() + getLatitudeFontSize() * 5f / 2f, lineVLength
						+ getAxisMarginBottom() - 1f);

				// draw text
				drawAlphaTextBox(BoxVS, BoxVE, getAxisXGraduate(getClickPostX()), getLatitudeFontSize(), canvas);
			}
		}

		if (isDisplayAxisYTitle()) {
			lineHLength = lineHLength - getAxisMarginLeft();

			if (getClickPostX() > 0 && getClickPostY() > 0) {
				// TODO calculate points to draw
				PointF BoxHS = new PointF(1f, getClickPostY() - getLatitudeFontSize() / 2f);
				PointF BoxHE = new PointF(getAxisMarginLeft(), getClickPostY() + getLatitudeFontSize() / 2f);

				// draw text
				drawAlphaTextBox(BoxHS, BoxHE, getAxisYGraduate(getClickPostY()), getLatitudeFontSize(), canvas);
			}
		}

		// draw line
		if (getClickPostX() > 0 && getClickPostY() > 0) {
			int count = (int) ((getClickPostX() - getAxisMarginLeft()) / stickWidth);
			int diff = (int) ((getClickPostX() - getAxisMarginLeft() - count) / stickWidth);
			float posX = getClickPostX() - getAxisMarginLeft();
			diff = diff;
			float mid = (1 + stickWidth) * (diff) + (1 + stickWidth) + getAxisMarginLeft();
			Log.e("debug", "diff:" + diff + "   click: " + mid + "   " + (getClickPostX() - getAxisMarginLeft()) + " "
					+ stickWidth);
			posX = mid - stickWidth / 2;

			canvas.drawLine(posX, 1f, posX, lineVLength, mPaint);

			// canvas.drawLine(getClickPostX(), 1f, getClickPostX(),
			// lineVLength,
			// mPaint);

			canvas.drawLine(getAxisMarginLeft(), getClickPostY(), getAxisMarginLeft() + lineHLength, getClickPostY(),
					mPaint);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param value
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#getAxisXGraduate(Object)
	 */
	@Override
	public String getAxisXGraduate(Object value) {
		float graduate = Float.valueOf(super.getAxisXGraduate(value));
		int index = (int) Math.floor(graduate * maxSticksNum);

		if (index >= maxSticksNum) {
			index = maxSticksNum - 1;
		}
		else if (index < 0) {
			index = 0;
		}

		return String.valueOf(OHLCData.get(index).getDate());
	}

	/**
	 * <p>
	 * get current selected data index
	 * </p>
	 * <p>
	 * 選択したスティックのインデックス
	 * </p>
	 * <p>
	 * 获取当前选中的柱条的index
	 * </p>
	 * 
	 * @return int
	 *         <p>
	 *         index
	 *         </p>
	 *         <p>
	 *         インデックス
	 *         </p>
	 *         <p>
	 *         index
	 *         </p>
	 */
	public int getSelectedIndex() {
		if (null == super.getTouchPoint()) {
			return 0;
		}
		float graduate = Float.valueOf(super.getAxisXGraduate(super.getTouchPoint().x));
		int index = (int) Math.floor(graduate * maxSticksNum);

		if (index >= maxSticksNum) {
			index = maxSticksNum - 1;
		}
		else if (index < 0) {
			index = 0;
		}

		return index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param value
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#getAxisYGraduate(Object)
	 */
	@Override
	public String getAxisYGraduate(Object value) {
		float graduate = Float.valueOf(super.getAxisYGraduate(value));
		return String.valueOf((int) Math.floor(graduate * (maxValue - minValue) + minValue));
	}

	/**
	 * <p>
	 * initialize degrees on Y axis
	 * </p>
	 * <p>
	 * Y軸の目盛を初期化
	 * </p>
	 * <p>
	 * 初始化Y轴的坐标值
	 * </p>
	 */
	protected void initAxisX() {
		List<String> TitleX = new ArrayList<String>();
		if (null != OHLCData) {
			float average = getMaxSticksNum() / this.getLongitudeNum();
			// �?��刻度
			for (int i = 0; i <= this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average) - getFirstIndex();
				if (index > maxSticksNum - 1) {
					index = maxSticksNum - 1;
				}
				Log.e("debug", "index:" + index + " average:" + average);
				// 追�??�?
				if (index < OHLCData.size() && index >= 0) {
					TitleX.add(String.valueOf(OHLCData.get(index).getDate()).substring(4));
				}
			}
			// TitleX.add(String.valueOf(OHLCData.get(maxSticksNum -
			// 1).getDate())
			// .substring(4));
		}
		super.setAxisXTitles(TitleX);
	}

	/**
	 * <p>
	 * initialize degrees on Y axis
	 * </p>
	 * <p>
	 * Y軸の目盛を初期化
	 * </p>
	 * <p>
	 * 初始化Y轴的坐标值
	 * </p>
	 */
	protected void initAxisY() {
		List<String> TitleY = new ArrayList<String>();
		float average = (int) ((maxValue - minValue) / this.getLatitudeNum()) / 10 * 10;
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = String.valueOf((int) Math.floor(minValue + i * average));
			if (value.length() < super.getAxisYMaxTitleLength()) {
				while (value.length() < super.getAxisYMaxTitleLength()) {
					value = new String(" ") + value;
				}
			}
			TitleY.add(value);
		}
		// calculate last degrees by use max value
		String value = String.valueOf((int) Math.floor(((int) maxValue) / 10 * 10));
		if (value.length() < super.getAxisYMaxTitleLength()) {
			while (value.length() < super.getAxisYMaxTitleLength()) {
				value = new String(" ") + value;
			}
		}
		TitleY.add(value);

		super.setAxisYTitles(TitleY);
	}

	public int getFirstIndex() {
		int index = (int) (xAxisOffset / (stickWidth + 1));
		return index;
	}

	public int getLastIndex() {
		int index = (int) (getFirstIndex() + (super.getWidth() - getAxisMarginLeft()) / (stickWidth + 1));
		return index;
	}

	/**
	 * <p>
	 * draw sticks
	 * </p>
	 * <p>
	 * スティックを書く
	 * </p>
	 * <p>
	 * 绘制柱条
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawCandleSticks(Canvas canvas) {

		// float stickWidth = ((super.getWidth() - super.getAxisMarginLeft() -
		// super
		// .getAxisMarginRight()) / maxSticksNum) - 1;
		stickWidth = 30;
		float stickX = super.getAxisMarginLeft() + 1;

		Paint mPaintPositive = new Paint();
		mPaintPositive.setColor(positiveStickFillColor);
		Paint mPaintNegative = new Paint();
		mPaintNegative.setColor(negativeStickFillColor);

		Paint mPaintCross = new Paint();
		mPaintCross.setColor(crossStarColor);

		if (null != OHLCData) {
			for (int i = 0; i < OHLCData.size(); i++) {
				OHLCEntity ohlc = OHLCData.get(i);
				float openY = (float) ((1f - (ohlc.getOpen() - minValue) / (maxValue - minValue))
						* (super.getHeight() - super.getAxisMarginBottom()) - super.getAxisMarginTop());
				float highY = (float) ((1f - (ohlc.getHigh() - minValue) / (maxValue - minValue))
						* (super.getHeight() - super.getAxisMarginBottom()) - super.getAxisMarginTop());
				float lowY = (float) ((1f - (ohlc.getLow() - minValue) / (maxValue - minValue))
						* (super.getHeight() - super.getAxisMarginBottom()) - super.getAxisMarginTop());
				float closeY = (float) ((1f - (ohlc.getClose() - minValue) / (maxValue - minValue))
						* (super.getHeight() - super.getAxisMarginBottom()) - super.getAxisMarginTop());

				float left = xAxisOffset + stickX;
				if (ohlc.getOpen() < ohlc.getClose()) {
					// stick or line
					if (stickWidth >= 2f) {
						canvas.drawRect(left, closeY, xAxisOffset + stickX + stickWidth, openY, mPaintPositive);
					}
					canvas.drawLine(left + stickWidth / 2f, highY, xAxisOffset + stickX + stickWidth / 2f, lowY,
							mPaintPositive);
				}
				else if (ohlc.getOpen() > ohlc.getClose()) {
					// stick or line
					if (stickWidth >= 2f) {
						canvas.drawRect(left, openY, xAxisOffset + stickX + stickWidth, closeY, mPaintNegative);
					}
					canvas.drawLine(xAxisOffset + stickX + stickWidth / 2f, highY, xAxisOffset + stickX + stickWidth
							/ 2f, lowY, mPaintNegative);
				}
				else {
					// line or point
					if (stickWidth >= 2f) {
						canvas.drawLine(left, closeY, xAxisOffset + stickX + stickWidth, openY, mPaintCross);
					}
					canvas.drawLine(left + stickWidth / 2f, highY, xAxisOffset + stickX + stickWidth / 2f, lowY,
							mPaintCross);
				}
				// next x
				stickX = stickX + 1 + stickWidth;
			}
		}
	}

	/**
	 * <p>
	 * add a new stick data to sticks and refresh this chart
	 * </p>
	 * <p>
	 * 新しいスティックデータを追加する，フラフをレフレシューする
	 * </p>
	 * <p>
	 * 追加一条新数据并刷新当前图表
	 * </p>
	 * 
	 * @param entity
	 *            <p>
	 *            data
	 *            </p>
	 *            <p>
	 *            データ
	 *            </p>
	 *            <p>
	 *            新数据
	 *            </p>
	 */
	public void pushData(OHLCEntity entity) {
		if (null != entity) {
			addData(entity);
			super.postInvalidate();
		}
	}

	/**
	 * <p>
	 * add a new stick data to sticks
	 * </p>
	 * <p>
	 * 新しいスティックデータを追加する
	 * </p>
	 * <p>
	 * 追加一条新数据
	 * </p>
	 * 
	 * @param entity
	 *            <p>
	 *            data
	 *            </p>
	 *            <p>
	 *            データ
	 *            </p>
	 *            <p>
	 *            新数据
	 *            </p>
	 */
	public void addData(OHLCEntity entity) {
		if (null != entity) {
			if (null == OHLCData || 0 == OHLCData.size()) {
				OHLCData = new ArrayList<OHLCEntity>();
				this.minValue = ((int) entity.getLow()) / 10 * 10;
				this.maxValue = ((int) entity.getHigh()) / 10 * 10;
			}

			this.OHLCData.add(entity);

			if (this.minValue > entity.getLow()) {
				this.minValue = ((int) entity.getLow()) / 10 * 10;
			}

			if (this.maxValue < entity.getHigh()) {
				this.maxValue = 10 + ((int) entity.getHigh()) / 10 * 10;
			}

			if (OHLCData.size() > maxSticksNum) {
				maxSticksNum = maxSticksNum + 1;
			}
		}
	}

	private final int NONE = 0;
	private final int ZOOM = 1;
	private final int DOWN = 2;

	private float olddistance = 0f;
	private float newdistance = 0f;

	private int TOUCH_MODE;

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when chart is touched<p> <p>チャートをタッチしたら、メソッドを呼ぶ<p>
	 * <p>图表点击时调用<p>
	 * 
	 * @param event
	 * 
	 * @see android.view.View#onTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super.getWidth() / 50);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			Log.e("debug", "down");
			firstDownX = event.getX();
			TOUCH_MODE = DOWN;
			preDownX = firstDownX;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			TOUCH_MODE = NONE;
			return super.onTouchEvent(event);
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_LENGTH) {
				TOUCH_MODE = ZOOM;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			final int pointerCount = event.getPointerCount();
			Log.e("debug", "count:" + pointerCount);

			float currentX = event.getX();
			float diff = currentX - preDownX;
			xAxisOffset += diff;
			Log.e("debug", "diff:  " + diff + "  xAxis:" + xAxisOffset);
			if (TOUCH_MODE == ZOOM) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_LENGTH && Math.abs(newdistance - olddistance) > MIN_LENGTH) {

					if (newdistance > olddistance) {
						zoomIn();
					}
					else {
						zoomOut();
					}
					olddistance = newdistance;

				}
			}
			preDownX = currentX;
			initAxisX();
			super.postInvalidate();
			super.notifyEventAll(this);
			break;
		}
		Log.e("debug", "first:" + getFirstIndex() + " last:" + getLastIndex());
		return true;
	}

	/**
	 * <p>
	 * calculate the distance between two touch points
	 * </p>
	 * <p>
	 * 複数タッチしたポイントの距離
	 * </p>
	 * <p>
	 * 计算两点触控时两点之间的距离
	 * </p>
	 * 
	 * @param event
	 * @return float
	 *         <p>
	 *         distance
	 *         </p>
	 *         <p>
	 *         距離
	 *         </p>
	 *         <p>
	 *         距离
	 *         </p>
	 */
	private float calcDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * <p>
	 * Zoom in the graph
	 * </p>
	 * <p>
	 * 拡大表示する。
	 * </p>
	 * <p>
	 * 放大表示
	 * </p>
	 */
	protected void zoomIn() {
		if (maxSticksNum > 10) {
			stickWidth += 1;
			maxSticksNum = maxSticksNum - 3;
		}
	}

	/**
	 * <p>
	 * Zoom out the grid
	 * </p>
	 * <p>
	 * 縮小表示する。
	 * </p>
	 * <p>
	 * 缩小
	 * </p>
	 */
	protected void zoomOut() {
		if (maxSticksNum < OHLCData.size() - 1) {
			stickWidth += 1;
			maxSticksNum = maxSticksNum + 3;
		}
	}

	/**
	 * @return the positiveStickBorderColor
	 */
	public int getPositiveStickBorderColor() {
		return positiveStickBorderColor;
	}

	/**
	 * @param positiveStickBorderColor
	 *            the positiveStickBorderColor to set
	 */
	public void setPositiveStickBorderColor(int positiveStickBorderColor) {
		this.positiveStickBorderColor = positiveStickBorderColor;
	}

	/**
	 * @return the positiveStickFillColor
	 */
	public int getPositiveStickFillColor() {
		return positiveStickFillColor;
	}

	/**
	 * @param positiveStickFillColor
	 *            the positiveStickFillColor to set
	 */
	public void setPositiveStickFillColor(int positiveStickFillColor) {
		this.positiveStickFillColor = positiveStickFillColor;
	}

	/**
	 * @return the negativeStickBorderColor
	 */
	public int getNegativeStickBorderColor() {
		return negativeStickBorderColor;
	}

	/**
	 * @param negativeStickBorderColor
	 *            the negativeStickBorderColor to set
	 */
	public void setNegativeStickBorderColor(int negativeStickBorderColor) {
		this.negativeStickBorderColor = negativeStickBorderColor;
	}

	/**
	 * @return the negativeStickFillColor
	 */
	public int getNegativeStickFillColor() {
		return negativeStickFillColor;
	}

	/**
	 * @param negativeStickFillColor
	 *            the negativeStickFillColor to set
	 */
	public void setNegativeStickFillColor(int negativeStickFillColor) {
		this.negativeStickFillColor = negativeStickFillColor;
	}

	/**
	 * @return the crossStarColor
	 */
	public int getCrossStarColor() {
		return crossStarColor;
	}

	/**
	 * @param crossStarColor
	 *            the crossStarColor to set
	 */
	public void setCrossStarColor(int crossStarColor) {
		this.crossStarColor = crossStarColor;
	}

	/**
	 * @return the oHLCData
	 */
	public List<OHLCEntity> getOHLCData() {
		return OHLCData;
	}

	/**
	 * @param oHLCData
	 *            the oHLCData to set
	 */
	public void setOHLCData(List<OHLCEntity> oHLCData) {
		if (null != OHLCData) {
			OHLCData.clear();
		}
		for (OHLCEntity e : oHLCData) {
			addData(e);
		}
	}

	/**
	 * @return the maxSticksNum
	 */
	public int getMaxSticksNum() {
		int StcickTotalwidth = (int) (super.getWidth() - getAxisMarginLeft());
		int count = (int) ((int) StcickTotalwidth / stickWidth);
		if (StcickTotalwidth % stickWidth != 0) {
			count = count + 1;
		}
		return count;
		// return maxSticksNum;
	}

	/**
	 * @param maxSticksNum
	 *            the maxSticksNum to set
	 */
	public void setMaxSticksNum(int maxSticksNum) {
		this.maxSticksNum = maxSticksNum;
	}

	/**
	 * @return the maxValue
	 */
	public float getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the minValue
	 */
	public float getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public void resetMax() {

	}
}
