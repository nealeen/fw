<#macro commonWeb title="">

<#if showFramework?default(false)>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>${title!}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		
		
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${webUrl}/static/ace/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${webUrl}/static/ace/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${webUrl}/static/ace/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="${webUrl}/static/ace/js/bootstrap.js"></script>
		
		<link rel="stylesheet" href="${webUrl}/static/ext/layout-fw.css" />

		<!--[if !IE]> -->
		<link rel="stylesheet" href="${webUrl}/static/ace/css/pace.css" />
		<script data-pace-options='{ "ajax": true, "document": true, "eventLag": false, "elements": false }' src="${webUrl}/static/ace/js/pace.js"></script>
		<!-- <![endif]-->
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${webUrl}/static/ace/css/bootstrap.css" />
		<link rel="stylesheet" href="${webUrl}/static/ace/css/font-awesome.css" />
		<!-- text fonts -->
		<link rel="stylesheet" href="${webUrl}/static/ace/css/ace-fonts.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${webUrl}/static/ace/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${webUrl}/static/ace/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${webUrl}/static/ace/css/ace-ie.css" />
		<![endif]-->
		<!-- ace settings handler -->
		<script src="${webUrl}/static/ace/js/ace-extra.js"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lte IE 8]>
		<script src="${webUrl}/static/ace/js/html5shiv.js"></script>
		<script src="${webUrl}/static/ace/js/respond.js"></script>
		<![endif]-->
		
		
		<link rel="stylesheet" href="${webUrl}/static/ace/css/jquery-ui.css" />
		<link rel="stylesheet" href="${webUrl}/static/ace/css/datepicker.css" />
		<script src="${webUrl}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
		<!-- ace scripts -->
		<script src="${webUrl}/static/ace/js/ace/elements.scroller.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.colorpicker.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.fileinput.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.typeahead.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.wysiwyg.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.spinner.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.treeview.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.wizard.js"></script>
		<script src="${webUrl}/static/ace/js/ace/elements.aside.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.ajax-content.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.touch-drag.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.sidebar.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.submenu-hover.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.widget-box.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.settings.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.settings-rtl.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.settings-skin.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.widget-on-reload.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.searchbox-autocomplete.js"></script>
		
		<script src="${webUrl}/static/sweetalert/sweetalert.min.js"></script>
		<link rel="stylesheet" href="${webUrl}/static/sweetalert/sweetalert.css" />
		
		<script src="${webUrl}/static/layer/layer.js"></script>
		<script src="${webUrl}/static/layer/extend/layer.ext.js"></script>
		<link rel="stylesheet" href="${webUrl}/static/layer/skin/layer.css" />
		<link rel="stylesheet" href="${webUrl}/static/layer/skin/layer.ext.css" />
		
		<script src="${webUrl}/static/js/tool.js"></script>
	</head>
</#if>
<body>		
		<#nested />
</body>
</html>
</#macro>

<#macro putSubSystem subSystemDto>
<#--子系统-->
<#if subSystemDto.subSystemDtos?size lte 0>
	<#--有模块的子系统-->
	<#if subSystemDto.modelDtos?size gt 0>
	<li class="">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa fa-folder"></i>
			<span class="menu-text">
				${subSystemDto.subSystem.name!}
				<span class="badge badge-primary">${subSystemDto.modelDtos?size}</span>
			</span>
			<b class="arrow fa fa-angle-down"></b>
		</a>
		<b class="arrow"></b>
		<ul class="submenu">
			<@putModels subSystemDto />
		</ul>
	</li>
	<#else>
	<#--没有模块的子系统-->
	<li class="">
		<a data-url="${subSystemDto.subSystem.url!}" href="#${subSystemDto.subSystem.url!}">
			<i class="menu-icon fa fa-fire"></i>
			<span class="menu-text"> ${subSystemDto.subSystem.name!} </span>
		</a>
		<b class="arrow"></b>
	</li>
	</#if>
<#else>
<#--子系统目录-->
<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-folder-o"></i>
		<span class="menu-text">
			${subSystemDto.subSystem.name!}
			<span class="badge badge-primary">${subSystemDto.subSystemDtos?size}</span>
		</span>
		<b class="arrow fa fa-angle-down"></b>
	</a>
	<b class="arrow"></b>
	<ul class="submenu">
		<#list subSystemDto.subSystemDtos as subSystem2>
		<@putSubSystem subSystem2 />
		</#list>
	</ul>
</li>
</#if>
</#macro>

<#macro putModel modelDto>
<li class="">
	<a data-url="${modelDto.model.url!}" href="#${modelDto.model.url!}">
		<i class="menu-icon fa fa-book"></i>
		<span class="menu-text"> ${modelDto.model.name!} </span>
	</a>
	<b class="arrow"></b>
</li>

</#macro>

<#macro putModels subSystem>
<#list subSystem.modelDtos as modelDto>
<#if modelDto.subModelDtos?size gt 0>
<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-hdd-o"></i>
		<span class="menu-text">
			${modelDto.model.name!}
			<span class="badge badge-primary">${modelDto.subModelDtos?size}</span>
		</span>
		<b class="arrow fa fa-angle-down"></b>
	</a>
	<b class="arrow"></b>
	<ul class="submenu">
		<#list modelDto.subModelDtos as model2>
		<@putModel model2 />
		</#list>
	</ul>
</li>
<#else>
<li class="">
	<a data-url="${modelDto.model.url!}" href="#${modelDto.model.url!}">
		<i class="menu-icon fa fa-book"></i>
		<span class="menu-text"> ${modelDto.model.name!} </span>
	</a>
	<b class="arrow"></b>
</li>
</#if>
</#list>
</#macro>
