//827修改后不调用此方法
//变量
var list_currentList;
var list_paging;
//初始化
function init(){
	list_currentList=$('#list-currentList');
	list_paging=$('#list-paging').eq(0);
	doSend('102');
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
	if (arr.length==0)
	arr.forEach(addCurrentQueueItem);
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

//通讯方法
var doMessage=function (response){
	var json=$.parseJSON(response);
	if (json.code=='103') {
		setPaging(json.pageNum);
		setQueue(json.list);
	}
}
function doSend(msg){
	ajax("../ajax/admin/queueList",msg,doMessage);
}


// 调用
window.onload=function(){
	init();
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

// document.write(" <script language=\"javascript\" src=\"../ajax.js\" > <\/script>");