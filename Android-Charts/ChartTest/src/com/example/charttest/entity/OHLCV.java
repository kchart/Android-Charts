package com.example.charttest.entity;

public class OHLCV {

	private String date;

	private float open_price;

	private float high_price;

	private float low_price;

	private float close_price;

	private long volume;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getOpen_price() {
		return open_price;
	}

	public void setOpen_price(float open_price) {
		this.open_price = open_price;
	}

	public float getHigh_price() {
		return high_price;
	}

	public void setHigh_price(float high_price) {
		this.high_price = high_price;
	}

	public float getLow_price() {
		return low_price;
	}

	public void setLow_price(float low_price) {
		this.low_price = low_price;
	}

	public float getClose_price() {
		return close_price;
	}

	public void setClose_price(float close_price) {
		this.close_price = close_price;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

}
