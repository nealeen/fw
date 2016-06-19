
<title>教师管理</title>

<link rel="stylesheet" href="${webUrl}/static/ace/css/ui.jqgrid.css" />
<script src="${webUrl}/static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
<script src="${webUrl}/static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>

<script src="${webUrl}/static/echarts/esl.js"></script>
<script src="${webUrl}/static/echarts/echarts.js"></script>

<!-- ajax layout which only needs content area -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="well well-sm">
			相关流程：
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
		</div>

		<div id="main" style="width:100%;height:300px;margin-bottom:10px;"></div>
		<div class="jqGrid_wrapper">
            <table id="teacher-list"></table>
            <div id="teacher-pager"></div>
        </div>
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<div id="content"></div>
<div id="example">33</div>


<script type="text/javascript">
   
</script>
    

<!-- page specific plugin scripts -->
<script type="text/javascript">
	$('.page-content-area').ace_ajax('loadScripts', [], function() {
		//加载部门列表
		$("#teacher-list").jqGrid({url:"${request.contextPath}/basedata/unit/${unitId!}/teachers",
			datatype: "json",mtype:"GET",height:"auto",autowidth:true,shrinkToFit:true,rowNum:10,rowList:[10,20,30,100],
			viewrecords:true,hidegrid:true,
			caption: "教师信息",
			colNames:["", "ID", "编号", "姓名","性别", "所在部门", "所属单位"],
			colModel:[
				{name:"_operation",fixed:true,width:60,sortable:false},
				{name:"id",width:90,sortable:false,hidden:true},
				{name:"teacherCode",width:90,sortable:false},
				{name:"teacherName",width:90,sortable:false},
				{name:"sex",width:100,sortable:false, formatter:"select", editoptions:{value:"${mcodeSetting.getMcodeWithJqGrid("DM-XB")}"}},
				{name:"dept.deptName",width:100,sortable:false},
				{name:"unit.unitName",width:100,sortable:false}
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
		
		require.config({
			paths : {
				'echarts' : '${request.contextPath}/static/echarts/echarts',
				'echarts/chart/bar' : '${request.contextPath}/static/echarts/chart/bar',
				'echarts/chart/line' : '${request.contextPath}/static/echarts/chart/line'
			}
		});
		
		var myChart;
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line'], function(ec) {
			myChart = ec.init(document.getElementById('main'));
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
					    }).trigger("reloadGrid");
				});
	    		},
	     		error : function(XMLHttpRequest, textStatus, errorThrown) {  
	     			var text = syncText(XMLHttpRequest);
	     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
	    		}
	    	});
		});
		
		//resize
		$(window).bind("resize",function(){
			var width=$(".jqGrid_wrapper").width();
			$("#teacher-list").setGridWidth(width);
			$("#main").width(width);
			myChart.resize();
		});
	
  		function dealTeachers(){
  			var ids = jQuery("#teacher-list").jqGrid('getDataIDs');
      		for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
	          	var rowData = $("#teacher-list").getRowData(id);
	          	var editBtn = buttons(rowData.deptName, "edit_btn_", id, "green", "fa-pencil");
	          	var trashBtn = buttons(rowData.deptName, "del_btn_", id, "red", "fa-trash-o");
          		$("#teacher-list").jqGrid('setRowData', ids[i], {_operation: "<div class=\"action-buttons\">" + editBtn + " " + trashBtn + "</div>" });
          
          		$("#edit_btn_" + id).on("click", function(){
          			var value = $(this).attr("value");
          			var index = layer.load(2);
          			$("#content").load('${request.contextPath}/basedata/teacher/' + value + '/detail',
          				function(){
          					layer.close(index);  
          					layerDiv('', '360px', $('#content'), '教师信息');
          				}
          			);
          		});
          
          		$("#del_btn_" + id).on("click", function(){
          			var value = $(this).attr("value");
          			var name = $(this).attr("name");
          			swal({title: "删除教师", html: true, 
						text: "是否要删除<strong><font color='red'>" + name + "</font></strong>？",   
						type: "warning", showCancelButton: true, closeOnConfirm: false, confirmButtonText: "是",
						cancelButtonText: "否",showLoaderOnConfirm: true
					}, 
					function(){   
						$.ajax({
				    		url:'${request.contextPath}/basedata/teacher/' + value + '/delete',
				    		data: "", type:'delete', beforeSend:function(XMLHttpRequest){},  
				    		success:function(data) {
				    			swal({title: "操作成功!",
				    			text: data,type: "success",showConfirmButton: true,confirmButtonText: "是"},
				    			function(){$("#dept-list").trigger("reloadGrid");});},
				     		error : function(XMLHttpRequest, textStatus, errorThrown) {  
				     			var text = syncText(XMLHttpRequest);
				     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
				    		}
				    	});
					});
				});
      		}
  		}
	});
</script>
