
<title>Form Elements - Ace Admin</title>

<link rel="stylesheet" href="${webUrl}/static/ace/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/chosen.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/datepicker.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/bootstrap-timepicker.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/daterangepicker.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/colorpicker.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="${webUrl}/static/ace/css/jquery.gritter.css" />


<div id="deptDetail" indexValue="" class="row" style="margin-top:10px;">
	<input type="hidden" id="id" value="${dept.id!}">
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="deptCode"> 部门编号 </label>

				<div class="col-sm-9">
					<input type="text" id="deptCode" placeholder="部门编号" class="col-xs-10 col-sm-5" value="${dept.deptCode!}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="deptName"> 部门名称 </label>

				<div class="col-sm-9">
					<input type="text" id="deptName" placeholder="部门名称" class="col-xs-10 col-sm-5" value="${dept.deptName!}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="deptShortName"> 部门简称 </label>

				<div class="col-sm-9">
					<input type="text" id="deptShortName" placeholder="部门简称" class="col-xs-10 col-sm-5" value="${dept.deptShortName!}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="deptType"> 部门类型 </label>

				<div class="col-sm-9">
					<select id="deptType" class="col-xs-10 col-sm-5">
						${mcodeSetting.getMcodeSelect("DM-JYBZ", "${dept.deptType}", "1")}
					</select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 所属单位 </label>

				<div class="col-sm-9">
					<input readonly="" type="text" class="col-xs-10 col-sm-5" id="form-input-readonly" value="${dept.unit.unitName!}" />
				</div>
			</div>

			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info" id="dept-commit" type="button" data-rel="tooltip" title="保存并关闭本窗口">
						<i class="ace-icon fa fa-check bigger-110"></i>
						确定
					</button>
					<button class="btn" id="dept-close" type="button" data-rel="tooltip" title="不保存直接关闭">
						<i class="ace-icon fa fa-times bigger-110"></i>
						关闭
					</button>
				</div>
			</div>
		</form>

</div><!-- /.row -->

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${webUrl}/static/ace/js/excanvas.js"></script>
<![endif]-->

<script type="text/javascript">
	var scripts = [null,"${webUrl}/static/ace/js/jquery-ui.custom.js","${webUrl}/static/ace/js/jquery.ui.touch-punch.js","${webUrl}/static/ace/js/chosen.jquery.js","${webUrl}/static/ace/js/fuelux/fuelux.spinner.js","${webUrl}/static/ace/js/date-time/bootstrap-datepicker.js","${webUrl}/static/ace/js/date-time/bootstrap-timepicker.js","${webUrl}/static/ace/js/date-time/moment.js","${webUrl}/static/ace/js/date-time/daterangepicker.js","${webUrl}/static/ace/js/date-time/bootstrap-datetimepicker.js","${webUrl}/static/ace/js/bootstrap-colorpicker.js","${webUrl}/static/ace/js/jquery.knob.js","${webUrl}/static/ace/js/jquery.autosize.js","${webUrl}/static/ace/js/jquery.inputlimiter.1.3.1.js","${webUrl}/static/ace/js/jquery.maskedinput.js","${webUrl}/static/ace/js/bootstrap-tag.js", null]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	  //inline scripts related to this page
		 jQuery(function($) {
		 $("#dept-close").on("click", function(){
		 	layer.closeAll(); 
		 });
		 $("#dept-commit").on("click", function(){
		 	$.ajax({
			    url:'${request.contextPath}/basedata/dept/update',
			    data: dealValue('#deptDetail'),  
			    type:'post',  
			    cache:false,  
			    contentType:'application/json',
			    beforeSend:function(XMLHttpRequest){
			    },  
			    success:function(data) { 
			    	layer.closeAll(); 
			    	swal({title: "操作成功!",
	    			text: data,type: "success",showConfirmButton: true,confirmButtonText: "是"}, function(){
	    				$("#dept-list").trigger("reloadGrid");
	    			});
			     },  
			     error : function(XMLHttpRequest, textStatus, errorThrown) {  
			     	var text = syncText(XMLHttpRequest);
	     			swal({title: "操作失败!",text: text, type:"error",showConfirmButton: true});
			    }  
			});
		 });
	
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				})
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				})
			});
	
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	
		$('[data-rel=tooltip]').tooltip();
		$('[data-rel=popover]').popover({html:true});
		
		$('textarea[class*=autosize]').autosize({append: "\n"});
		$('textarea.limited').inputlimiter({
			remText: '%n character%s remaining...',
			limitText: 'max allowed : %n.'
		});
	
		$.mask.definitions['~']='[+-]';
		$('.input-mask-date').mask('99/99/9999');
		$('.input-mask-phone').mask('(999) 999-9999');
		$('.input-mask-eyescript').mask('~9.99 ~9.99 999');
		$(".input-mask-product").mask("a*-999-a999",{placeholder:" ",completed:function(){alert("You typed the following: "+this.val());}});
	
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		})
		.next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
	
		//or change it into a date range picker
		$('.input-daterange').datepicker({autoclose:true});
	
	
		$('#timepicker1').timepicker({
			minuteStep: 1,
			showSeconds: true,
			showMeridian: false
		}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		
		$('#date-timepicker1').datetimepicker().next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		
		$(document).one('ajaxloadstart.page', function(e) {
			$('textarea[class*=autosize]').trigger('autosize.destroy');
			$('.limiterBox,.autosizejs').remove();
			$('.daterangepicker.dropdown-menu,.colorpicker.dropdown-menu,.bootstrap-datetimepicker-widget.dropdown-menu').remove();
		});
	
	});
	});
</script>
