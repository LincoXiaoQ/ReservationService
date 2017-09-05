package com.support_Singleton;

import com.MVC.model.QueueUser;
import com.MVC.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linco on 2017/8/7.
 * 通讯接口方法通过cid得到存放于内存的user对象,后面通过user对象作为参数调用各功能函数
 * 一般通过session保存的user对象,没有时(比如换session登录)才用这个
 * User的Login连接到这里,QueueUser对象由此唯一生成
 */
public class Uid_UserMapper {
	private Map<Integer,QueueUser> uid_queueUser=new HashMap<Integer,QueueUser>();

	//登录后获取用户对象,如果不再已登录列表,创建对象返回,存入列表,否则返回已有对象
	//user对象存于session,是登陆后检索数据库得到的
	public QueueUser getQueueUser(Object obj){
		if (!(obj instanceof User))
			return null;
		User user=(User)obj;
		int uid=user.getUid();
		if (uid_queueUser.containsKey(uid))
			return uid_queueUser.get(uid);
		else {
			QueueUser queueUser=new QueueUser(user.getUid(), user.getName(),user.getTTempAbove());
			uid_queueUser.put(uid,queueUser);
			return queueUser;
		}
	}

	// TODO: 2017/8/25 问题:queueUser对象一直存在且不同步
	//思路:撤销根据uid同步qu对象的功能--qu操作怎么办
	//思路2:提交Qu修改时清除,等待队列为空时清空  所有服务都关闭(非暂停)时会清空等待队列
}
