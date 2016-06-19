package net.zdsoft.framework.config;

import java.util.Map;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Freemarker的全局参数放到这里
 * 
 * @author linqz
 */
public class EisFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    public void setFreemarkerVariables(Map<String, Object> variables) {
        Map<String, String> map = FrameworkEvn.newInstance().getMap();
        for (String key : map.keySet()) {
            variables.put(key, map.get(key));
        }
        variables.put("frameworkEnv", FrameworkEvn.newInstance());
        variables.put("mcodeSetting", McodeSetting.newInstance());
        super.setFreemarkerVariables(variables);
    }
}
