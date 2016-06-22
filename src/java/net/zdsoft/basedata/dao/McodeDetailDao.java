package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.McodeDetail;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;

import org.springframework.data.jpa.repository.Query;

public interface McodeDetailDao extends BaseJapRepositoryDao<McodeDetail, String> {

	@Query("from McodeDetail where mcodeId = ?1 and isUsing = 1 order by displayOrder")
	List<McodeDetail> findByMcodeId(String mcodeId);

	McodeDetail findByMcodeIdAndThisId(String mcodeId, String thisId);
}
