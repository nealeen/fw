package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.McodeDetailService;
import net.zdsoft.basedata.dao.McodeDetailDao;
import net.zdsoft.basedata.entity.McodeDetail;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.springframework.stereotype.Service;

@Service
public class McodeDetailServiceImpl extends BaseServiceImpl<McodeDetail, String> implements McodeDetailService {

	@Resource
	private McodeDetailDao mcodeDetailDao;

	@Override
	public McodeDetail findByMcodeAndThisId(final String mcodeId, final String thisId) {
		return RedisUtils.getObject("mcodedetail.by.mcodeid.thisid." + mcodeId + "," + thisId, new RedisInterface<McodeDetail>() {
			@Override
			public McodeDetail queryData() {
				return mcodeDetailDao.findByMcodeIdAndThisId(mcodeId, thisId);
			}
		});
	}

	@Override
	public List<McodeDetail> findByMcodeIdIn(final String... mcodeIds) {
		List<McodeDetail> list = new ArrayList<McodeDetail>();
		for (String mcodeId : mcodeIds) {
			final String mcodeIdf = mcodeId;
			list.addAll(RedisUtils.getObject("mcodedetail.by.mcodeid." + mcodeIdf, new RedisInterface<List<McodeDetail>>() {
				@Override
				public List<McodeDetail> queryData() {
					return mcodeDetailDao.findByMcodeId(mcodeIdf);
				}
			}));
		}
		return list;
	}

	@Override
	public Map<String, List<McodeDetail>> findMapByMcodeIdIn(final String... mcodeIds) {
		List<McodeDetail> details = findByMcodeIdIn(mcodeIds);
		Map<String, List<McodeDetail>> map = new HashMap<String, List<McodeDetail>>();
		for (McodeDetail detail : details) {
			String mcodeId = detail.getMcodeId();
			List<McodeDetail> list = map.get(mcodeId);
			if (list == null) {
				list = new ArrayList<McodeDetail>();
				map.put(mcodeId, list);
			}
			list.add(detail);
		}
		return map;
	}

	@Override
	public Map<String, McodeDetail> findMapByMcodeId(final String mcodeId) {
		final String mcodeIdf = mcodeId;
		Map<String, McodeDetail> map = new HashMap<String, McodeDetail>();
		List<McodeDetail> mcodes = findByMcodeIdIn(mcodeIdf);
		for (McodeDetail mcode : mcodes) {
			map.put(mcode.getThisId(), mcode);
		}
		return map;
	}

	@Override
	protected BaseJapRepositoryDao<McodeDetail, String> getBaseJapRepositoryDao() {
		return mcodeDetailDao;
	}
}
