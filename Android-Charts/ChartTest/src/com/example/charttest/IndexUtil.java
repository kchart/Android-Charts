package com.example.charttest;

import java.util.ArrayList;
import java.util.List;

public class IndexUtil {

	/**
	 * calculate simple moving average 计算简单移动平均值
	 * 
	 * @param data
	 *            原始数据
	 * @param period
	 *            周期
	 * @return
	 */
	public static List<Float> calculateSMA(List<Float> data, int period) {
		if (null == data || data.isEmpty() || period < 2) {
			return data;
		}
		// List<Float> result = new ArrayList<Float>();
		// float temp = 0;
		// for (int i = 0; i < period; i++) {
		// temp += data.get(i);
		// }
		// temp = temp / period;
		// result.addAll(data.subList(0, period - 1));//用原数据填充前period - 1
		// result.add(temp);
		// for (int i = period; i < data.size(); i++) {
		// temp += (data.get(i) - data.get(i - period)) / period;
		// result.add(temp);
		// }
		return calculateSMA(data, period, data.get(0));
	}

	/**
	 * 根据初始SMA计算简单移动平均值
	 * 
	 * @param data 原始数据
	 * @param period 周期
	 * @param sma
	 * @return
	 */
	public static List<Float> calculateSMA(List<Float> data, int period, float sma) {
		if (null == data || period < 2) {
			return data;
		}
		List<Float> result = new ArrayList<Float>();
		float temp = sma;
		for (int i = 0; i < data.size(); i++) {
			temp += (data.get(i) - (i < period ? sma / period : data.get(i - period))) / period;
			result.add(temp);
		}
		return result;
	}

	/**
	 * calculate exponential moving average 计算指数移动平均值
	 * 
	 * @param data 原始数据
	 * @param period 周期
	 * @return
	 */
	public static List<Float> calculateEMA(List<Float> data, int period) {
		if (null == data || data.isEmpty() || period < 2) {
			return data;
		}
//		List<Float> result = new ArrayList<Float>();
//		float temp = 0;
//		double exp = 2 / (period + 1);// 指数
//		double denominator = 0;// 分母
//		for (int i = 0; i < period; i++) {
//			temp += data.get(i) * Math.pow(1 - exp, i);
//			denominator += Math.pow(1 - exp, i);
//		}
//		temp = (float) (temp / denominator);
//		result.addAll(data.subList(0, period - 1));// 用原数据填充前period - 1
//		result.add(temp);
//		for (int i = period; i < data.size(); i++) {
//			temp += exp * (data.get(i) - temp);
//			result.add(temp);
//		}
		return calculateEMA(data,period,data.get(0));
	}

	/**
	 * 根据初始EMA计算指数移动平均值
	 * 
	 * @param data 原始数据
	 * @param period 周期
	 * @param ema 初始EMA
	 * @return
	 */
	public static List<Float> calculateEMA(List<Float> data, int period, float ema) {
		if (null == data || period < 2) {
			return data;
		}
		List<Float> result = new ArrayList<Float>();
		float temp = ema;
		double exp = 2 / (period + 1);// 指数
		for (int i = 0; i < data.size(); i++) {
			temp += exp * (data.get(i) - temp);
			result.add(temp);
		}
		return null;
	}

	/**
	 * 
	 * 计算差离值
	 * @param fast 快周期
	 * @param slow 慢周期
	 * @return
	 */
	public static List<Float> calculateDIFF(List<Float> data, int fast, int slow) {
		if (null == data || fast < 2 || slow < 2 || fast > slow) {
			return data;
		}
		List<Float> result = new ArrayList<Float>();
		List<Float> emaFast = calculateEMA(data, fast);
		List<Float> emaSlow = calculateEMA(data, slow);
		if (null != emaFast && null != emaSlow) {
			for (int i = 0; i < emaFast.size() && i < emaSlow.size(); i++) {
				result.add(emaFast.get(i) - emaSlow.get(i));
			}

		}
		return result;
	}

	/**
	 * 就算讯号值
	 * 
	 * @return
	 */
	public static List<Float> calculateDEM(List<Float> diff, int period) {
		return calculateEMA(diff, period);
	}

	/**
	 * calculate Moving Average Convergence / Divergence 计算指数平滑异同移动平均值
	 * 
	 * @return
	 */
	public static List<Float> calculateMACD(List<Float> diff, List<Float> dem) {
		if (null == diff || null == dem) {
			return null;
		}
		List<Float> result = new ArrayList<Float>();
		for (int i = 0; i < diff.size() && i < dem.size(); i++) {
			result.add(diff.get(i) - dem.get(i));
		}
		return result;
	}
}
