var display_userPic;
var display_jumpBtn;
var tempBtn;
var switch_join;
//变量
var list_currentList;
var list_paging;
var state_ser;
var state_SP;
var uid;
//模态窗
var modal_win;
var modal_winTitle;
var modal_winImg;
var modal_winText;

var modal_info;

function init(){
	display_userPic=$('#display-userPic');
	display_jumpBtn=$('#display-jumpBtn');
	tempBtn=display_jumpBtn.children('span').eq(0);
	switch_join=$('#switch-join');
	list_currentList=$('#list-currentList');
	list_paging=$('#list-paging');
	uid=getCookie('uid');
	modal_win=$('#modal-win');
	// children只遍历一级,所以不行
	// modal_winTitle=modal_win.children('div div div #modal-title');
	modal_winTitle=modal_win.find('div div div #modal-title');
	modal_winImg=modal_win.find('div div div img').eq(0);
	modal_winText = modal_win.find('div div div #modal-text');
	modal_info=$('#modal-info');

	onSwitch=function(event,state){
		if (state){
			doSend('ToQueue-true');
			console.log('ToQueue-true');
		}else
			doSend('ToQueue-false');
	}
	switch_join.on('switchChange.bootstrapSwitch', onSwitch);
	connect();
}
function setDisplay(isWhoId,timeTemp){
	/*if (isWhoId==uid) {
		display_userPic.sytle.dispaly='gone';
		display_jumpBtn.style.display='display';
		setButton_jump(state_ser,timeTemp);
	}else{
		display_userPic.sytle.dispaly='gone';
		display_jumpBtn.style.display='display';
	}*/
		display_userPic.style.dispaly='gone';
		display_jumpBtn.style.display='display';
		setButton_jump(state_ser,timeTemp);
}
function setButton_jump(state,timeS){
	clearTimeout(jump_timeTask);
	if (state) {
		//设置颜色
		//注意DOM对象转jQ对象方式
		display_jumpBtn.removeClass('button-caution');
		display_jumpBtn.addClass('button-primary');
		//开启倒计时(timeS)
		timeD(time_s);
	}
}
{

	function timeP(wait){
		if (state_ser&&state_SP){
	 		if(wait<90) {
				wait++;
				tempBtn.text(wait);
				jump_timeTask=setTimeout(function() {timeP()},999)
			}
		}
	}
	function timeD(wait) {
		if (wait <= 0) {
			timeP(-wait);
			//设置颜色
			display_jumpBtn.removeClass('button-primary');
			display_jumpBtn.addClass('button-caution');
		} else if (state_ser&&state_SP){
			wait--;
			tempBtn.text(wait);
			jump_timeTask=setTimeout(function() {timeD()},999);
		}
	}
}
function check(queueUser){
	if (queueUser.uid===uid) {
		// 设置值为队序
		console.log();
	}
}
//检查是否有本用户
function checkMe(isWhoId){
		for (var i=0;i<isWhoId.length;i++){
			if (isWhoId[i]===uid) {
				send("202");
				console.log(i);
				break;
			}
		}
}
//设置页数
function setPaging(pageNum){
	list_paging.empty();
	for(var i=1;i<=pageNum;i++)
		list_paging.append('<li><a href=\'#'+i+'\'>'+i+'</a></li>');
}
//刷新队列,依赖addCurrentQueueItem
function setQueue(queue){
	list_currentList.empty();
	var arr=queue;
	//页数,需要修正
	setPaging(parseInt(arr.length/20)+1);
	if (arr.length!=0)
		arr.forEach(addCurrentQueueItem);	//forEach的值会自动作为方法参数
	mark3();
}
//把一个列对象加入队列中
function addCurrentQueueItem(item){
	list_currentList.append('<li class=\'list-group-item\'><img src=\'images/icon/icon64.png\'>'+item+'</li>');
}
//渲染前三位
function mark3(){
	var node=list_currentList.children().first();	//或者eq(0);
	for(var i=1;i<3;i++)
		if (node!=null) {
			node.prepend('<span class=\'badge\'>'+i+'</span>');
			node=node.next();
		}else
			break;
}
function getCookie(c_name){
if (document.cookie.length>0){
  var c_start=document.cookie.indexOf(c_name + "=");
  if (c_start!=-1){
    c_start=c_start + c_name.length+1;
    var c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1)
    	c_end=document.cookie.length;
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
	return null;
}

//通讯方法
function doMessage(json){
	switch(json.code){
		case 201:
			state_ser=json.state_ser;
			state_SP=json.state_SP;
		//一些基于多队列的信息改为如果判断"是我"再发送队列标识去请求
			checkMe(json.isWhoId);
			setQueue(json.queue);
			break;
		case 202:
			state_ser=json.state_ser;
			state_SP=json.state_SP;
			if (state_ser)
				setDisplay(json.timeTemp);
		case 203:
			//显示公告
			modal_winTitle.text('新公告');
			modal_winImg.attr("src","images/pic/xinqingfuza.jpg");
			modal_winText.text(json.text);
			modal_win.modal();
			break;
		case 204:
			//显示操作信息模态窗
			modal_winTitle.text('新提醒');
			modal_winImg.attr("src","images/pic/xinqingfuza.jpg");
			modal_winText.text(json.text);
			modal_win.modal();
			break;
		default:
			console.log('unKnown command!');
			break;
	}
}
function doSend(msg){
	if (send!=undefined)
		send(msg);
}


// 调用
window.onload=function(){
	init();
}
