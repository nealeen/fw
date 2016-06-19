package net.zdsoft.framework.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.framework.annotation.ControllerInfo;
import net.zdsoft.framework.config.FrameworkEvn;
import net.zdsoft.framework.config.Session;
import net.zdsoft.framework.entity.LoginInfo;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonHandlerInterceptor implements HandlerInterceptor {

	private String ignore;

	private String auth;
	private String loginUrl;
	private String indexUrl;
	private String[] excludeUrls;
	private String[] excludePaths;

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		String uri = req.getRequestURI();
		Session session = Session.get(req.getSession().getId());
		if (FrameworkEvn.newInstance().isDevModel()) {
			HandlerMethod method = (HandlerMethod) arg2;
			Method m = method.getMethod();
			System.out.println();
			System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println(uri);
			ControllerInfo info = m.getAnnotation(ControllerInfo.class);
			if (info != null) {
				String logStr = session.getLoginInfo().getUserId() + "@" + (new Date()) + ": " + info.value();
				@SuppressWarnings("unchecked")
				Map<String, Object> attrMap = (Map<String, Object>) req
						.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
				String url = (String) req.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
				while (StringUtils.contains(logStr, "{") && StringUtils.contains(logStr, "}")) {
					String s = StringUtils.substringBetween(logStr, "{", "}");
					Object o = attrMap.get(s);
					logStr = StringUtils.replace(logStr, "{" + s + "}", o == null ? "[" + s + "]" : o.toString());
				}
				System.out.println(logStr + ", url: " + url);
			}
			System.out.println();
		}
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse arg1, Object arg2, ModelAndView view) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		HttpSession httpSession = req.getSession();
		Session session = null;
		if (httpSession != null) {
			String sessionId = httpSession.getId();
			session = Session.get(sessionId);
			if (session != null)
				session.refresh();
			else {
				System.out.println("INIT session");
				session = new Session(sessionId);
			}
		}
		String uri = req.getRequestURI();

		for (String s : excludePaths) {
			if (StringUtils.contains(uri, s)) {
				return true;
			}
		}

		for (String s : excludeUrls) {
			if (StringUtils.endsWith(uri, s)) {
				return true;
			}
		}
		LoginInfo loginInfo = session.getLoginInfo();
		if (loginInfo == null) {
			String loginUrl = RedisUtils.get("login.url", new RedisInterface<String>() {
				@Override
				public String queryData() {
					return FrameworkEvn.newInstance().getString("eis_login_url");
				}
			});

			String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath()
					+ req.getServletPath();
			res.sendRedirect(loginUrl + "?rd=" + basePath);
			return false;
		}
		return true;
	}

	public String getIgnore() {
		return ignore;
	}

	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String[] getExcludePaths() {
		return excludePaths;
	}

	public void setExcludePaths(String[] excludePaths) {
		this.excludePaths = excludePaths;
	}

}
