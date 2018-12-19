package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInt injectRandomIntAnnotation = field.getAnnotation(InjectRandomInt.class);
            if (injectRandomIntAnnotation != null) {
                int max = injectRandomIntAnnotation.max();
                int min = injectRandomIntAnnotation.min();
                Random random = new Random();
                int i = min + random.nextInt(max-min);
                field.setAccessible(true);
                ReflectionUtils.setField(field,bean,i);
            }
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
