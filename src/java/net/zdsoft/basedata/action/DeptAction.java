package net.zdsoft.basedata.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.basedata.common.service.DeptService;
import net.zdsoft.basedata.common.service.McodeDetailService;
import net.zdsoft.basedata.common.service.TeacherService;
import net.zdsoft.basedata.common.service.UnitService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.dto.DeptDto;
import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.action.BaseAction;
import net.zdsoft.framework.annotation.ControllerInfo;
import net.zdsoft.framework.entity.Constant;
import net.zdsoft.framework.entity.Json;
import net.zdsoft.framework.entity.LoginInfo;
import net.zdsoft.framework.utils.ToolUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/basedata")
public class DeptAction extends BaseAction {

	private static Logger log = Logger.getLogger(DeptAction.class);

	@Resource
	private UserService userService;

	@Resource
	private DeptService deptService;
	@Resource
	private UnitService unitService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private McodeDetailService mcodeDetailService;

	@RequestMapping("/dept")
	@ControllerInfo("部门管理首页")
	public String index(String sex, ModelMap map, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		LoginInfo loginInfo = getLoginInfo(httpSession);
		String unitId = loginInfo.getUnitId();
		map.put("unitId", unitId);
		return "/basedata/dept/deptIndex.ftl";
	}

	@ResponseBody
	@RequestMapping(value = "/dept/{id}/delete", method = RequestMethod.DELETE)
	@ControllerInfo("删除部门")
	public String doDelete(ModelMap map, HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {

		List<Dept> depts = deptService.findByParentId(id);
		if(CollectionUtils.isNotEmpty(depts))
			return returnError("此部门存在下级部门，不能删除！");
		List<User> users = userService.findByDeptId(id);
		if (CollectionUtils.isNotEmpty(users)) {
			return returnError("此部门下存在用户信息，不能删除！");
		}
		deptService.delete(id);
		return returnSuccess();
	}

	@ResponseBody
	@RequestMapping("/unit/{unitId}/depts/ztree")
	@ControllerInfo("显示部门树")
	public String depts4Ztree(@PathVariable String unitId, ModelMap map, HttpServletRequest request, HttpServletResponse response,
			HttpSession sesion) {
		List<Dept> depts = deptService.findByUnitId(unitId);
		JSONArray array = new JSONArray();
		Unit unit = unitService.findOne(unitId);
		if(unit != null){
			Json json = new Json();
			json.putEx("id", unit.getId()).putEx("pId", "").putEx("name", unit.getUnitName()).putEx("type", "unit");
			json.put("title", unit.getUnitName());
			json.put("open", true);
			array.add(json);
		}
		for (Dept dept : depts) {
			Json json = new Json();
			if(StringUtils.equals(Constant.GUID_ZERO, dept.getParentId())){
				json.put("pId", unitId);
			}
			else{
				json.put("pId", dept.getParentId());
			}
			json.put("type", "dept");
			json.putEx("id", dept.getId()).putEx("name", dept.getDeptName())
					.putEx("title", dept.getDeptName());
			array.add(json);
		}
		return returnSuccess(JSONUtils.toJSONString(array));
	}

	@ResponseBody
	@RequestMapping("/unit/{unitId}/depts")
	@ControllerInfo("列出单位内部门")
	public String depts(@PathVariable String unitId, ModelMap map, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) {
		List<Dept> depts = deptService.findByUnitId(unitId);
		return createJqGridJson(depts);
	}

	@ResponseBody
	@RequestMapping("/dept/{parentId}/depts")
	@ControllerInfo("列出{parentId}下级部门")
	public String showSubdepts(@PathVariable String parentId, ModelMap map, HttpServletRequest request, HttpServletResponse response,
			HttpSession sesion) {
		List<Dept> depts = deptService.findByParentId(parentId);
		return createJqGridJson(depts);
	}

	@ResponseBody
	@RequestMapping("/dept/update")
	@ControllerInfo("更新部门信息")
	public String doUpdate(@RequestBody DeptDto deptDto, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		try {
			Dept dept = deptDto.getDept();
			String id = dept.getId();
			Dept deptOri = deptService.findOne(id);
			ToolUtils.copyProperties(dept, deptOri, true);
			deptService.save(deptOri);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("", "保存部门失败，请联系管理员！", e.getMessage());
		}
		return returnSuccess();
	}

	@ResponseBody
	@RequestMapping("/dept/save")
	@ControllerInfo("保存新增部门")
	public String doSave(@RequestBody DeptDto deptDto, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		try {
			Dept dept = deptDto.getDept();
			dept.setId(ToolUtils.createUuid());
			dept.setInstituteId(Constant.GUID_ONE);
			deptService.save(dept);
			log.info(Json.toJSONString(dept));
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("", "保存部门失败，请联系管理员！", e.getMessage());
		}
		return returnSuccess();
	}

	@RequestMapping("/dept/{id}/detail")
	@ControllerInfo("显示{id}部门明细")
	public String showDept(@PathVariable String id, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Dept dept = deptService.findOne(id);
		DeptDto dto = new DeptDto();
		dto.setDept(dept);
		map.put("dto", dto);
		List<String> fields = ToolUtils.getEntityFiledNames(Dept.class);
		map.put("fields", fields);
		Map<String, Map<String, Object>> columnInfo = ToolUtils.getColumnInfos(Dept.class);
		map.put("columnInfo", columnInfo);
		return "/basedata/dept/deptDetail.ftl";
	}

	@RequestMapping("/dept/add")
	@ControllerInfo("新增部门")
	public String doAdd(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Dept dept = new Dept();
		DeptDto dto = new DeptDto();
		dto.setDept(dept);
		LoginInfo info = getLoginInfo(request.getSession());
		String unitId = info.getUnitId();
		dept.setParentId(info.getDeptId());
		dept.setUnitId(unitId);
		dept.setIsDeleted(Constant.IS_DELETED_FALSE);
		map.put("dto", dto);
		map.put("fields", ToolUtils.getEntityFiledNames(Dept.class));
		Map<String, Map<String, Object>> columnInfo = ToolUtils.getColumnInfos(Dept.class);
		columnInfo.get("unitId").put("hide", false);
		map.put("columnInfo", columnInfo);
		return "/basedata/dept/deptAdd.ftl";
	}
}
