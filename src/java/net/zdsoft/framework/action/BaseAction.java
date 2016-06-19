package net.zdsoft.framework.action;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zdsoft.framework.config.ControllerException;
import net.zdsoft.framework.config.Session;
import net.zdsoft.framework.dto.ResultDto;
import net.zdsoft.framework.entity.Json;
import net.zdsoft.framework.entity.JsonArray;
import net.zdsoft.framework.entity.LoginInfo;
import net.zdsoft.framework.utils.Pagination;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


public class BaseAction {

	public LoginInfo getLoginInfo(HttpSession httpSession) {
		String sessionId = httpSession.getId();
		Session session = Session.get(sessionId);
		if (session == null)
			return null;
		else
			return session.getAttribute("loginInfo");
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String runtimeExceptionHandler(ControllerException runtimeException, WebRequest request) {
		return Json.toJSONString(new ResultDto().setSuccess(false).setCode(runtimeException.getCode()).setMsg(runtimeException.getMessage()));
	}

	public String returnError(String code, String msg) {
		return Json.toJSONString(new ResultDto().setSuccess(false).setCode(code).setMsg(msg));
	}

	public String returnError(String msg) {
		return Json.toJSONString(new ResultDto().setSuccess(false).setCode("-1").setMsg(msg));
	}
	
	public String returnError() {
		return Json.toJSONString(new ResultDto().setSuccess(false).setCode("-1").setMsg("操作失败！"));
	}

	public String returnSuccess(String code, String msg) {
		return Json.toJSONString(new ResultDto().setSuccess(true).setCode(code).setMsg(msg));
	}

	public String returnSuccess(String msg) {
		return Json.toJSONString(new ResultDto().setSuccess(true).setCode("00").setMsg(msg));
	}
	public String returnSuccess() {
		return Json.toJSONString(new ResultDto().setSuccess(true).setCode("00").setMsg("操作成功！"));
	}

	public Session getSession(HttpSession httpSession) {
		String sessionId = httpSession.getId();
		return Session.getWithCreate(sessionId);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String> syncParameters(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String s = request.getParameter(key);
			paramMap.put(key, s);
		}
		return paramMap;
	}

	protected <T> String createJqGridJson(List<T> ts, Pagination page) {
		Json.toJSON(ts);
		Json on = new Json();
		JsonArray jsonArray = new JsonArray();
		for (T t : ts) {
			jsonArray.add(t);
		}
		on.put("rows", jsonArray);
		if (page != null) {
			on.put("records", page.getMaxRowCount());
			on.put("page", page.getPageIndex());
			on.put("total", page.getMaxPageIndex());
		}
		return on.toJSONString();
	}

	protected <T> String createJqGridJson(List<T> ts) {
		return createJqGridJson(ts, null);
	}

	protected Pagination createPagination(HttpServletRequest request) {
		Map<String, String> paramMap = syncParameters(request);
		Pagination page = new Pagination(NumberUtils.toInt(paramMap.get("page")), NumberUtils.toInt(paramMap.get("rows")), false);
		return page;
	}

	// /**
	// * 根据jqgrid过滤器生成SPEL语言脚本
	// *
	// * @param request
	// * @return
	// */
	// protected String createJqGridFilter(HttpServletRequest request) {
	// String json = request.getParameter("filters");
	// List<String> ops = new ArrayList<String>();
	// String groupOp = null;
	// if (json != null) {
	// JsonNode node = JsonUtils.toObjectNode(json);
	// if (node != null) {
	// groupOp = node.get("groupOp").asText();
	// JsonNode rules = node.findValues("rules").get(0);
	// for (JsonNode jnode : rules) {
	// String op = jnode.get("op").asText();
	// String dn = "data." + jnode.get("field").asText();
	// String field;
	// if (ArrayUtils.contains(new String[] { "nn", "nu" }, op)) {
	// field = "(#" + dn + ")";
	// }
	// else {
	// field = "(#" + dn + " == null ? \"\" : #" + dn +
	// " + \"\").toLowerCase()";
	// }
	// String data = jnode.get("data").asText().toLowerCase();
	// if (StringUtils.equals(op, "cn"))
	// ops.add("(" + field + ".contains(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "bw"))
	// ops.add("(" + field + ".startsWith(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "bn"))
	// ops.add("(!" + field + ".startsWith(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "in"))
	// ops.add("(" + field + ".contains(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "ni"))
	// ops.add("(" + field + ".contains(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "ew"))
	// ops.add("(" + field + ".endsWith(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "en"))
	// ops.add("(!" + field + ".endsWith(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "nc"))
	// ops.add("(!" + field + ".contains(\"" + data + "\"))");
	// else if (StringUtils.equals(op, "nu"))
	// ops.add("(" + field + " == null)");
	// else if (StringUtils.equals(op, "nn"))
	// ops.add("(" + field + " != null)");
	// else
	// ops.add("(" + field + " " + op + " \"" + data + "\")");
	//
	// }
	// }
	// }
	// String ops_ = StringUtils.join(ops.toArray(new String[0]), " " +
	// groupOp
	// + " ");
	// return ops_;
	// }

	/**
	 * 根据jqgrid的标题点击排序进行排序
	 * 
	 * @param units
	 * @param filterMap
	 * @return
	 */
	protected <T> List<T> applyJqGridSort(List<T> units, Map<String, String> filterMap) {
		final String sidx = String.valueOf(filterMap.get("sidx"));
		if (StringUtils.isNotBlank(sidx)) {
			final StandardEvaluationContext context = new StandardEvaluationContext();
			final ExpressionParser parser = new SpelExpressionParser();
			final String sord = String.valueOf(filterMap.get("sord"));

			Collections.sort(units, new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					try {
						Field field = o1.getClass().getDeclaredField(sidx);
						String c = field.getType().getName();
						if (ArrayUtils.contains(new String[] { "java.lang.Integer", "java.lang.Long", "int", "long" }, c)) {
							context.setVariable("o1", o1);
							context.setVariable("o2", o2);
							Integer i1 = parser.parseExpression("#o1." + sidx).getValue(context, Integer.class);
							Integer i2 = parser.parseExpression("#o2." + sidx).getValue(context, Integer.class);
							int k = (i1 == null ? 0 : i1) - (i2 == null ? 0 : i2);
							return StringUtils.equals(sord, "asc") ? k : -k;
						} else {
							context.setVariable("o1", o1);
							context.setVariable("o2", o2);
							int k = parser.parseExpression("#o1." + sidx + ".compareTo(#o2." + sidx + ")").getValue(
									context, int.class);
							return StringUtils.equals(sord, "asc") ? k : -k;
						}
					} catch (Exception e) {
						return 0;
					}

				}
			});
		}
		return units;
	}

}
