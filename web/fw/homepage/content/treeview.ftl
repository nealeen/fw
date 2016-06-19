
<title>Treeview - Ace Admin</title>

<!-- ajax layout which only needs content area -->
<div class="page-header">
	<h1>
		Treeview
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			with selectable items(single &amp; multiple) and custom icons
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<!-- #section:plugins/fuelux.treeview -->
		<div class="row">
			<div class="col-sm-6">
				<div class="widget-box widget-color-blue2">
					<div class="widget-header">
						<h4 class="widget-title lighter smaller">Choose Categories</h4>
					</div>

					<div class="widget-body">
						<div class="widget-main padding-8">
							<ul id="tree1"></ul>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-6">
				<div class="widget-box widget-color-green2">
					<div class="widget-header">
						<h4 class="widget-title lighter smaller">Browse Files</h4>
					</div>

					<div class="widget-body">
						<div class="widget-main padding-8">
							<ul id="tree2"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- /section:plugins/fuelux.treeview -->
		<script type="text/javascript">
			var $assets = "${request.contextPath}/static/ace";//this will be used in fuelux.tree-sampledata.js
		</script>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
	var scripts = [null,"${request.contextPath}/static/ace/js/fuelux/fuelux.tree.js", null]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	  //inline scripts related to this page
		 jQuery(function($){
	});
</script>