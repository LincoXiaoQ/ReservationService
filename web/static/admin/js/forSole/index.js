// 初始化变量
var dynamic_textState;
var dynamic_qNum;
var dynamic_wNum;
var dynamic_openGroupNum;

var button_SF;
var dynamic_SFstate;
var dynamic_courrentSerTime;
var button_SP;
var dynamic_SPstate;
var button_jump;
var dynamic_jumpTime;

var switch_ser;
var dynamic_serTime;
var switch_g1;
var switch_g2;
var switch_g3;
var switch_g4;
var dynamic_courrentTime;
var dynamic_adName;
var dynamic_lastLogin;
var modal_win;
var modal_text;

//非视图元素变量
//dynamicUpdateData
var jsonData;
var ajaxUrl;
//由计算控制的时间
var courrentSerTime;
var jumpTime;
//辅助判断的变量
var state_ser;
var state_SP;
//线程管理量
var cst_timeTask;
var jump_timeTask;

//按钮监听
var button_SF_Listener;
var button_SP_Listener;
var button_jump_Listener;

function init(){
	dynamic_textState=$('#dynamic-textState');				//本地控制
	dynamic_qNum=$('#dynamic-qNum');						//动态更新
	dynamic_wNum=$('#dynamic-wNum');						//动态更新
	dynamic_openGroupNum=$('#dynamic-openGroupNum');		//初值_本地计算

	button_SF=document.getElementById('button-SF');								//动态更新(class)
	dynamic_SFstate=button_SF.getElementsByTagName('span')[0];			//本地控制
	dynamic_courrentSerTime=button_SF.getElementsByTagName('span')[1];	//动态更新+本地计算
	button_SP=document.getElementById('button-SP');
	dynamic_SPState=button_SP.getElementsByTagName('span')[0];			//动态更新
	button_jump=document.getElementById('button-jump');							//动态更新(颜色)
	dynamic_jumpTime=button_jump.getElementsByTagName('span')[0];		//动态更新+本地计算

	switch_ser=$('#switch-ser');
	dynamic_serTime=$('#dynamic-serTime');					//初值+本地计算
	switch_g1=$('#switch-g1');								//初值+本地控制
	switch_g2=$('#switch-g2');								//初值+本地控制
	switch_g3=$('#switch-g3');								//初值+本地控制
	switch_g4=$('#switch-g4');								//初值+本地控制
	dynamic_courrentTime=$('#dynamic-courrentTime');		//本地计算
	dynamic_adName=$('#dynamic-adName');					//初值
	dynamic_lastLogin=$('#dynamic-lastLogin');				//初值

	modal_win=$('#modal-win');
	modal_text=modal_win.children('div div div #modal-text');

	jsonData=new Object();
	jsonData.code='101';
	ajaxUrl='/ajax/admin/index';

	button_SF_Listener=function (){
		if (typeof jsonData!=undefined) {
			jsonData.state_SF=jsonData.state_SF==0?1:0;
			ajax(ajaxUrl,'state_SF-'+jsonData.state_SF,onSuccess);
			//重绘 这样没有传达的话只有下次通信才能矫正
			setButton_SF(jsonData.state_SF==0);
		}
	};
	button_SP_Listener=function(){
		if (typeof jsonData!=undefined) {
			jsonData.state_SP=!jsonData.state_SP;
			ajax(ajaxUrl,'state_SP-'+jsonData.state_SP,onSuccess);
			//重绘 这样没有传达的话只有下次通信才能矫正
			dynamic_SPState.innerHTML=json.state_SP?'P':'S';
		}
	};
	button_jump_Listener=function(){
		if (state_ser==true) {
			if (!jsonData.state_SF) {
				ajax(ajaxUrl,'op-jump',onSuccess);
			}
		}
	};
	button_SF.onclick=button_SF_Listener;
	button_SP.onclick=button_SP_Listener;
	button_jump.onclick=button_jump_Listener;
	//绑定switch
	$('.bootstrapSwitch').on('switchChange.bootstrapSwitch', onSwitch);
	//初始化值
	ajax(ajaxUrl,'NoOP',onSuccess);
}


//Switch监听
var onSwitch=function onBootstrapSwitch(event,state){
	var id=event.currentTarget.id;
	var openGroupNum=parseInt(dynamic_openGroupNum.text());
	switch (id){
		case 'switch-g1':
			console.log('G1:'+state);
			ajax(ajaxUrl,'switch_g1-'+state);
			if (state)
				dynamic_openGroupNum.text(openGroupNum+1);
			else
				dynamic_openGroupNum.text(openGroupNum-1);
			break;
		case 'switch-g2':
			console.log('G2:'+state);
			ajax(ajaxUrl,'switch_g2-'+state);
			if (state)
				dynamic_openGroupNum.text(openGroupNum+1);
			else
				dynamic_openGroupNum.text(openGroupNum-1);
			break;
		case 'switch-g3':
			console.log('G3:'+state);
			ajax(ajaxUrl,'switch_g3-'+state);
			if (state)
				dynamic_openGroupNum.text(openGroupNum+1);
			else
				dynamic_openGroupNum.text(openGroupNum-1);
			break;
		case 'switch-g4':
			console.log('G4:'+state);
			ajax(ajaxUrl,'switch_g4-'+state);
			if (state)
				dynamic_openGroupNum.text(openGroupNum+1);
			else
				dynamic_openGroupNum.text(openGroupNum-1);
			break;
		case 'switch-ser':
			ajax(ajaxUrl,'switch_ser-'+state);
			break;
	}
};

//ajax返回
var onSuccess=function onReceived(data){
	var json=JSON.parse(data);
	jsonData.value_courrentSerTime=courrentSerTime;
	jsonData.value_jumpTime=jumpTime;
	if (json.code=='101') {
		if (jsonData!=json) {
			//修正对象值
			if (json.value_qNum!=jsonData.value_qNum) {
				dynamic_qNum.innerHTML=json.value_qNum;
			}
			if (json.value_wNum!=jsonData.value_wNum) {
				dynamic_wNum.innerHTML=json.value_wNum;
			}
			//state_SF有三种:1(正在服务),0(正在等待),-1(队列空)
			if (json.state_SF!=jsonData.state_SF) {
				setButton_SF(json.state_SF>0);
			}
			if (json.state_SP!=jsonData.state_SP) {
				//true表示正在工作
				state_SP=json.state_SP;
				dynamic_SPState.innerHTML=json.state_SP?'P':'S';
			}
			if (json.state_ser!=jsonData.state_ser) {
				state_ser=json.state_ser;
				switch_ser.find('input').bootstrapSwitch('toggleState');
				//switch_ser.toggleState(json.state_ser);版本问题.不支持
			}
			if (json.value_courrentSerTime!=jsonData.value_courrentSerTime) {
				var time_m=parseInt(json.value_courrentSerTime/60);	//要转int
				var time_s=json.value_courrentSerTime%60;
				clearTimeout(cst_timeTask);
				setCourrentSerTime(time_m,time_s);
				//计时服务重置并设初值(依赖上面两个的正确设置状态)
			}
			if (json.state_SF==0) {		//未开始此服务,在等待
				setButton_jump(json.value_jumpTime);
			}else{
				cleanButton_jump();
			}
			//Todo
			//存下新值(需要手动回收?)
			jsonData=json;
		}else if (json.code=='100') {
			modal_text.text(json.text);
			modal_win.modal();
		}
	}
}

/*支持方法*/
//界面重绘函数
function setButton_SF(state){
	if (state){
		//修改class
		$(dynamic_SFstate).removeClass('glyphicon-pause');
		$(dynamic_SFstate).addClass('glyphicon-play');
		//重新计时
		setCourrentSerTime(0,0);
	}else{
		//修改class	
		$(dynamic_SFstate).removeClass('glyphicon-play');
		$(dynamic_SFstate).addClass('glyphicon-pause');
		//重新计时
		setCourrentSerTime(0,0);
	}
}
function setButton_jump(timeS){
	clearTimeout(jump_timeTask);
	if (timeS>0) {
		//设置颜色
		//注意DOM对象转jQ对象方式
		$(button_jump).removeClass('button-caution');
		$(button_jump).addClass('button-primary');
	}
	//开启倒计时(timeS)
	timeD(timeS);
}
function cleanButton_jump(){
	clearTimeout(jump_timeTask);
	$(button_jump).removeClass('button-caution');
	$(button_jump).addClass('button-primary');
	//置零
	dynamic_jumpTime.innerHTML="0";
}
//界面重绘的依赖函数,都要改正重复调用的问题
function setCourrentSerTime(time_m,time_s){
	if (state_ser&&state_SP){
		if (time_s==60) {
			time_s=0;
			time_m++;
			if (time_m==60) {
				return;
			}
		}
		time_s++;
		//异步队列定时任务,多次循环下越靠前定时越准确
		cst_timeTask=setTimeout(function() {setCourrentSerTime(time_m,time_s)},999);
	}else if (!state_ser) {
		dynamic_courrentSerTime.innerHTML='';
		return;
	}
	var t_second="0"+time_s;
	dynamic_courrentSerTime.innerHTML=time_m+":"+t_second.substring(t_second.length-2);
}
{

	function timeP(wait){
		if (state_ser&&state_SP){
	 		if(wait<90) {
				wait++;
				dynamic_jumpTime.innerHTML=wait;
				jump_timeTask=setTimeout(function() {timeP()},999)
			}
		}
	}
	function timeD(wait) {
		if (wait <= 0) {
			timeP(-wait);
			//设置颜色
			$(button_jump).removeClass('button-primary');
			$(button_jump).addClass('button-caution');
		} else if (state_ser&&state_SP){
			wait--;
			dynamic_jumpTime.innerHTML=wait;
			jump_timeTask=setTimeout(function() {timeD()},999);
		}
	}
}

//ajax通讯方法
function ajax(url,data,onSuccess) {
	$.ajax({
		type: "POST",
		url: url,
		data: "op="+data,
		cache: false, //不缓存此页面   
		success: onSuccess
	});
}


// 调用
window.onload=function(){
	init();
	connect();
}


/*
(1)通过thymeleaf生成初始化值
(2)通过Ajax传递控制信息
(3)管理计时线程考虑用线程间通讯实现
 */


//webSocket通讯处理方法
/*function doMessage(json){
	if (json.code=='101') {
		if (jsonData!=json) {
			//修正对象值
			//Todo
			//存下新值
			jsonData=json;
		}
	}
}
function doSend(){
	var msg='';
	if (send!=undefined)
		send(msg);
}*/
// 状态是input的,事件是外层div的