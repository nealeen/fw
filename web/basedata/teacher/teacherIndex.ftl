<title>教师管理</title>
<link rel="stylesheet" href="${webUrl}/static/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/ui.jqgrid.css" />
<script src="${webUrl}/static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
<script src="${webUrl}/static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>

<!-- ajax layout which only needs content area -->
<div class="row">
	<div class="col-lg-6 col-md-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="well well-sm">
			相关功能：
			<a href="http://www.trirand.com/blog/?page_id=6" target="_blank">
				单位管理
				<i class="fa bigger-110"></i>
			</a>
			<i class="fa fa-long-arrow-right blue" aria-hidden="true"></i>
			<a href="http://www.trirand.com/blog/?page_id=6" target="_blank">
				部门管理
				<i class="fa bigger-110"></i>
			</a>
			<i class="fa fa-long-arrow-right blue" aria-hidden="true"></i>
			<a href="http://www.trirand.com/blog/?page_id=6" target="_blank">
				教师管理
				<i class="fa bigger-110"></i>
			</a>
			<i class="fa fa-long-arrow-right blue" aria-hidden="true"></i>
			<a href="http://www.trirand.com/blog/?page_id=6" target="_blank">
				用户管理
				<i class="fa bigger-110"></i>
			</a>
		</div>
	</div>
	<div class="col-lg-6 col-md-12">
		<div class="well well-sm">说明：分辨率>1200时，内容分两列</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-12">
		<div id="deptTeacherChart" style="width:100%;height:300px;margin-bottom:10px;"></div>
	</div>
</div>

<div class="row">
	<div class="col-sm-3">
		<div class="widget-box widget-color-blue2">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">请选择部门</h4>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-8">
					<ul id="deptTree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-sm-9" id="gridDiv">
		<div class="jqGrid_wrapper">
            <table id="teacher-list"></table>
            <div id="teacher-pager"></div>
        </div>
	</div>
</div>

<div class="row">
<div id="content" class="col-sm-12"></div>
</div>
<!-- page specific plugin scripts -->
<script type="text/javascript">
	$('.page-content-area').ace_ajax('loadScripts', [null,"${request.contextPath}/static/ztree/js/jquery.ztree.all-3.5.min.js",null], function() {
		//加载部门列表
	<@teacherChart />		
	<@teacherList />
	<@deptTree2 />
	
		//resize
		$(window).bind("resize",function(){
			var width = $(".row").width();
			var jqwidth=$(".jqGrid_wrapper").width();
			$("#teacher-list").setGridWidth(jqwidth);
			$("#deptTeacherChart").width(width);
			myChart.resize();
			layer.style(indexDiv, {
				width:calWidthPx('.row', null, 800),
				height:"auto"
			});
		});
	});
</script>

<#macro teacherList>
var indexDiv = 0;
$("#teacher-list").jqGrid({url:"${request.contextPath}/basedata/unit/${unitId!}/teachers",
			datatype: "json",mtype:"GET",height:"auto",autowidth:true,shrinkToFit:true,rowNum:10,rowList:[10,20,30,100],
			viewrecords:true,hidegrid:true,
			
			caption: "教师信息&nbsp; <a title='显示所有教师' href='javascript:void();' id='spn-showAllTeacher'  class='white	' href='#'><i class='ace-icon fa fa-refresh'></i></a>",
			colModel:[
				{name:"_operation",label:"操作",fixed:true,width:60,sortable:false},
				//修改了jqgrid的源码，使其支持表头th元素的样式控制(thclasses)
				{name:"id",width:90,sortable:false,hidden:true,},
				{name:"teacherCode",label:"编号",width:90,sortable:false},
				{name:"teacherName",label:"姓名",width:90,sortable:false},
				{name:"sex",label:"性别",width:100,sortable:false, formatter:"select", editoptions:{value:"${mcodeSetting.getMcodeWithJqGrid("DM-XB")}"}},
				{name:"birthday",label:"出生日期",width:100,sortable:false,formatter : formatDate},
				{name:"mobilePhone", label:"手机号码",width:100,sortable:false}
			],
			gridComplete: function () {
	          dealTeachers();
	          updatePagerIcons(this);
	        }, 
			jsonReader: {
				root: "rows", page: "page",total: "total",  records: "records", repeatitems: false,id: "id" 
            },
			pager:"#teacher-pager"}
		);
		
		function buttons(name, preIdName, id, color, icon){
			var b = "<a href=\"javascript:void();\" name=\"" + name + "\" value=\"" + id + "\" id=\"" + preIdName + id + "\" class=\"" + color + "\" href=\"#\"><i class=\"ace-icon fa " + icon + " bigger-130\"></i></a>"
			return b;
		}
		
		$("#spn-showAllTeacher").on("click", function(){
			  $("#teacher-list").jqGrid('setGridParam', {
					      url : "${request.contextPath}/basedata/unit/${unitId!}/teachers"
					    }).trigger("reloadGrid");
		}).on("mouseenter", function(){
			$(this).css("cursor", 'pointer');
		});
		
		function dealTeachers(){
  			var ids = jQuery("#teacher-list").jqGrid('getDataIDs');
      		for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
	          	var rowData = $("#teacher-list").getRowData(id);
	          	var editBtn = buttons(rowData.teacherName, "edit_btn_", id, "green", "fa-pencil");
	          	var trashBtn = buttons(rowData.teacherName, "del_btn_", id, "red", "fa-trash-o");
          		$("#teacher-list").jqGrid('setRowData', ids[i], {_operation: "<div class=\"action-buttons\">" + editBtn + " " + trashBtn + "</div>" });
          		
          		$("#edit_btn_" + id).on("click", function(){
          			var value = $(this).attr("value");
          			var index = layer.load(2);
          			$("#content").load('${request.contextPath}/basedata/teacher/' + value + '/detail',
          				function(){
          					layer.close(index);  
          					indexDiv = layerDiv(calWidthPx('.row', null, 800), '', $('#content'), '教师信息');
          				}
          			);
          		});
          		
          		$("#del_btn_" + id).on("click", function(){
          			var value = $(this).attr("value");
          			var name = $(this).attr("name");
          			swal({title: "删除教师", html: true, 
						text: "是否要删除<strong><font color='red'>" + name + "</font></strong>？",   
						type: "warning", showCancelButton: true, closeOnConfirm: false, confirmButtonText: "是",
						cancelButtonText: "否",showLoaderOnConfirm: true,animation:false
					}, 
					function(){   
						$.ajax({
				    		url:'${request.contextPath}/basedata/teacher/' + value + '/delete',
				    		data: "", type:'delete', beforeSend:function(XMLHttpRequest){},  
				    		success:function(data) {
				    			var jsonO = JSON.parse(data);
						 		if(jsonO.success){
						 			swal({title: "操作成功!",
				    					text: jsonO.msg,type: "success",showConfirmButton: true,confirmButtonText: "确定"},
				    					function(){$("#teacher-list").trigger("reloadGrid");}
				    				);
						 		}
						 		else{
				    				swal({title: "操作失败!",
				    					text: jsonO.msg,type: "error",showConfirmButton: true,confirmButtonText: "确定"},
				    					function(){$("#teacher-list").trigger("reloadGrid");}
				    				);
				    			}
				    		},
				     		error : function(XMLHttpRequest, textStatus, errorThrown) {  
				     			var text = syncText(XMLHttpRequest);
				     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
				    		}
				    	});
					});
				});
      		}
  		}
</#macro>

<#macro deptTree2>
	var setting = {
			data: {
				key: {
					title:"title"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
		function onClick(event, treeId, treeNode, clickFlag) {
			 jQuery("#teacher-list").jqGrid('setGridParam', {
				url : "${request.contextPath}/basedata/dept/" + treeNode.id + "/teachers"
					    }).trigger("reloadGrid",[{page:1}]);
		}	
		
		$.ajax({
			url:"${request.contextPath}/basedata/unit/${unitId!}/depts/ztree",
			success:function(data){
				var jsonO = JSON.parse(data);
		 		if(jsonO.success){
		 			$.fn.zTree.init($("#deptTree"), setting, JSON.parse(jsonO.msg));
		 		}
			}
		});
</#macro>

<#macro teacherChart>
require.config({
			paths : {
				'echarts' : '${request.contextPath}/static/echarts/echarts',
				'echarts/chart/bar' : '${request.contextPath}/static/echarts/chart/bar',
				'echarts/chart/line' : '${request.contextPath}/static/echarts/chart/line'
			}
		});
		
		var myChart;
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line'], function(ec) {
			myChart = ec.init(document.getElementById('deptTeacherChart'));
			$.ajax({
	    		url:'${request.contextPath}/basedata/unit/${unitId}/statTeachersByDept',
	    		type:'get', beforeSend:function(XMLHttpRequest){},  
	    		success:function(data) {
	    			var opt = JSON.parse(data);
	    			// 为echarts对象加载数据 
					var ecConfig = require('echarts/config'); 
					myChart.setOption(opt);
					myChart.on(ecConfig.EVENT.CLICK, function(param){
				    var tt = param.data.name.split(",");
					    jQuery("#teacher-list").jqGrid('setGridParam', {
					      url : "${request.contextPath}/basedata/dept/" + tt[0] + "/teachers?sex=" + tt[1]
					    }).trigger("reloadGrid",[{page:1}]);
				});
	    		},
	     		error : function(XMLHttpRequest, textStatus, errorThrown) {  
	     			var text = syncText(XMLHttpRequest);
	     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
	    		}
	    	});
		});
</#macro>


	<#if showTestInfo!true>
	<script>
		$(document).on("dblclick", function(){
			if(_keydown == 3){
				layerDiv('500px', '360px', $('#teacherIndex-modal-table'), '测试要点');
			}
			_keydown = 0;
		});
	</script>
	<div id="teacherIndex-modal-table" class="hide clearfix form-actions" tabindex="-1" height="100">
				<hr />
				<h3>linqz, 20160607</h3>
				<p>1. 调用宏的时候，如果id没有设置，则不会将数据传输到后台
				<hr />
				<h3>linqz, 20160606</h3>
				<P>1. 动态计算弹出层宽度。
				<P>2. 调整手机端显示效果。
	</div> 
	</#if>