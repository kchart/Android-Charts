package com.example.charttest.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.charttest.entity.OHLCV;

public class CSVUtil {

	private static final String OHLCV_SPLITOR = ",";
	private static final int OHLCV_ARG_SIZE = 6;
	private static final String NUM_REG = "^\\d+.?\\d*$";

	/**
	 * 将CSV格式数据对象化
	 * @param in
	 * @return
	 */
	public static List<OHLCV> readOHLCVFromCSV(InputStream in) {
		if (null == in) {
			return null;
		}
		List<OHLCV> rslt = new ArrayList<OHLCV>();
		StringBuffer bufStr = new StringBuffer();
		char tempChar;
		try {
			BufferedInputStream bufIn = new BufferedInputStream(in);
			int data = bufIn.read();
			while(data > 0){
				tempChar = (char) data;
				if(tempChar != '\n' && tempChar != '\r'){
					bufStr.append(tempChar);
				}else{
					String arg[] = bufStr.toString().split(OHLCV_SPLITOR);
					if(arg.length >= OHLCV_ARG_SIZE){
						OHLCV ohlcv = new OHLCV();
						ohlcv.setDate(arg[0]);
						ohlcv.setOpen_price(Double.parseDouble(arg[1]));
						ohlcv.setHigh_price(Double.parseDouble(arg[2]));
						ohlcv.setLow_price(Double.parseDouble(arg[3]));
						ohlcv.setClose_price(Double.parseDouble(arg[4]));
						ohlcv.setVolume(Long.parseLong(arg[5]));
						rslt.add(ohlcv);
					}
					bufStr = new StringBuffer();
				}
				data = bufIn.read();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return rslt;
	}

	/**
	 * 将CSV格式数据对象化
	 * @param des
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<OHLCV> readOHLCVFromCSV(FileDescriptor des) {
		if (null == des) {
			return null;
		}
		List<OHLCV> rslt = new ArrayList<OHLCV>();
		try {
			BufferedReader bufReader = new BufferedReader(new FileReader(des));
			String text = bufReader.readLine().trim();
			String[] args;
			while (text != null) {
				args = text.split(OHLCV_SPLITOR);
				if (args.length >= OHLCV_ARG_SIZE) {
					OHLCV data = new OHLCV();
					data.setDate(args[0]);
					data.setOpen_price(Double.parseDouble(args[1]));
					data.setHigh_price(Double.parseDouble(args[2]));
					data.setLow_price(Double.parseDouble(args[3]));
					data.setClose_price(Double.parseDouble(args[4]));
					data.setVolume(Long.parseLong(args[5]));
					rslt.add(data);
				}
				text = bufReader.readLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return rslt;
	}
}
