/*
 * IndexMAStickChart.java
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

package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @version v1.0 2013年12月16日 下午3:45:51
 * 
 */
public class IndexMAStickChart extends MAStickChart {

	private Map<String, Float> values;

	private List<String> keys;

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public IndexMAStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public boolean onTouchEvent(MotionEvent event) {
//		super.onTouchEvent(event);
//		postInvalidate();
		return super.onTouchEvent(event);
	}

	protected void onDraw(Canvas canvas) {
//		initAxisX();
//		initAxisY();
		super.onDraw(canvas);
//		drawSticks(canvas);
	}

	protected void initAxisX() {
		List<String> TitleX = new ArrayList<String>();
		if (null != keys) {
			float average = maxSticksNum / this.getLongitudeNum();
			for (int i = 0; i < this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average);
				if (index > maxSticksNum - 1) {
					index = maxSticksNum - 1;
				}
				TitleX.add(keys.get(index));
			}
			TitleX.add(keys.get(maxSticksNum - 1));
		}
		this.setAxisXTitles(TitleX);
	}
	

	protected void initAxisY() {
		List<String> TitleY = new ArrayList<String>();
		float average = (maxValue - minValue) / this.getLatitudeNum();
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = String.valueOf(minValue + i * average);
			if (value.length() < super.getAxisYMaxTitleLength()) {
				while (value.length() < super.getAxisYMaxTitleLength()) {
					value = new String(" ") + value;
				}
			}
			TitleY.add(value);
		}
		// calculate last degrees by use max value
		String value = String.valueOf(maxValue);
		if (value.length() < super.getAxisYMaxTitleLength()) {
			while (value.length() < super.getAxisYMaxTitleLength()) {
				value = new String(" ") + value;
			}
		}
		TitleY.add(value);
		this.setAxisYTitles(TitleY);
	}

	protected void drawWithFingerClick(Canvas canvas) {
		super.drawWithFingerClick(canvas);
	}

	protected void drawSticks(Canvas canvas) {

		// super.drawSticks(canvas);
		if (null == keys) {
			return;
		}
		float stickWidth = (getWidth() - getAxisMarginLeft() - getAxisMarginRight()) / this.maxSticksNum;
		float heightRatio = (getHeight() - getAxisMarginBottom() - getAxisMarginTop())
				/ (this.maxValue - this.minValue);
		float value;
		float baseValue;
		if (minValue < 0) {
			baseValue = 0;
		}
		else {
			baseValue = minValue;
		}

		Paint paint = new Paint();
		for (int i = 0; i < keys.size(); i++) {
			value = values.get(keys.get(i));
			if (value > 0) {
				paint.setColor(Color.RED);
				canvas.drawRect(getAxisMarginLeft() + i * stickWidth + 1, getHeight() - getAxisMarginBottom()
						- (value - minValue) * heightRatio, getAxisMarginLeft() + (i + 1) * stickWidth, getHeight()
						- getAxisMarginBottom() - (baseValue - minValue) * heightRatio, paint);
			}
			if (value < 0) {
				paint.setColor(Color.GREEN);
				canvas.drawRect(getAxisMarginLeft() + i * stickWidth + 1, getHeight() - getAxisMarginBottom()
						- (baseValue - minValue) * heightRatio, getAxisMarginLeft() + (i + 1) * stickWidth, getHeight()
						- getAxisMarginBottom() - (value - minValue) * heightRatio, paint);
			}
			
			if(value == 0){
				paint.setColor(Color.GRAY);
				canvas.drawRect(getAxisMarginLeft() + i * stickWidth + 1, getHeight() - getAxisMarginBottom()
						- (baseValue - minValue) * heightRatio, getAxisMarginLeft() + (i + 1) * stickWidth, getHeight()
						- getAxisMarginBottom() - (value - minValue) * heightRatio - 2, paint);
			}
		}
	}

	public Map<String, Float> getValues() {
		return values;
	}

	public void setValues(Map<String, Float> values) {
		this.values = values;
		if (null != values) {
			if (values.size() > this.maxSticksNum) {
				maxSticksNum = values.size();
			}
			keys = new ArrayList<String>();
			for (String key : values.keySet()) {
				keys.add(key);
			}
			Collections.sort(keys, new Comparator() {

				public int compare(Object lhs, Object rhs) {
					return ((String) lhs).compareToIgnoreCase((String) rhs);
				}
			});
			for (String key : values.keySet()) {
				if (this.minValue > values.get(key)) {
					minValue = values.get(key);
				}

				if (this.maxValue < values.get(key)) {
					maxValue = values.get(key);
				}
			}
		}

	}

}
