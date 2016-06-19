<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>${platformName!}</title>
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
		
		<link rel="stylesheet" href="${webUrl}/static/ace/css/jquery-ui.css" />
		
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
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lte IE 8]>
		<script src="${webUrl}/static/ace/js/html5shiv.js"></script>
		<script src="${webUrl}/static/ace/js/respond.js"></script>
		<![endif]-->
		
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							${platformName!}
						</small>
					</a>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<#if showStat!true>
						<#--待办-->
						<li class="red" id="nf-stat" />
						</#if>
						<#if showTodo!false>
						<#--待办-->
						<li class="grey" id="nf-todo" />
						</#if>
						
						<#if showNotice!false>
						<#--通知-->
						<li class="purple" id="nf-notice" />
						</#if>
						
						<#if showMessage!false>
						<#--消息-->
						<li class="green" id="nf-message" />
						</#if>
						
						
						<#if showInfo!false>
						<#--个人-->
						<li class="light-blue" id="nf-info" />
						</#if>
						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>
						<span class="btn btn-info"></span>
						<span class="btn btn-warning"></span>
						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->
				
				<ul class="nav nav-list" id="nf-navList" >
				</ul>
				
				<#if showSidebarCollapse!false>
				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>
				</#if>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<#if showBreadcrumb!false>
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">首页</a>
							</li>
						</ul><!-- /.breadcrumb -->
						</#if>

						<#if showSearch!false>
						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
						</#if>
						<!-- /section:basics/content.searchbox -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-content-area" data-ajax-content="true">
							<!-- ajax content goes here -->
						</div><!-- /.page-content-area -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">${footer!}</span>
							${subfooter!}
						</span>
					</div>
					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		
		<link rel="stylesheet" href="${webUrl}/static/ace/css/datepicker.css" />
		<script src="${webUrl}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
		<!-- ace scripts -->
		<script src="${webUrl}/static/ace/js/ace/elements.scroller.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.ajax-content.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.sidebar.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.submenu-hover.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.widget-box.js"></script>
		<script src="${webUrl}/static/ace/js/ace/ace.widget-on-reload.js"></script>
		
		<script>
		$("#nf-info").load("${request.contextPath}/homepage/cus/info/list");
		$("#nf-message").load("${request.contextPath}/homepage/cus/nf-message");
		$("#nf-notice").load("${request.contextPath}/homepage/cus/nf-notice");
		$("#nf-todo").load("${request.contextPath}/homepage/cus/nf-todo");
		$("#nf-stat").load("${request.contextPath}/homepage/cus/nf-stat");
		$("#nf-navList").load("${request.contextPath}/homepage/cus/nav/list");
		<#if showSetting!false>
		$("#ace-settings-container").load("${request.contextPath}/homepage/cus/nf-setting");
		</#if>
		</script>
		
		<script src="${webUrl}/static/sweetalert/sweetalert-dev.js"></script>
		<link rel="stylesheet" href="${webUrl}/static/sweetalert/sweetalert.css" />
		
		<script src="${webUrl}/static/layer/layer.js"></script>
		<script src="${webUrl}/static/layer/extend/layer.ext.js"></script>
		<link rel="stylesheet" href="${webUrl}/static/layer/skin/layer.css" />
		<link rel="stylesheet" href="${webUrl}/static/layer/skin/layer.ext.css" />
		
		<script src="${webUrl}/static/js/tool.js"></script>
		<script src="${webUrl}/static/require/require.min.js"></script>
	</body>
</html>
