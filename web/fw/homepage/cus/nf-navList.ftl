<#import "/fw/macro/webmacro.ftl" as webmacro>
			
<li class="">
	<a data-url="${request.contextPath}/basedata/teacher" href="#${request.contextPath}/basedata/teacher">
		<i class="menu-icon fa fa-asterisk"></i>
		<span class="menu-text"> 【教师管理】 </span>
	</a> 
	<b class="arrow"></b>
</li>
					
<li class="">
	<a data-url="${request.contextPath}/basedata/unit" href="#${request.contextPath}/basedata/unit">
		<i class="menu-icon fa fa-asterisk"></i>
		<span class="menu-text"> 【单位管理】 </span>
	</a> 
	<b class="arrow"></b>
</li>
<li class="">
	<a data-url="${request.contextPath}/homepage/content/typography" href="#${request.contextPath}/homepage/content/typography">
		<i class="menu-icon fa fa-asterisk"></i>
		<span class="menu-text"> 【typography】 </span>
	</a>
	<b class="arrow"></b>
</li>

<#list subSystemDtos as subsystemDto>
	<@webmacro.putSubSystem subsystemDto />
</#list>


<li class="">
	<a data-url="${request.contextPath}/homepage/content/index" href="#${request.contextPath}/homepage/content/index">
		<i class="menu-icon fa fa-tachometer"></i>
		<span class="menu-text"> Dashboard </span>
	</a>
	<b class="arrow"></b>
</li>

<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-desktop"></i>
		<span class="menu-text">
			UI &amp; Elements
		</span>

		<b class="arrow fa fa-angle-down"></b>
	</a>

	<b class="arrow"></b>

	<ul class="submenu">
		<li class="">
			<a data-url="${request.contextPath}/homepage/content/typography" href="#${request.contextPath}/homepage/content/typography">
				<i class="menu-icon fa fa-caret-right"></i>
				Typography
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/elements" href="#${request.contextPath}/homepage/content/elements">
				<i class="menu-icon fa fa-caret-right"></i>
				Elements
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/buttons" href="#${request.contextPath}/homepage/content/buttons">
				<i class="menu-icon fa fa-caret-right"></i>
				Buttons &amp; Icons
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/content-slider" href="#${request.contextPath}/homepage/content/content-slider">
				<i class="menu-icon fa fa-caret-right"></i>
				Content Sliders
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/treeview" href="#${request.contextPath}/homepage/content/treeview">
				<i class="menu-icon fa fa-caret-right"></i>
				Treeview
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/nestable-list" href="#${request.contextPath}/homepage/content/nestable-list">
				<i class="menu-icon fa fa-caret-right"></i>
				Nestable Lists
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-caret-right"></i>

				Three Level Menu
				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="#">
						<i class="menu-icon fa fa-leaf green"></i>
						Item #1
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-pencil orange"></i>

						4th level
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-plus purple"></i>
								Add Product
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-eye pink"></i>
								View Products
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>
			</ul>
		</li>
	</ul>
</li>

<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-list"></i>
		<span class="menu-text"> Tables </span>

		<b class="arrow fa fa-angle-down"></b>
	</a>

	<b class="arrow"></b>

	<ul class="submenu">
		<li class="">
			<a data-url="${request.contextPath}/homepage/content/tables" href="#${request.contextPath}/homepage/content/tables">
				<i class="menu-icon fa fa-caret-right"></i>
				Simple &amp; Dynamic
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/jqgrid" href="#${request.contextPath}/homepage/content/jqgrid">
				<i class="menu-icon fa fa-caret-right"></i>
				jqGrid plugin
			</a>

			<b class="arrow"></b>
		</li>
	</ul>
</li>

<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-pencil-square-o"></i>
		<span class="menu-text"> Forms </span>

		<b class="arrow fa fa-angle-down"></b>
	</a>

	<b class="arrow"></b>

	<ul class="submenu">
		<li class="">
			<a data-url="${request.contextPath}/homepage/content/form-elements" href="#${request.contextPath}/homepage/content/form-elements">
				<i class="menu-icon fa fa-caret-right"></i>
				Form Elements
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/form-elements-2" href="#${request.contextPath}/homepage/content/form-elements-2">
				<i class="menu-icon fa fa-caret-right"></i>
				Form Elements 2
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/form-wizard" href="#${request.contextPath}/homepage/content/form-wizard">
				<i class="menu-icon fa fa-caret-right"></i>
				Wizard &amp; Validation
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/wysiwyg" href="#${request.contextPath}/homepage/content/wysiwyg">
				<i class="menu-icon fa fa-caret-right"></i>
				Wysiwyg &amp; Markdown
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/dropzone" href="#${request.contextPath}/homepage/content/dropzone">
				<i class="menu-icon fa fa-caret-right"></i>
				Dropzone File Upload
			</a>

			<b class="arrow"></b>
		</li>
	</ul>
</li>

<li class="">
	<a data-url="${request.contextPath}/homepage/content/widgets" href="#${request.contextPath}/homepage/content/widgets">
		<i class="menu-icon fa fa-list-alt"></i>
		<span class="menu-text"> Widgets </span>
	</a>

	<b class="arrow"></b>
</li>

<li class="">
	<a data-url="${request.contextPath}/homepage/content/calendar" href="#${request.contextPath}/homepage/content/calendar">
		<i class="menu-icon fa fa-calendar"></i>

		<span class="menu-text">
			Calendar

			<!-- #section:basics/sidebar.layout.badge -->
			<span class="badge badge-transparent tooltip-error" title="2 Important Events">
				<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
			</span>

			<!-- /section:basics/sidebar.layout.badge -->
		</span>
	</a>

	<b class="arrow"></b>
</li>

<li class="">
	<a data-url="${request.contextPath}/homepage/content/gallery" href="#${request.contextPath}/homepage/content/gallery">
		<i class="menu-icon fa fa-picture-o"></i>
		<span class="menu-text"> Gallery </span>
	</a>

	<b class="arrow"></b>
</li>

<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-tag"></i>
		<span class="menu-text"> More Pages </span>

		<b class="arrow fa fa-angle-down"></b>
	</a>

	<b class="arrow"></b>

	<ul class="submenu">
		<li class="">
			<a data-url="${request.contextPath}/homepage/content/profile" href="#${request.contextPath}/homepage/content/profile">
				<i class="menu-icon fa fa-caret-right"></i>
				User Profile
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/inbox" href="#${request.contextPath}/homepage/content/inbox">
				<i class="menu-icon fa fa-caret-right"></i>
				Inbox
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/pricing" href="#${request.contextPath}/homepage/content/pricing">
				<i class="menu-icon fa fa-caret-right"></i>
				Pricing Tables
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/invoice" href="#${request.contextPath}/homepage/content/invoice">
				<i class="menu-icon fa fa-caret-right"></i>
				Invoice
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/timeline" href="#${request.contextPath}/homepage/content/timeline">
				<i class="menu-icon fa fa-caret-right"></i>
				Timeline
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/email" href="#${request.contextPath}/homepage/content/email">
				<i class="menu-icon fa fa-caret-right"></i>
				Email Templates
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/login" href="#${request.contextPath}/homepage/content/login">
				<i class="menu-icon fa fa-caret-right"></i>
				Login &amp; Register
			</a>

			<b class="arrow"></b>
		</li>
	</ul>
</li>

<li class="">
	<a href="#" class="dropdown-toggle">
		<i class="menu-icon fa fa-file-o"></i>

		<span class="menu-text">
			Other Pages

			<!-- #section:basics/sidebar.layout.badge -->
			<span class="badge badge-primary">5</span>

			<!-- /section:basics/sidebar.layout.badge -->
		</span>

		<b class="arrow fa fa-angle-down"></b>
	</a>

	<b class="arrow"></b>

	<ul class="submenu">
		<li class="">
			<a data-url="${request.contextPath}/homepage/content/faq" href="#${request.contextPath}/homepage/content/faq">
				<i class="menu-icon fa fa-caret-right"></i>
				FAQ
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/error-404" href="#${request.contextPath}/homepage/content/error-404">
				<i class="menu-icon fa fa-caret-right"></i>
				Error 404
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/error-500" href="#${request.contextPath}/homepage/content/error-500">
				<i class="menu-icon fa fa-caret-right"></i>
				Error 500
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/grid" href="#${request.contextPath}/homepage/content/grid">
				<i class="menu-icon fa fa-caret-right"></i>
				Grid
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a data-url="${request.contextPath}/homepage/content/blank" href="#${request.contextPath}/homepage/content/blank">
				<i class="menu-icon fa fa-caret-right"></i>
				Blank Page
			</a>

			<b class="arrow"></b>
		</li>
	</ul>
</li>