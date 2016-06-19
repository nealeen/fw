package net.zdsoft.basedata.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.basedata.common.service.DeptService;
import net.zdsoft.basedata.common.service.McodeDetailService;
import net.zdsoft.basedata.common.service.TeacherService;
import net.zdsoft.basedata.common.service.UnitService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.dto.TeacherDto;
import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.basedata.entity.McodeDetail;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.action.BaseAction;
import net.zdsoft.framework.annotation.ControllerInfo;
import net.zdsoft.framework.config.ControllerException;
import net.zdsoft.framework.config.Session;
import net.zdsoft.framework.echarts.entity.sub.Series;
import net.zdsoft.framework.entity.Json;
import net.zdsoft.framework.entity.LoginInfo;
import net.zdsoft.framework.entity.Specifications;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.ToolUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/basedata")
public class TeacherAction extends BaseAction {

	@Resource
	private TeacherService teacherService;

	@Resource
	private DeptService deptService;

	@Resource
	private UserService userService;

	@Resource
	private McodeDetailService mcodeDetailService;

	@Resource
	private UnitService unitService;

	@ResponseBody
	@RequestMapping("/teacher/{id}/update")
	@ControllerInfo("更新教师，ID: {id}")
	public String doUpdate(@PathVariable String id, @RequestBody TeacherDto dto, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {

		if (!StringUtils.equals(id, dto.getTeacher().getId())) {
			throw new ControllerException("数据主键不匹配！");
		}
		Teacher teacherO = teacherService.findOne(id);
		ToolUtils.copyProperties(dto.getTeacher(), teacherO, true);
		try {
			teacherService.save(teacherO);
		} catch (Exception e) {
			throw new ControllerException(e.getMessage());
		}
		return returnSuccess("修改成功！");
	}

	@ResponseBody
	@RequestMapping("/teacher/save")
	@ControllerInfo("保存新增教师")
	public String doAddSave(@RequestBody TeacherDto dto, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = dto.getTeacher();
		try {
			teacher.setId(ToolUtils.createUuid());
			teacherService.save(teacher);
		} catch (Exception e) {
			throw new ControllerException(e.getMessage());
		}

		return returnSuccess("新增成功！");
	}

	@RequestMapping("/teacher/{id}/detail")
	@ControllerInfo("查看教师，ID：{id}")
	public String showTeacherDetail(@PathVariable String id, ModelMap map) {
		TeacherDto dto = new TeacherDto();
		Teacher teacher = teacherService.findOne(id);
		String unitId = teacher.getUnitid();
		if (StringUtils.isNotBlank(unitId)) {
			dto.setUnit(unitService.findOne(unitId));
		}
		String deptId = teacher.getDeptId();
		if (StringUtils.isNotBlank(deptId)) {
			dto.setDept(deptService.findOne(deptId));
		}
		dto.setTeacher(teacher);
		map.put("dto", dto);
		map.put("fields", ToolUtils.getEntityFiledNames(Teacher.class));
		Map<String, Map<String, Object>> columnInfo = ToolUtils.getColumnInfos(Teacher.class);
		map.put("columnInfo", columnInfo);
		return "/basedata/teacher/teacherDetail.ftl";
	}

	@RequestMapping("/teacher/add")
	@ControllerInfo("新增教师")
	public String showTeacherDetailAdd(ModelMap map, HttpSession httpSession) {
		TeacherDto dto = new TeacherDto();
		Teacher teacher = new Teacher();
		LoginInfo loginInfo = getLoginInfo(httpSession);
		String unitId = loginInfo.getUnitId();
		if (StringUtils.isNotBlank(unitId)) {
			dto.setUnit(unitService.findOne(unitId));
		}
		String deptId = loginInfo.getDeptId();
		if (StringUtils.isNotBlank(deptId)) {
			dto.setDept(deptService.findOne(deptId));
		}
		dto.setTeacher(teacher);
		map.put("dto", dto);
		map.put("fields", ToolUtils.getEntityFiledNames(Teacher.class));
		Map<String, Map<String, Object>> columnInfo = ToolUtils.getColumnInfos(Teacher.class);
		map.put("columnInfo", columnInfo);
		return "/basedata/teacher/teacherAdd.ftl";
	}

	@ResponseBody
	@RequestMapping("/unit/{unitId}/statTeachersByDept")
	@ControllerInfo("按部门统计教师情况（echarts）")
	public String doStatTeacherByDept(@PathVariable String unitId) {
		List<Dept> depts = deptService.findByUnitId(unitId);
		List<String> deptNames = new ArrayList<String>();

		List<McodeDetail> xbs = mcodeDetailService.findByMcodeIdIn("DM-XB");
		McodeDetail md = new McodeDetail();
		md.setMcodeContent("总数");
		md.setThisId("");
		xbs.add(md);
		List<String> xbnames = new ArrayList<String>();
		for (McodeDetail md2 : xbs) {
			xbnames.add(md2.getMcodeContent());
		}

		List<Teacher> teachers = teacherService.findByUnitId(unitId);
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		for (Teacher t : teachers) {
			String key = t.getDeptId() + t.getSex();
			Integer count = countMap.get(t.getDeptId() + t.getSex());
			if (count == null)
				count = 0;
			countMap.put(key, ++count);
			key = t.getDeptId();
			count = countMap.get(key);
			if (count == null)
				count = 0;
			countMap.put(key, ++count);
		}
		for (Dept dept : depts) {
			deptNames.add(dept.getDeptName());
		}

		Json bar = new Json();

		Json tooltip = new Json();
		tooltip.put("trigger", "bar");
		tooltip.put("axisPointer", new Json().put("type", "shadow"));
		bar.put("tooltip", tooltip);

		Json title = new Json();
		title.put("text", "单位教师统计(按部门)");
		title.put("subtext", "点击图例刷新教师列表");
		bar.put("title", title);

		bar.put("grid", new Json().putEx("left", "0%").putEx("right", "0%").putEx("bottom", "0%").putEx("containLabel", true));

		Json dataZoom = new Json();
		dataZoom.put("realtime", false);
		dataZoom.put("show", true);
		dataZoom.put("start", 0);
		dataZoom.put("end", 30);
		bar.put("dataZoom", dataZoom);

		Json legend = new Json();
		legend.put("data", xbnames);
		bar.put("legend", legend);

		Json xAxis = new Json();
		xAxis.put("type", "category");
		xAxis.put("data", deptNames);
		bar.put("xAxis", xAxis);

		JSONArray yAxis = JSON.parseArray("[{'type':'value'}]");
		bar.put("yAxis", yAxis);

		List<Json> series = new ArrayList<Json>();
		for (McodeDetail md3 : xbs) {
			Json data = new Json();
			data.put("name", md3.getMcodeContent());
			data.put("type", "bar");

			Series s = new Series();
			s.setName(md3.getMcodeContent());
			s.setType("bar");
			List<Json> ls = new ArrayList<Json>();
			for (Dept dept : depts) {
				Json j = new Json();
				j.put("name", dept.getId() + "," + md3.getThisId());
				j.put("value", countMap.get(dept.getId() + md3.getThisId()) + "");
				ls.add(j);
			}
			data.put("data", ls);
			series.add(data);
		}
		bar.put("series", series);

		Json toolbox = new Json();
		Json feature = new Json();
		feature.put("magicType", "{show: true, type: ['line', 'bar']}");
		feature.put("restore", "{show: true}");
		feature.put("saveAsImage", "{show: true}");
		feature.put("dataView", "{show: true, readOnly: false}");
		feature.put("mark", "{show: true}");
		toolbox.put("feature", feature);
		bar.put("toolbox", toolbox);
		return JSON.toJSONString(bar);
	}

	@ResponseBody
	@RequestMapping("/unit/{unitId}/teachers")
	@ControllerInfo("按单位显示教师列表")
	public String teachers(@PathVariable String unitId, ModelMap map, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) {
		Pagination page = createPagination(request);
		List<Teacher> teachers = teacherService.findAll(new Specifications<Teacher>().addEq("unitId", unitId).getSpecification(), page);
		final Set<String> deptIds = new HashSet<String>();
		for (Teacher t : teachers) {
			deptIds.add(t.getDeptId());
		}
		List<Dept> depts = deptService.findByIdIn(deptIds.toArray(new String[0]));

		Map<String, Dept> deptMap = new HashMap<String, Dept>();
		for (Dept dept : depts) {
			deptMap.put(dept.getId(), dept);
		}
		JSONArray array = new JSONArray();

		for (Teacher t : teachers) {
			array.add(JSON.toJSON(t));
		}
		JSONObject on = new JSONObject();
		if (page.getMaxPageIndex() < page.getPageIndex()) {
			page.setPageIndex(1);
		}
		on.put("rows", array);
		on.put("records", page.getMaxRowCount());
		on.put("page", page.getPageIndex());
		on.put("total", page.getMaxPageIndex());
		return on.toString();
	}

	@ResponseBody
	@RequestMapping("/teacher/{id}/delete")
	@ControllerInfo("删除教师")
	public String doDelete(@PathVariable String id, ModelMap map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<User> users = userService.findByOwnerId(id);
		if (CollectionUtils.isNotEmpty(users)) {
			throw new ControllerException("已经存在关联用户，不能删除！");
		}
		teacherService.removeOne(id);
		return "删除成功！";
	}

	@RequestMapping("/teacher")
	@ControllerInfo("教师管理首页")
	public String index(String sex, ModelMap map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		LoginInfo loginInfo = Session.get(session.getId()).getLoginInfo();
		String unitId = loginInfo.getUnitId();
		map.put("unitId", unitId);
		return "/basedata/teacher/teacherIndex.ftl";
	}

	@ResponseBody
	@RequestMapping("/dept/{deptId}/teachers")
	@ControllerInfo("检索部门内教师")
	public String findByDeptId(@PathVariable String deptId, Integer sex, ModelMap map, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		Pagination page = createPagination(request);
		Specifications<Teacher> ss = new Specifications<Teacher>().addEq("deptId", deptId);
		if (sex != null)
			ss.addEq("sex", sex);
		List<Teacher> teachers = teacherService.findAll(ss.getSpecification(), page);
		JSONArray array = new JSONArray();
		for (Teacher t : teachers) {
			array.add(JSON.toJSON(t));
		}
		JSONObject on = new JSONObject();
		on.put("rows", array);
		on.put("records", page.getMaxRowCount());
		on.put("page", page.getPageIndex());
		on.put("total", page.getMaxPageIndex());
		return on.toJSONString();
	}
}
