package net.zdsoft.framework.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.basedata.common.service.ModelService;
import net.zdsoft.basedata.common.service.SubsystemService;
import net.zdsoft.basedata.common.service.TeacherService;
import net.zdsoft.basedata.common.service.UnitService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.dto.ModelDto;
import net.zdsoft.basedata.dto.SubSystemDto;
import net.zdsoft.basedata.entity.Model;
import net.zdsoft.basedata.entity.SubSystem;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.config.ControllerException;
import net.zdsoft.framework.config.Session;
import net.zdsoft.framework.dto.LoginInfoDto;
import net.zdsoft.framework.entity.LoginInfo;
import net.zdsoft.framework.utils.PWD;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/homepage")
public class HomepageAction extends BaseAction {

    @Resource
    private UserService userService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private UnitService unitService;
    @Resource
    private SubsystemService subsystemService;
    @Resource
    private ModelService modelService;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,
            HttpSession httpSession, ModelMap map) {
        Session session = Session.get(httpSession.getId());
        session.invalidate();
        return "redirect:/homepage/login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,
            HttpSession httpSession, ModelMap map) {
        Session session = Session.get(httpSession.getId());
        if (session.getLoginInfo() != null) {
            return "redirect:/homepage/index";
        }
        map.put("showFramework", true);
        map.put("showOnlyBase", true);
        return "/fw/homepage/loginMain.ftl";
    }

    @RequestMapping("/loginPage")
    public String loginPage(HttpServletRequest request, HttpServletResponse response,
            HttpSession httpSession, ModelMap map) {
        Session session = Session.get(httpSession.getId());
        if (session.getLoginInfo() != null) {
            return "redirect:/homepage/index";
        }
        map.put("showFramework", true);
        return "/fw/homepage/login.ftl";
    }
    
    @RequestMapping("/test")
    public String test() {
        return "/1.ftl";
    }

    @ResponseBody
    @RequestMapping("/loginUser")
    public String loginUser(String username, String password, HttpServletRequest request,
            HttpServletResponse response, HttpSession httpSession, ModelMap map) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ControllerException("username", "找不到此用户！");
        }
        String pwd = user.getPassword();
        if (StringUtils.length(pwd) == 64)
            pwd = PWD.decode(pwd);
        if (StringUtils.equalsIgnoreCase(pwd, password)) {
            Session session = Session.get(httpSession.getId());
            LoginInfo loginInfo = new LoginInfo();
            if (user.getOwnerType() == 2) {
                Teacher teacher = teacherService.findOne(user.getOwnerId());
                if (teacher != null) {
                    loginInfo.setOwnerId(teacher.getId());
                    loginInfo.setDeptId(teacher.getDeptId());
                }
            }
            loginInfo.setOwnerType(user.getOwnerType());
            loginInfo.setUnitId(user.getUnitId());
            loginInfo.setUserId(user.getId());
            session.setLoginInfo(loginInfo);
            return returnSuccess("登陆成功,跳转中……");
        }
        else {
            throw new ControllerException("password", "密码错误！");
        }
    }

    @RequestMapping("/index")
    public String index(ModelMap map, HttpSession httpSession) {
        map.addAttribute("platformName", "基础支撑平台");
        map.addAttribute("showNotice", true);
        map.addAttribute("showMessage", true);
        map.addAttribute("showTodo", true);
        map.addAttribute("showInfo", true);
        map.addAttribute("showSidebarCollapse", true);
        map.addAttribute("showSidebarShortcuts", true);
        map.addAttribute("showBreadcrumb", true);
        map.addAttribute("showSearch", true);
        map.addAttribute("showHeader", false);
        map.addAttribute("showTip", true);
        map.addAttribute("showSetting", true);

        map.addAttribute("tip", "基础支撑平台v1.0新发布，欢迎使用");
        map.addAttribute("platformName", "基础支撑平台");
        map.addAttribute("footer", "Zdsoft");
        map.addAttribute("subfooter", "浙江万朋教育科技股份有限公司");
        return "/fw/homepage/index.ftl";
    }

    @RequestMapping("/cus/info/list")
    public String doInfo(ModelMap map, HttpSession httpSession) {
        Session session = getSession(httpSession);
        LoginInfo loginInfo = session.getLoginInfo();
        LoginInfoDto loginInfoDto = new LoginInfoDto();
        loginInfoDto.setLoginInfo(loginInfo);
        loginInfoDto.setUser(userService.findOne(loginInfo.getUserId()));
        Integer ownerType = loginInfo.getOwnerType();
        if (ownerType == 2) {
            loginInfoDto.setTeacher(teacherService.findOne(loginInfo.getOwnerId()));
        }
        map.put("loginInfoDto", loginInfoDto);

        String welcome = "你好";
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 12) {
            if (hour > 22)
                welcome = "夜深了";
            else if (hour >= 18)
                welcome = "晚上好";
            else
                welcome = "下午好";
        }
        else {
            if (hour < 3)
                welcome = "夜深了";
            else if (hour < 12) {
                welcome = "上午好";
            }
            else
                welcome = "中午好";
        }

        map.put("welcome", welcome);
        return "/fw/homepage/cus/nf-info.ftl";
    }

    @RequestMapping("/cus/nav/list")
    public String doNav(ModelMap map, HttpSession httpSession) {
        Session session = getSession(httpSession);
        List<SubSystem> subSystems = subsystemService.findAll();
        Map<Integer, SubSystemDto> subSystemMap = new HashMap<Integer, SubSystemDto>();
        for (SubSystem sub : subSystems) {
            Integer id = sub.getId();
            SubSystemDto dto = new SubSystemDto();
            dto.setSubSystem(sub);
            subSystemMap.put(id, dto);
        }
        List<Model> models = modelService.findByUserId(session.getLoginInfo().getUserId());
        Map<Integer, ModelDto> modelMap = new HashMap<Integer, ModelDto>();
        for (Model model : models) {
            ModelDto dto = new ModelDto();
            dto.setModel(model);
            modelMap.put(model.getId(), dto);
        }
        for (Model model : models) {
            ModelDto modelDto = modelMap.get(model.getId());
            Integer subSystemId = model.getSubSystem();
            SubSystemDto subSubSystem = subSystemMap.get(subSystemId);
            if (subSubSystem == null)
                continue;
            Integer parentId = model.getParentId();
            if (parentId == Model.PARENT_ID_DIRECT_SUBSYSTEM)
                subSubSystem.addModelDto(modelDto);

            ModelDto rm = modelMap.get(parentId);
            if (rm != null) {
                rm.addModelDto(modelDto);
            }
        }
        List<SubSystemDto> subs = new ArrayList<SubSystemDto>();
        for (SubSystem sub : subSystems) {
            Integer parentId = sub.getParentId();
            if (parentId != null && parentId.intValue() > 0) {
                SubSystemDto parent = subSystemMap.get(parentId);
                parent.addSubSystemDto(subSystemMap.get(sub.getId()));
            }
            else {
                subs.add(subSystemMap.get(sub.getId()));
            }
        }
        map.addAttribute("subSystemDtos", subs);
        return "/fw/homepage/cus/nf-navList.ftl";
    }

    @RequestMapping("/cus/{ftlName}")
    public String doCus(@PathVariable String ftlName, ModelMap map, HttpSession httpSession) {
        return "/fw/homepage/cus/" + ftlName + ".ftl";
    }

    @RequestMapping("/content/{ftlName}")
    public String doContent(@PathVariable String ftlName, ModelMap map) {
        return "/fw/homepage/content/" + ftlName + ".ftl";
    }

}
