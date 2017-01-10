package com.excilys.formation.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

/**
 * Created by Ookami on 31/12/2016.
 * Class initializing the Spring contexts for the MVC.
 */
public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {PersistenceSpringConfig.class, ServiceSpringConfig.class, WebSpringConfig.class, DataBindingSpringConfig.class, WebSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/" };
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet serv = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);

        serv.setThrowExceptionIfNoHandlerFound(true);

        return serv;
    }

    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}