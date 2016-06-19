package net.zdsoft.framework.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import net.zdsoft.framework.utils.RedisUtils;
import net.zdsoft.framework.utils.ZookeeperConnector;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service
@Lazy(false)
public class FrameworkEvn implements ApplicationContextAware {

    private static FrameworkEvn frameworkEvn = new FrameworkEvn();

    private static final Map<String, Object> beanMap = new HashMap<String, Object>();

    private Logger log = Logger.getLogger(getClass());

    /**
     * 初始与mapO一直，根据具体环境会发生变化，譬如reids是否连接，如果配置为true，但是实际环境已经断开了，那么为false
     */
    public final Map<String, String> map = new HashMap<String, String>();

    /**
     * 记录配置文件原始值，不会变化
     */
    private final Map<String, String> mapO = new HashMap<String, String>();

    public String getString(String key) {
        return map.get(key);
    }

    public void putBean(String name, Object bean) {
        if (!beanMap.containsKey(name)) {
            beanMap.put(name, bean);
            if (StringUtils.endsWith(name, "Impl"))
                beanMap.put(StringUtils.substringBeforeLast(name, "Impl"), bean);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String name) {
//        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        T t = null;
        if (applicationContext.containsBean(name)) {
            t = (T) applicationContext.getBean(name);
        }
        else {
            t = (T) beanMap.get(name);
        }
        return t;
        // return SpringContextHolder.getBean(name);
    }

    // ---------------常用的几个参数接口------------
    public boolean isDevModel() {
        return BooleanUtils.toBoolean(getString("fw.devModel"));
    }

    public boolean isRedisEnable() {
        return BooleanUtils.toBoolean(getString("fw.redis.enable"));
    }

    public boolean isMemcachedEnable() {
        return BooleanUtils.toBoolean(getString("fw.memcached.enable"));
    }

    public static FrameworkEvn newInstance() {
        return frameworkEvn;
    }

    private FrameworkEvn() {

    }

    public void init() {
        log.info("初始化启动类");
        ResourceBundle bundle = ResourceBundle.getBundle("conf/framework");
        if (bundle != null) {
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = bundle.getString(key);
                map.put(key, value);
                mapO.put(key, value);
            }
        }

        if (isRedisEnable()) {
            try {
                RedisUtils.get("");
            }
            catch (Exception e) {
                map.put("fw.redis.enable", "false");
            }
        }

        bundle = ResourceBundle.getBundle("conf/dubbo");
        String dubboAddress = bundle.getString("dubbo.registry.address");
        String portal = StringUtils.substringBefore(dubboAddress, ":");
        map.put("dubbo_portal", portal);
        mapO.put("dubbo_portal", portal);
        String dubboIp = StringUtils.substringAfter(dubboAddress, "zookeeper://");
        map.put("zookeeper_domain", dubboIp);
        mapO.put("zookeeper_domain", dubboIp);

        // 定时处理一些业务，定时5分钟循环
        new Timer().schedule(new FrameworkTimer(), 10000, 3000000);
    }

    private class FrameworkTimer extends TimerTask {
        @Override
        public void run() {
            boolean check;
            if (BooleanUtils.toBoolean(mapO.get("fw.redis.enable"))) {
                // redis服务是否正常，如果不正常，会重连
                log.info("检查Redis服务……");
                check = RedisUtils.check();
                map.put("fw.redis.enable", check + "");
                if (check) {
                    log.info("Redis服务正常.");
                }
                else {
                    log.info("Redis服务连接失败.");
                }
            }

            if (StringUtils.equals("zookeeper", map.get("dubbo_portal"))) {
                ZookeeperConnector zookeeperConnector = ZookeeperConnector.newInstance(map.get("zookeeper_domain"));
                check = zookeeperConnector.check();
                if (check) {
                    log.info("Zookeeper服务正常.");
                }
                else {
                    log.info("Zookeeper服务连接失败.");
                }
            }
        }
    }

    public Map<String, String> getMap() {
        return map;
    }
    
    private static ApplicationContext   applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	    this.applicationContext = applicationContext;
//        String[] names = applicationContext.getBeanDefinitionNames();
//        for (String name : names) {
//            putBean(name, applicationContext.getBean(name));
//        }
    }

public static ApplicationContext getApplicationContext() {
	return applicationContext;
}
}
