
<title>教师信息</title>

<#assign teacher = dto.teacher />
<div id="teacherDetail"  class="row" style="margin-top:10px;">
		<div class="clearfix">
			<input type="hidden" id="id" value="${teacher.id!}">
			<div class="form-horizontal col-sm-6 col-xs-12 col-md-6" role="form">
				<@inputDiv id="teacherCode"  value=teacher.teacherCode! />
				<@inputDiv id="teacherName"  value=teacher.teacherName! />
				<@selectDiv id="sex" value=teacher.sex! />
				<@inputDiv id="birthday" value=teacher.birthday! />
				<@inputDiv id="mobilePhone" value=teacher.mobilePhone! />
			</div>
				<div class="form-horizontal col-sm-6 col-xs-12 col-md-6" role="form">
				<@inputDiv id="nativePlace"  value=teacher.nativePlace! />
				<@selectDiv id="nation"  value=teacher.nation!  />
			</div>
		</div>
		<div class="clearfix form-actions center">
				<button class="btn btn-info" id="dept-commit" type="button" title="保存并关闭本窗口">
					<i class="ace-icon fa fa-check bigger-110"></i>
					确定
				</button>
				<button class="btn" id="dept-close" type="button" title="不保存直接关闭">
					<i class="ace-icon fa fa-times bigger-110"></i>
					关闭
				</button>
		</div>
</div><!-- /.row -->

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${webUrl}/static/ace/js/excanvas.js"></script>
<![endif]-->

<script type="text/javascript">
	var scripts = [];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	  //inline scripts related to this page
		 jQuery(function($) {
		 
		 $("#dept-close").on("click", function(){
		 	layer.closeAll(); 
		 });
		 
		 $("#dept-commit").on("click", function(){
			 $(this).addClass("disabled");
			 var check = checkValue('#teacherDetail');
			 if(!check){
			 	$(this).removeClass("disabled");
			 	return;
			 }
			 var obj = new Object();
			 obj.teacher = JSON.parse(dealValue('#teacherDetail'));
			 	$.ajax({
				    url:'${request.contextPath}/basedata/teacher/${teacher.id!}/update',
				    data: JSON.stringify(obj),  
				    type:'post',  
				    cache:false,  
				    contentType: "application/json",
				    beforeSend:function(XMLHttpRequest){
				    },  
				    success:function(data) {
				    	//layer.closeAll(); 
				    	var jsonO = JSON.parse(data);
				 		if(!jsonO.success){
				 			swal({title: "操作失败!",
			    			text: jsonO.msg,type: "error",showConfirmButton: true,confirmButtonText: "确定"}, function(){
			    			});
				 		}
				 		else{
				 			layer.tips(jsonO.msg, "#dept-commit", {tips: [4, '#228B22']});
				 			setTimeout(function(){layer.closeAll();}, 500);
				 			$("#teacher-list").trigger("reloadGrid");
		    			}
				     },  
				     error : function(XMLHttpRequest, textStatus, errorThrown) {  
				     	var text = JSON.stringify(XMLHttpRequest);
		     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
				    }  
				});
			 });
		});
	});
</script>
<#macro inputDiv value id="" displayName=""  placeholder="" readonly=false>
	<#if displayName == "">
		<#local displayName2 = (columnInfo[id]["displayName"])!"" />
		<#if displayName2 == "">
		<#local displayName2 = id />
		</#if>
	<#else>
		<#local displayName2 = displayName>
	</#if>
	<#local format = (columnInfo[id]["format"])!"" />
	<div class="form-group" id="form-group-${id!}" >
		<label class="col-md-3 control-label no-padding-right" for="${id!}"> ${displayName2!} </label>
		<div class="col-xs-12 col-sm-12 col-md-9">
			<span class="block input-icon input-icon-right">
			<#local nullable = (columnInfo[id]["nullable"]?string("true", "false"))! />
			<input nullable="${nullable!}" <#if readonly!false == true>readonly</#if> type="text" id="${id!}" placeholder="<#if placeholder="">${displayName2}<#else>${placeholder!}</#if>" class="form-control col-xs-10 col-sm-10 col-md-10" value="<#if format?default("") != "" && value?is_date>${(value?string(format))!}<#else>${value!}</#if>" />
			<#if nullable?default("true") == "false"><i class='ace-icon orange fa fa-star'></i></#if>
			</span>
		</div>
	</div>
</#macro>

<#macro selectDiv id="" displayName="" value=""  readonly="false" mcodeId="">
<div class="form-group">
	<label class="col-md-3 control-label no-padding-right" for="${id!}"> <#if displayName == "">${(columnInfo[id]["displayName"])!""}<#else>${displayName!}</#if> </label>
	<div class="col-md-9">
		<select id="${id!}" class="form-control col-md-10 col-sm-10 col-xs-10">
			<#if mcodeId == "">
			${mcodeSetting.getMcodeSelect((columnInfo[id]["mcodeId"])!"", value?string, "1")}
			<#else>
			${mcodeSetting.getMcodeSelect(mcodeId, value?string, "1")}
			</#if>
		</select>
	</div>
</div>
</#macro>
