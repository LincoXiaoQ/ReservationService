package com.MVC.controller.Manager;

import com.MVC.model.Admin;
import com.MVC.model.QueueUser;
import com.support.ThisServer;
import com.support_Singleton.RunningServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Linco_S on 2017/8/2.
 * 控制面板的通讯和响应
 */
@Controller
@ResponseBody
public class ControlPadResponder {
	@Autowired
	RunningServer runningServer;
	@RequestMapping("/ajax/admin/index")
	public String doControl(HttpServletRequest httpServletRequest, @RequestParam("op") String command){
		System.out.println("thisServer: "+command);
		// TODO: 2017/8/19 哪来的attribute
		ThisServer thisServer= (ThisServer) httpServletRequest.getSession().getAttribute("thisServer");
		if (thisServer==null){
			Admin admin= (Admin) httpServletRequest.getSession().getAttribute("admin");
			if (admin!=null) {
				switch (command) {
					case "switch_ser-true":
						thisServer = runningServer.getServer(admin.getAid());
						//初始化hs,多登录排挤
						thisServer.setHs(httpServletRequest.getSession());
						httpServletRequest.getSession().setAttribute("thisServer",thisServer);
						break;
					default://返回出错报告
						return "{\"code\":400}";
				}
			}
			return "{\"code\":400}";
		}
		switch (command){
			case "state_SF-start":
				thisServer.onServing();
				break;
			case "state_SF-finish":
				// 读取下一个
				thisServer.onFinish();
				break;
			case "state_SP-true":
				if (!thisServer.isState_SP()) {    //暂停后开始的状态
					thisServer.setState_SP(true);
					thisServer.onWait();
				}
				break;
			case "state_SP-false":
				if (thisServer.getState_SF()!=1)	//不正在服务中
					thisServer.setState_SP(false);	//不用清理状态,因为切换到onWait()会自动初始化
				break;
			case "op-jump":
				thisServer.onJump();
				break;
			case "switch_g1-true":
				thisServer.getQoe().add(new QueueUser(12345,"Linco",120));
				//如果该组中有人排队,不允许切换
				break;
			case "switch_g1-false":
				break;
			case "switch_g2-true":
				break;
			case "switch_g2-false":
				break;
			case "switch_g3-true":
				break;
			case "switch_g3-false":
				break;
			case "switch_g4-true":
				break;
			case "switch_g4-false":
				break;
			case "switch_ser-true":
				thisServer.onOpenSer();
				break;
			case "switch_ser-false":
				thisServer.onCloseSer();
				break;
		}
		String response = ("{\"code\":101," +
				"\"value_qNum\":" + thisServer.getQits().getQueue().getCurrentQueue().size() +
				",\"value_wNum\":" + thisServer.getQits().getWaitQueue().getSize() +
				",\"state_SF\":" + thisServer.getState_SF() +
				",\"state_SP\":" + thisServer.isState_SP() +
				",\"state_ser\":" + thisServer.isState_ser() +
				",\"value_currentSerTime\":" + thisServer.getCutc().getTimeLine() +
				",\"value_allSerTime\":" + thisServer.getAllServerTime() +
				",\"value_jumpTime\":" + thisServer.getCutc().getTempAboveTime() + "}");
		return response;
	}
}

//Socket连接断开,倒计时移除服务
/*
httpServletRequest.getSession().removeAttribute("thisServer");
* */

//json的key也是双引号包裹的