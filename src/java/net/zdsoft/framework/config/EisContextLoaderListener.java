package net.zdsoft.framework.config;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @author zhangza
 * @date 2009-11-2
 */
public class EisContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("=============================================================");
        System.out.println("========================== EIS STARTING =====================");
        System.out.println("=============================================================");
        FrameworkEvn.newInstance().init();
        super.contextInitialized(event);
    }

}
