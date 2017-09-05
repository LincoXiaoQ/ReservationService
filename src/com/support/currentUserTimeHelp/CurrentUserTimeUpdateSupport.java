package com.support.currentUserTimeHelp;

import com.MVC.model.QueueUser;
import com.hibernate.dao.impl.UserImpl;
import com.hibernate.entity.User;
import com.support.ThisServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Linco on 2017/8/7.
 */
public class CurrentUserTimeUpdateSupport {
	private final static int TIME_PLUS=30;
	@Autowired
	private UserImpl ui;
	private ThisServer thisServer;

	public CurrentUserTimeUpdateSupport(ThisServer thisServer) {
		this.thisServer = thisServer;
	}

	public boolean updateCurrentUser(boolean jump){
		//1.读user
		QueueUser qUser=thisServer.getQits().getQueue().getFirst();
		User user=ui.getUser(qUser.getUid());
		//2.读时间
		//3.计算值
		int aboveTime=thisServer.getCutc().getAboveTime();
		int timeTempAbove=0;
		if (jump&&aboveTime<0)
			timeTempAbove=user.getTTempAbove()+aboveTime;
		else if (!jump)
			timeTempAbove=(user.getTTempAbove()>0?user.getTTempAbove():0)+TIME_PLUS;
		//4.写入
		ui.updateTime(user.getUid(),timeTempAbove);
		return true;
	}
}
