function ajax(url,data,onSuccess) {
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		cache: false, //不缓存此页面   
		success: onSuccess
	});
}

// 把url写成本地文件说不定就可以本地调试了