var ws;
function connect(){
	var hosturl=window.location.host;
	var url=hosturl+'/Socket/admin';
	ws=new WebSocket ('ws://'+url);
	// 这里的等于要的是一个function对象而不是返回值,下同
	// (给方法对象命名就可以直接赋对吗)
	ws.onerror = function(event) {
      onError(event)
    };
 
    ws.onmessage = function(event) {
      onMessage(event)
    };
 	
    /*ws.onclose=function (event) {
    	location.reload();
    	console.log("刷新");
    }*/
    function onMessage(event) {
    	var json=JSON.parse(event.data);
    	if (doMessage!=undefined)
    		doMessage(json);
    }
 
    function onError(event) {
		if (typeof event.data!='undefined') 
			alert(event.data);
		else
			alert("连接服务器失败,请检查");
    }
 
}
function send(message){
	if (ws.readyState==WebSocket.OPEN) {
		ws.send(message);
		return true;
	}
	return false;
}