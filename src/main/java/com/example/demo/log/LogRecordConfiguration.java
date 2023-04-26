package com.example.demo.log;

import com.mzt.logapi.beans.Operator;
import com.mzt.logapi.service.IOperatorGetService;
import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableLogRecord(tenant = "temp") // 2
@Configuration(proxyBeanMethods = false)
public class LogRecordConfiguration {
    public LogRecordConfiguration() {
    }

    @Bean
    public IOperatorGetService operatorGetService() {
        return () -> {
            Operator operator = new Operator();
            operator.setOperatorId("test");
            return operator;
        };
    }

}
