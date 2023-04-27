package com.example.demo.log;

import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.springframework.context.annotation.Configuration;


@EnableLogRecord(tenant = "temp")
@Configuration(proxyBeanMethods = false)
public class LogRecordConfiguration1 {

}
