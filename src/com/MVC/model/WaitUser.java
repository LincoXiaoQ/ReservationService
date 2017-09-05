package com.MVC.model;

/**
 * Created by Linco_S on 2017/7/22.
 */
public class WaitUser implements Comparable<WaitUser> {
	private long timeAddToQueue;
	private QueueUser qu;

	public WaitUser(QueueUser qu, int timeAbove) {
		this.qu = qu;
		this.timeAddToQueue = System.currentTimeMillis()+timeAbove*1000;
	}

	@Override
	public int compareTo(WaitUser wu) {
		if (this.timeAddToQueue > wu.timeAddToQueue)
			return 1;
		else if (this.timeAddToQueue < wu.timeAddToQueue)
			return -1;
		return 0;
	}

	public long getTimeAddToQueue() {
		return timeAddToQueue;
	}

	public QueueUser getQu() {
		return qu;
	}

	//CompareTo方法显示,本类对象可以读取本类其他对象的私有对象??
}
