package net.zdsoft.basedata.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.basedata.entity.McodeDetail;

public interface McodeDetailService extends BaseService<McodeDetail, String> {

	/**
	 * 根据mcodeId和thisId值，检索单个微代码
	 * 
	 * @param mcodeId
	 * @param thisId
	 * @return
	 */
	McodeDetail findByMcodeAndThisId(String mcodeId, String thisId);

	/**
	 * 根据mcodeIds，检索所有的微代码
	 * 
	 * @param mcodeIds
	 * @return
	 */
	List<McodeDetail> findByMcodeIdIn(String... mcodeIds);

	/**
	 * 根据Mcodeids,检索微代码，并按照mcodeId,组装成Map
	 * 
	 * @param mcodeIds
	 * @return
	 */
	Map<String, List<McodeDetail>> findMapByMcodeIdIn(String... mcodeIds);

	Map<String, McodeDetail> findMapByMcodeId(String mcodeId);

}
