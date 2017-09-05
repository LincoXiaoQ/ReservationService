package com.support.currentUserTimeHelp;

import com.support.ThisServer;

/**
 * Created by Linco on 2017/8/7.
 */
public class CurrentUserTimeHelp {
	private int timeTemp;

	private long timeLineStart;
	private long timeTempStart;
	public void startTimeLineTiming(){
		timeLineStart =System.currentTimeMillis();
	}
	public int getTimeLine(){
		return (int) (System.currentTimeMillis()- timeLineStart)/1000;
	}
	public void startTimeTemp(int tempTime){
		timeTempStart=System.currentTimeMillis();
		this.timeTemp=tempTime;
	}
	//用于计算的值
	public int getAboveTime(){
		int tempAboveTime= (int) (timeTempStart +timeTemp*1000-System.currentTimeMillis())/1000;
		return tempAboveTime>0?0:tempAboveTime;
	}
	//用于显示的值

	public int getTempAboveTime(){
		int tempAboveTime= (int) (timeTempStart +timeTemp*1000-System.currentTimeMillis())/1000;
		return tempAboveTime;
	}
}
