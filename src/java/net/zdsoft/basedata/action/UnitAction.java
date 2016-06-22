package net.zdsoft.basedata.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.basedata.common.service.DeptService;
import net.zdsoft.basedata.common.service.UnitService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.action.BaseAction;
import net.zdsoft.framework.utils.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/basedata")
public class UnitAction extends BaseAction {

    @Resource
    private UnitService unitService;

    @Resource
    private DeptService deptService;

    @Resource
    private UserService userService;

    @RequestMapping("/unit")
    public String unitIndex(ModelMap map) {
        return "/basedata/unit/unitindex.ftl";
    }

    @RequestMapping(value = "/unit/{id}/show", method = RequestMethod.GET)
    public String unit(@PathVariable String id, ModelMap map) {
        User user = userService.findOne(id);
        map.put("user", user);
        return "/basedata/unit/unitdetail.ftl";
    }

    @ResponseBody
    @RequestMapping(value = "/unit/{id}/delete", method = RequestMethod.DELETE)
    public String doDeleteUnit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response, @PathVariable String id) {
        List<Dept> depts = deptService.findByUnitId(id);
        if (CollectionUtils.isNotEmpty(depts)) {
            throw new RuntimeException("此单位存在部门信息，不能删除，请先删除部门数据！");
        }
        unitService.delete(id);
        return "删除成功！";
    }

    @ResponseBody
    @RequestMapping("/unit/all")
    public String units(ModelMap map, HttpServletRequest request, HttpServletResponse response,
            HttpSession sesion) {
        Pagination page = createPagination(request);
        Unit topUnit = unitService.findTopUnit();
        List<User> users = userService.findByUnitId(topUnit.getId(), page);
        if (CollectionUtils.isEmpty(users)) {
            return "";
        }
        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parse = new SpelExpressionParser();
        List<User> uts = new ArrayList<User>();
        // ObjectMapper mapper = new ObjectMapper();
        // ArrayNode an = mapper.createArrayNode();
        JSONArray array = new JSONArray();
        for (User user : users) {
            // uts.add(user);
            array.add(JSON.toJSON(user));
        }
        // uts = applyJqGridSort(uts, syncParameters(request));
        JSONObject on = new JSONObject();
        on.put("rows", array);
        on.put("records", page.getMaxRowCount());
        on.put("page", syncParameters(request).get("page"));
        on.put("total", page.getMaxPageIndex());
        return on.toString();
    }
}
