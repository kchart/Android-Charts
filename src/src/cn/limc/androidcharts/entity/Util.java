package cn.limc.androidcharts.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create time：2013-12-17 下午4:00:03 declare:
 */
public class Util {

	public static List<Float> initMA(int days, List<OHLCEntity> ohlc) {
		if (days < 2) {
			return null;
		}
		List<Float> MA5Values = new ArrayList<Float>();
		float sum = 0;
		float avg = 0;
		for (int i = 0; i < ohlc.size(); i++) {
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

}
