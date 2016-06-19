<!DOCTYPE html>
<html lang="en">
	<head>
		<script>
		_webUrl = "${webUrl!}";
		_contextPath = "${request.contextPath!}";
		</script>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Welcome - Ace Admin</title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!--[if !IE]> -->
		<link rel="stylesheet" href="${resourceUrl}/ace/css/pace.css" />

		<script data-pace-options='{ "ajax": true, "document": true, "eventLag": false, "elements": false }' src="${resourceUrl}/ace/js/pace.js"></script>

		<!-- <![endif]-->

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${resourceUrl}/ace/css/bootstrap.css" />
		<link rel="stylesheet" href="${resourceUrl}/ace/css/font-awesome.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${resourceUrl}/ace/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${resourceUrl}/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${resourceUrl}/ace/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${resourceUrl}/ace/css/ace-ie.css" />
		<![endif]-->

		<!-- ace settings handler -->
		<script src="${resourceUrl}/ace/js/ace-extra.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${resourceUrl}/ace/js/html5shiv.js"></script>
		<script src="${resourceUrl}/ace/js/respond.js"></script>
		<![endif]-->
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

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
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>
				
				<#--菜单栏上方快捷方式-->				
				<div class="sidebar-shortcuts" id="nf-sidebarShortcuts">
				</div><!-- #sidebar-shortcuts -->

				<#--左侧菜单栏-->
				<ul class="nav nav-list" id="nf-navList" >
				</ul>

				<ul class="nav nav-list">
					
				</ul><!-- /.nav-list -->

				<#--是否显示收起开关-->
				<#if showSidebarCollapse!false>
				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
				</#if>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

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
						<!-- #section:settings.box -->
						<#if showSetting!false>
						<div class="ace-settings-container" id="ace-settings-container">
							
						</div><!-- /#ace-settings-container -->
						</#if>

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
							<span class="blue bolder">${footer1!}</span>
							${footer2!}
						</span>
						<#--
						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>-->
					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${resourceUrl}/ace/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${resourceUrl}/ace/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${resourceUrl}/ace/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		
		<script>
		$("#nf-info").load("${webUrl}/fw/ace2/html/ajax/cus/nf-info");
		$("#nf-message").load("${webUrl}/fw/ace2/html/ajax/cus/nf-message");
		$("#nf-notice").load("${webUrl}/fw/ace2/html/ajax/cus/nf-notice");
		$("#nf-todo").load("${webUrl}/fw/ace2/html/ajax/cus/nf-todo");
		$("#nf-navList").load("${webUrl}/fw/ace2/html/ajax/cus/nf-navList");
			<#if showSidebarShortcuts!false>
			$("#nf-sidebarShortcuts").load("${webUrl}/fw/ace2/html/ajax/cus/nf-shortcut");
			</#if>
		<#if showSetting!false>
		$("#ace-settings-container").load("${webUrl}/fw/ace2/html/ajax/cus/nf-setting");
		</#if>
		</script>
		
		<script src="${resourceUrl}/ace/js/bootstrap.js"></script>

		<!-- ace scripts -->
		<script src="${resourceUrl}/ace/js/ace/elements.scroller.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.colorpicker.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.fileinput.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.typeahead.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.wysiwyg.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.spinner.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.treeview.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.wizard.js"></script>
		<script src="${resourceUrl}/ace/js/ace/elements.aside.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.ajax-content.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.touch-drag.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.sidebar.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.submenu-hover.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.widget-box.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.settings.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.settings-rtl.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.settings-skin.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.widget-on-reload.js"></script>
		<script src="${resourceUrl}/ace/js/ace/ace.searchbox-autocomplete.js"></script>
	</body>
</html>
