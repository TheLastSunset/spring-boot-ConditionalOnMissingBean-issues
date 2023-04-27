package com.example.demo.log;

import com.mzt.logapi.beans.Operator;
import com.mzt.logapi.service.IOperatorGetService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 需要加 @Order，让 LogRecordConfiguration2 优先级更高，见 configCandidates.sort
 * {@link org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions}
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogRecordConfiguration2 {

    @Bean
    public IOperatorGetService operatorGetService() {
        return () -> {
            Operator operator = new Operator();
            operator.setOperatorId("test");
            return operator;
        };
    }

}
