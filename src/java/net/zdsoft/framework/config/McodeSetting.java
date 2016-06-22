package net.zdsoft.framework.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.basedata.common.service.McodeDetailService;
import net.zdsoft.basedata.common.service.RegionService;
import net.zdsoft.basedata.entity.McodeDetail;
import net.zdsoft.basedata.entity.Region;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class McodeSetting {
	private static McodeSetting mcodeSetting = new McodeSetting();

	public static McodeSetting newInstance() {
		if (mcodeSetting == null) {
			mcodeSetting = new McodeSetting();
		}
		mcodeDetailService = FrameworkEvn.newInstance().getBean("mcodeDetailService");
		regionService = FrameworkEvn.newInstance().getBean("regionService");
		return mcodeSetting;
	}

	private McodeSetting() {

	}

	static McodeDetailService mcodeDetailService;
	static RegionService regionService;

	public String getMcodeWithJqGrid(final String mcodeId) {
		return RedisUtils.get("eis.mcode.getMcodeWithJqGrid." + mcodeId, new RedisInterface<String>() {
			@Override
			public String queryData() {
				mcodeDetailService = FrameworkEvn.newInstance().getBean("mcodeDetailService");
				List<McodeDetail> mcodes = mcodeDetailService.findByMcodeIdIn(mcodeId);
				String s = "";
				for (McodeDetail mcode : mcodes) {
					if (StringUtils.isBlank(s)) {
						s = mcode.getThisId() + ":" + mcode.getMcodeContent();
					} else {
						s = s + ";" + mcode.getThisId() + ":" + mcode.getMcodeContent();
					}
				}
				return s;
			}

		});
	}

	public String getMcode(final String mcodeId, final String thisId) {
		if (StringUtils.startsWith(mcodeId.toLowerCase(), "dm-")) {
			String s = RedisUtils.get("eis.model.mcode:" + mcodeId + "_" + thisId, 3600, new RedisInterface<String>() {
				@Override
				public String queryData() {
					mcodeDetailService = FrameworkEvn.newInstance().getBean("mcodeDetailService");
					McodeDetail mcodeDetail = mcodeDetailService.findByMcodeAndThisId(mcodeId, thisId);
					if (mcodeDetail == null)
						return "";
					else
						return mcodeDetail.getMcodeContent();
				}
			});
			return s;
		} else if (StringUtils.equals(mcodeId.toLowerCase(), "region")) {

		} else if (StringUtils.startsWith(mcodeId.toLowerCase(), "select")) {
			// baseCommonService =
			// FrameworkEvn.newInstance().getBean("baseCommonService");
			// List<Object[]> datas =
			// baseCommonService.querySql(mcodeId, null);
			// for (Object[] mcode : datas) {
			// String content = String.valueOf(mcode[1]);
			// String mcodeThisId = String.valueOf(mcode[0]);
			// if(StringUtils.equals(thisId, mcodeThisId))
			// return content;
			// }
		}
		return "";
	}

	/**
	 * 
	 * @param mcodeId
	 * @param thisId
	 * @param name
	 * @return
	 */
	public String getMcodeCheckbox(final String mcodeId, final String thisId, final String name) {
		String[] ts = StringUtils.split(thisId, ",");
		List<McodeDetail> mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
		StringBuffer sb = new StringBuffer();
		for (McodeDetail mcode : mcodeDetails) {
			String content = mcode.getMcodeContent();
			String mcodeThisId = mcode.getThisId();
			sb.append("<label>");
			sb.append("<input type='checkbox' id='").append(name).append("_").append(mcodeThisId).append("' name='").append(name)
					.append("' value='").append(mcode.getThisId()).append("'");
			sb.append(" title='").append(content).append("'");
			if (ArrayUtils.contains(ts, mcodeThisId)) {
				sb.append(" checked");
			}
			sb.append(" class='ace' />");
			sb.append("<span class='lbl'> ").append(content).append("</span></label>");
		}
		return sb.toString();
	}

	public String mcodeSelectJqGrid(String mcodeId) {
		mcodeDetailService = FrameworkEvn.newInstance().getBean("mcodeDetailService");
		List<McodeDetail> mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
		String s = "";
		for (McodeDetail detail : mcodeDetails) {
			if (StringUtils.isBlank(s))
				s = detail.getThisId() + ":" + detail.getMcodeContent();
			else
				s += ";" + detail.getThisId() + ":" + detail.getMcodeContent();
		}
		return s;
	}

	@RequestMapping("/mcode/{mcodeId}")
	@ResponseBody
	public String mcodeSelect(@PathVariable String mcodeId) {
		List<McodeDetail> mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
		String s = "<select>";
		for (McodeDetail mcode : mcodeDetails) {
			s += "<option value='" + mcode.getThisId() + "'>" + mcode.getThisId() + "-" + mcode.getMcodeContent() + "</option>";
		}
		s += "</select>";
		return s;
	}

	/**
	 * 组装select的option数据
	 * 
	 * @param mcodeId
	 * @param thisId
	 * @param containNull
	 * @return
	 */
	public String getMcodeSelect(final String mcodeId, final String thisId, final String containNull) {
		if (StringUtils.isBlank(mcodeId)) {
			return "<option value=''>--- 请选择 ---</option>";
		}
		List<McodeDetail> mcodeDetails;
		if (StringUtils.equals("DM-REGION", mcodeId)) {
			mcodeDetails = new ArrayList<McodeDetail>();
			List<Region> regions = regionService.findTop20();
			for (Region region : regions) {
				McodeDetail detail = new McodeDetail();
				detail.setThisId(region.getFullCode());
				detail.setMcodeContent(region.getFullName());
				mcodeDetails.add(detail);
			}
		} else {
			mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
		}

		StringBuffer sb = new StringBuffer();
		if (StringUtils.equals("1", containNull)) {
			sb.append("<option value=''>--- 请选择 ---</option>");
		}
		for (McodeDetail mcode : mcodeDetails) {
			String content = mcode.getMcodeContent();
			String mcodeThisId = mcode.getThisId();
			sb.append("<option value='").append(mcode.getThisId()).append("'");
			sb.append(" title='").append(content).append("'");
			if (null != mcode.getIsFolder() && BooleanUtils.toBoolean(mcode.getIsFolder())) {
				sb.append(" disabled='disabled' style='color:rgb(98, 117, 255);'");
			}
			if (StringUtils.equals(mcodeThisId, thisId))
				sb.append(" selected");
			sb.append(">").append(mcodeThisId).append("-").append(content).append("</option>");
		}
		return sb.toString();
	}

	public String getVsqlSelect(String vsql, final String thisId, final String containNull, Map<String, Object> obj) {
		if (StringUtils.isBlank(vsql)) {
			return "<option value=''>--- 请选择 ---</option>";
		}

		List<Object> params = new ArrayList<Object>();
		while (StringUtils.contains(vsql, "{") && StringUtils.contains(vsql, "}")) {
			String p = StringUtils.substringBetween(vsql, "{", "}");
			vsql = StringUtils.replace(vsql, "{" + p + "}", "?");
			Object o = obj.get(p);
			params.add(o == null ? "0" : o);
		}

		List<String[]> os = mcodeDetailService.findBySql(vsql, params.toArray(new Object[0]));
		List<McodeDetail> mcodeDetails = new ArrayList<McodeDetail>();
		for (String[] o : os) {
			McodeDetail detail = new McodeDetail();
			detail.setThisId(o[0]);
			detail.setMcodeContent(o[1]);
			mcodeDetails.add(detail);
		}

		StringBuffer sb = new StringBuffer();
		if (StringUtils.equals("1", containNull)) {
			sb.append("<option value=''>--- 请选择 ---</option>");
		}
		for (McodeDetail mcode : mcodeDetails) {
			String content = mcode.getMcodeContent();
			String mcodeThisId = mcode.getThisId();
			sb.append("<option value='").append(mcode.getThisId()).append("'");
			sb.append(" title='").append(content).append("'");
			if (null != mcode.getIsFolder() && BooleanUtils.toBoolean(mcode.getIsFolder())) {
				sb.append(" disabled='disabled' style='color:rgb(98, 117, 255);'");
			}
			if (StringUtils.equals(mcodeThisId, thisId))
				sb.append(" selected");
			sb.append(">").append(content).append("</option>");
		}
		return sb.toString();
	}

	public String getMcodeRadio(final String mcodeId, final String thisId, final String name) {
		List<McodeDetail> mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
		StringBuffer sb = new StringBuffer();
		for (McodeDetail mcode : mcodeDetails) {
			String content = mcode.getMcodeContent();
			String mcodeThisId = mcode.getThisId();
			sb.append("<label>");
			sb.append("<input type='radio' id='").append(name).append("_").append(mcodeThisId).append("' name='").append(name)
					.append("' value='").append(mcode.getThisId()).append("'");
			sb.append(" title='").append(content).append("'");
			if (StringUtils.equals(mcodeThisId, thisId))
				sb.append(" checked");
			sb.append(" class='ace' />");
			sb.append("<span class='lbl'> ").append(content).append("</span></label>");
			// append("<label for='").append(name).append("_").append(mcodeThisId).append("'>").append(content)
			// .append("</label>&nbsp;");
		}
		return sb.toString();
	}
}
