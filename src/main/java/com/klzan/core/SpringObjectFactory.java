package com.klzan.core;

import com.klzan.core.exception.SystemException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpringObjectFactory {

    private static ApplicationContext applicationContext;

    public <T> T createBean(Class<T> beanClass) {
        return applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
    }

    public <T> T getOrCreateBean(Class<T> beanClass) {
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        return getOrCreateBean(beanClass, beanName);
    }

    public <T> T getOrCreateBean(Class<T> beanClass, String beanName) {
        registerSingletonBean(beanName, beanClass);
        return applicationContext.getBean(beanName, beanClass);
    }

    public <T> T getOrCreateBean(Class<T> beanClass, String beanName, GenericBeanDefinition definition) {
        registerBeanDefinition(definition, beanName);
        return applicationContext.getBean(beanName, beanClass);
    }

    public <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public <T> T getNullableBean(Class<T> beanClass) {
        try {
            return applicationContext.getBean(beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public Object getNullableBean(String beanName) {
        try {
            return applicationContext.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }

    public <T> List<T> getBeans(Class<T> beanClass) {
        return new ArrayList<T>(applicationContext.getBeansOfType(beanClass).values());
    }

    public Resource[] getResources(String locationPattern) {
        try {
            return applicationContext.getResources(locationPattern);
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T initializeBean(T bean) {
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(bean);
        return (T) beanFactory.initializeBean(bean, bean.getClass().getName());
    }

    public void destroyBean(String beanName) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
                .getAutowireCapableBeanFactory();
        if (getNullableBean(beanName) != null) {
            beanFactory.destroySingleton(beanName);
        }
    }

    /**
     * register bean definition with singleton scope, when the definition has existed, do nothing, or register it
     * @param beanName
     * @param beanClass
     */
    public void registerSingletonBean(String beanName, Class<?> beanClass) {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            return;
        } catch (Exception e) {
            GenericBeanDefinition definition =  new GenericBeanDefinition();
            definition.setBeanClass(beanClass);
            definition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanRegistry.registerBeanDefinition(beanName, definition);
        }
    }

    public void registerSingletonBean(Class<?> beanClass) {
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        GenericBeanDefinition definition =  new GenericBeanDefinition();
        definition.setBeanClass(beanClass);
        definition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanRegistry.registerBeanDefinition(beanName, definition);
    }

    /**
     * 根据传入的bean定义信息注册到spring容器中
     * @param definition
     * @param beanName
     */
    public void registerBeanDefinition(GenericBeanDefinition definition, String beanName) {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            return;
        } catch (Exception e) {
            beanRegistry.registerBeanDefinition(beanName, definition);
        }
    }

    /**
     * 向spring容器中注册实例,如果容器中已经存在对应beanName的定义，则不进行任何操作。
     * @param beanName
     * @param beanInstance
     */
    public void registerSingletonBean(String beanName, Object beanInstance) {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            return;
        } catch (Exception e) {
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
            beanFactory.registerSingleton(beanName, beanInstance);
        }
    }

    public void registerSingletonBean(Object beanInstance) {
        String beanName = StringUtils.uncapitalize(beanInstance.getClass().getSimpleName());
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            return;
        } catch (Exception e) {
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
            beanFactory.registerSingleton(beanName, beanInstance);
        }
    }

    public Object initializeBean(String beanName, Object beanInstance) {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            beanInstance = getBean(beanName);
            beanFactory.initializeBean(beanInstance, beanName);
        } catch (Exception e) {
            beanFactory.registerSingleton(beanName, beanInstance);
            beanFactory.initializeBean(beanInstance, beanName);
        }
        return getBean(beanName);
    }

    public Object initializeDisposableBean(String beanName, DisposableBean beanInstance) {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            beanInstance = (DisposableBean)getBean(beanName);
            beanFactory.registerDisposableBean(beanName, beanInstance);
            beanFactory.initializeBean(beanInstance, beanName);
        } catch (Exception e) {
            beanFactory.registerSingleton(beanName, beanInstance);
            beanFactory.registerDisposableBean(beanName, beanInstance);
            beanFactory.initializeBean(beanInstance, beanName);
        }
        return getBean(beanName);
    }

    public static SpringObjectFactory.Profile getActiveProfile() {
        String[] activeProfiles = applicationContext.getEnvironment()
                .getActiveProfiles();
        if (ArrayUtils.isNotEmpty(activeProfiles)) {
            return SpringObjectFactory.Profile.resolve(activeProfiles[0]);
        }
        return null;
    }

    public static enum Profile {
        DEV("dev"), PROD("prod"), TEST("test");

        private String profileName;

        Profile(String profileName) {
            this.profileName = profileName;
        }

        public String getProfileName() {
            return profileName;
        }

        public static Profile resolve(String profileName) {
            for (Profile profile : values()) {
                if (profile.getProfileName().equalsIgnoreCase(profileName)) {
                    return profile;
                }
            }
            return null;
        }

        public boolean isDev() {
            if(SpringObjectFactory.Profile.DEV.equals(this)) {
                return true;
            }
            return false;
        }
        public boolean isProd() {
            if(SpringObjectFactory.Profile.PROD.equals(this)) {
                return true;
            }
            return false;
        }

    }

    public Object initializeDisposableBean(DisposableBean beanInstance) {
        return initializeDisposableBean(StringUtils.uncapitalize(beanInstance.getClass().getSimpleName()), beanInstance);
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        Assert.isTrue(applicationContext.getAutowireCapableBeanFactory() instanceof BeanDefinitionRegistry, "autowireCapableBeanFactory should be BeanDefinitionRegistry");
    }
    
}