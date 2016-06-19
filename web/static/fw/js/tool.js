_keydown = 0;
$(document).keyup(function(event){
	if(event.which == 17 && _keydown == 0) _keydown = 1; 
	else if(event.which == 16 && _keydown == 1) _keydown = 2;
	else if(event.which == 20 && _keydown == 2) _keydown = 3;
	else _keydown = 0;
});
	
Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

function syncText(xmlHttpRequest){
	var text = xmlHttpRequest.responseText;
 	var begin = text.indexOf("java.lang.RuntimeException: ");
 	var end = text.indexOf("org.springframework", begin);
 	var text = text.substring(begin + "java.lang.RuntimeException: ".length, end);
 	return text;
}


function checkValue(containerName, prefix){
	var tgs = ["INPUT:not(:file)", "SELECT", "TEXTAREA"];
	var len = tgs.length;
	var obj = new Object();
	var os;
	if(!prefix){
		prefix = "form-group-";
	}
	for(var j = 0; j < len; j ++){
		if(containerName){
			if(typeof(containerName) ==  "string"){
				os = jQuery(containerName + " " + tgs[j]);
			}else{
				len = 1;//针对单一确定元素
				os = jQuery(containerName);
			}
		}
		else{
			os = jQuery(tgs[j]);
		}
		if(os){
			os.each(function(){
				var id = $(this).attr("id");
				if(id != ""){
					$("#" + id + "-error").remove();
					$("#" + prefix + id).removeClass("has-error");
					var value = $(this).val();
					var nullable = $(this).attr("nullable");
					if(nullable == "false" && value == ""){
						obj[id] = "不能为空";
					}
				}
			});
		}
	}
	
	var cyc = 0;
	var focusObj;
	 for(key in obj){
	 	cyc = 1;
	 	$("#" + prefix + key).addClass("has-error");
	 	if(!focusObj)
	 		focusObj = $("#" + key);
	 	$("<div class='help-block error-tip' id='" + key + "-error'>" + obj[key] + "</div>").insertAfter($("#" + key));
	 }
	if(cyc > 0){
	 	if(focusObj){
	 		focusObj.focus();
	 	}
	 	return false;
	 }			 
			 
	return true;
}


function dealValue(containerName){
	var tgs = ["INPUT:not(:file)", "SELECT", "TEXTAREA"];
	var len = tgs.length;
	var obj = new Object();
	for(var j = 0; j < len; j ++){
		if(containerName){
			if(typeof(containerName) ==  "string"){
				var os = jQuery(containerName + " " + tgs[j]);
			}else{
				len = 1;//针对单一确定元素
				var os = jQuery(containerName);
			}
		}
		else{
			var os = jQuery(tgs[j]);
		}
		if(os){
			os.each(function(){
				var id = $(this).attr("id");
				if(id != ""){
					var value = $(this).val();
					obj[id] = value;
				}
			});
		}
	}
	return JSON.stringify(obj);
}

function layerDiv(width, height, content, title,  max){
	var areaSize;
	if(height == '' && width == ''){
		areaSize = 'auto';
	}
	else{
		if(height == ''){
			areaSize = width;
		}
		else{
			areaSize = [width, height];
		}
	}
	if(content.is(':hidden')){
		content.removeClass("hide");
	}
	
	var index = layer.open({
		zIndex:1000,
		type: 1,skin: 'layui-layer-demo',closeBtn: 1,shift: 2,shadeClose: true,maxmin:true,scrollbar:false,title:title,
		area: areaSize,				  
		content: content
	});
	if(max)
		layer.full(index);
	$(".layui-layer").focus();
	return index;
}

function calWidth(baseDom, minWidth, maxWidth){
	var width = $(baseDom).width() - 20;	
	if(minWidth && minWidth != null && minWidth > width){
		width = minWidth;
	}
	if(maxWidth && maxWidth != null && maxWidth < width){
		width = maxWidth;
	}
	return width;
}

function calWidthPx(baseDom, minWidth, maxWidth){
	return calWidth(baseDom, minWidth, maxWidth) + "px";
}

function formatDateWithTime(cellvalue, options, rowObject){
	if(!cellvalue || cellvalue == "0")
		return "";
	var datetime = new Date();
	datetime.setTime(cellvalue);
	return datetime.format("yyyy-MM-dd hh:mm:ss");
}

function formatDate(cellvalue, options, rowObject){
	if(!cellvalue || cellvalue == "0")
		return "";
	var datetime = new Date();
	datetime.setTime(cellvalue);
	return datetime.format("yyyy-MM-dd");
}

function cookie(key, value, expire){
	if(key && value && expire){
		$.cookie(key, encodeURIComponent(value), {"expires" : expire});
	}
	else if(key && value){
		$.cookie(key, encodeURIComponent(value));
	}
	else if(key){
		return decodeURIComponent($.cookie(key));
	}
}