package net.zdsoft.basedata.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.basedata.common.service.TeacherService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.action.BaseAction;
import net.zdsoft.framework.utils.Pagination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basedata")
public class UserAction extends BaseAction {

    @Resource
    private UserService userService;

    @Resource
    private TeacherService teacherService;

    @RequestMapping("/user/{id}/delete")
    @ResponseBody
    public String doDelete(@PathVariable String id, ModelMap map, HttpServletRequest request,
            HttpServletResponse response, HttpSession sesion) {
        Teacher teacher = teacherService.findByUserId(id);
        if (teacher != null) {
            throw new RuntimeException("存在相关教师，不能删除！");
        }
        userService.delete(id);
        return "删除成功！";
    }

    @ResponseBody
    @RequestMapping("/unit/{unitId}/users")
    public String users(@PathVariable String unitId, ModelMap map, HttpServletRequest request,
            HttpServletResponse response, HttpSession sesion) {
        Pagination page = createPagination(request);
        List<User> users = userService.findByUnitId(unitId, page);
        return createJqGridJson(users, page);
    }

}
