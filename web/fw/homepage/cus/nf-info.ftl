							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${loginInfoDto.user.photoPath!}" alt="" />
								<span class="user-info">
									<small>${welcome!"你好"},</small>
									${loginInfoDto.user.realName!}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										系统设置
									</a>
								</li>

								<li>
									<a href="#page/profile">
										<i class="ace-icon fa fa-user"></i>
										个人配置
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${request.contextPath}/homepage/logout">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
