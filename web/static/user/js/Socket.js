var ws;
function connect(){
	// console.log(document.URI);
	var hosturl=window.location.host;
	var url=hosturl+'/Socket/user';
	ws=new WebSocket ('ws://'+url);
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
    	if (doMessage!='undefined')
    		doMessage(json);
    }
 
    function onError(event) {
    	// 使用字符串的undefined
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
	console.log("error SocketState :"+ws.readyState);
	return false;
}