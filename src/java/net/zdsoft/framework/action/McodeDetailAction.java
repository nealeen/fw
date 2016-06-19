package net.zdsoft.framework.action;

import java.util.List;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.McodeDetailService;
import net.zdsoft.basedata.entity.McodeDetail;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanpeng.basedata.remote.dto.RemoteMcodeDetail;

@RestController
@RequestMapping("/fw/mcodedetail")
public class McodeDetailAction {

    @Resource
    private McodeDetailService mcodeDetailService;

    @RequestMapping("/select/{mcodeId}")
    public String mcodeSelect(@PathVariable String mcodeId) {
        List<McodeDetail> mcodeDetails = mcodeDetailService.findByMcodeIdIn(mcodeId);
        String s = "<select>";
        for (McodeDetail mcode : mcodeDetails) {
            s += "<option value='" + mcode.getThisId() + "'>" + mcode.getMcodeContent()
                    + "</option>";
        }
        s += "</select>";
        return s;
    }

}
