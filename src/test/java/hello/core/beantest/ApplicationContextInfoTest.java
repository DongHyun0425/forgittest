package hello.core.beantest;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("모든 빈 출력")
    void findAllBean(){
        String[] beanDefinitionsNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionsName: beanDefinitionsNames) {
            Object bean = ac.getBean(beanDefinitionsName);
            System.out.println("name = " + beanDefinitionsName +"object ="+ bean);
        }
    }

    @Test
    @DisplayName("모든 어플리케이션 빈 출력")
    void findAllApplicationBean(){
        String[] beanDefinitionsNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionsName: beanDefinitionsNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionsName);
            if(beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionsName);
                System.out.println("name = " + beanDefinitionsName +"object ="+ bean);
            }
        }
    }
}
