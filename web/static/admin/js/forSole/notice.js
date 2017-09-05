var dynamic_noticeList=$('#dynamic-noticeList');
var index;
function init(){
	var onGetList=function(data){
		var json=JSON.parse(data);
		if (json.code==='103') {
			setData(json.list);
		}
	}
	var onBClick=function(){
		//有错
		var list=dynamic_niticeList.children();
		var node;
		if (window.confirm("此操作不可逆，是否确认？")) {
			//注意遍历的方式和参数,date是dom元素非jq
			list.each(function(index,data){
				node=data.getElementsByTagName('input')[0];
				if (node.checked==true) {
					doDelete(data.getElementsByTagName("a")[0]);
				}
			});
		}
	}
	// 获取初始数据
	ajax('/ajax/admin/noticeList','103',onGetList);
	//这个会在通讯完成之前调用,导致失败
	//$("#dynamic-niticeList tr td a").click(onAClick);
	$('#button-deleteSelected').click(onBClick);
}
//设置操作
function setData(arr){
	dynamic_noticeList.empty();
	index=0;
	arr.forEach(addItem);

	var onAClick=function () {
		var name = $(this);
		if (name.text() =='删除') {
			if (window.confirm("此操作不可逆，是否确认？")) {
				doDelete(this);
			}
		}else if (name.text()=='修改') {
			//注意不是add-notice.html,这个由th渲染页面返回
			location.href='add-notice?ni='+name.attr("rel");
		}
	};
	$("#dynamic-noticeList tr td a").click(onAClick);
}
//把一个列对象加入列表中
function addItem(item){
	dynamic_noticeList.append('<tr><td><input type="checkbox" class="input-control" name="checkbox[]"/></td><td class="article-title">'
		+item.title+'</td><td>'+item.time+'</td><td><a rel="'+index+'">修改</a> <a rel="'+index+++'">删除</a></td></tr>');
}
//进行删除操作,node是末端dom结点
function doDelete(node){
		/*有后台时用*/
		var id = node.rel; 
		ajax('/ajax/admin/removeNotice','delete='+id,function (data) {
			if (data==="OK") {
				// 到tr结点
				var toRemove=node.parentNode.parentNode;
				//删除
				removeElement(toRemove);
			}
		});
		// 本地测试,用时删除
		/*// 到tr结点
		var toRemove=node.parentNode.parentNode;
		//删除
		removeElement(toRemove);*/
}
//移除结点的函数
function removeElement(_element){
	var _parentElement = _element.parentNode;
	if(_parentElement){
		_parentElement.removeChild(_element);  
	}
}

//ajax通讯方法
function ajax(url,data,onSuccess) {
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		cache: false, //不缓存此页面   
		success: onSuccess
	});
}


// 调用
window.onload=function(){
	init();
}
