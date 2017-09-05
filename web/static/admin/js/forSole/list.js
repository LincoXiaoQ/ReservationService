//827修改后不调用此方法
//变量
var list_currentList;
var list_paging;
//初始化
function init(){
	list_currentList=$('#list-currentList');
	list_paging=$('list-paging');
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
	currentList.empty();
	var arr=json.queue;
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
function doMessage(json){
	if (json.code=='102') {
		setPaging(json.pageNum);
		setQueue(json);
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
