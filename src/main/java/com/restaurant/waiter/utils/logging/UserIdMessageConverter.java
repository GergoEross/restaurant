package com.restaurant.waiter.utils.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.restaurant.waiter.spring.ApplicationContextProvider;
import com.restaurant.waiter.utils.request.RequestBean;
import net.logstash.logback.marker.ObjectAppendingMarker;

import static com.restaurant.waiter.utils.logging.CustomRequestLoggingFilter.NOT_DEF;
import static com.restaurant.waiter.utils.logging.CustomRequestLoggingFilter.USER_ID;

public class UserIdMessageConverter extends ClassicConverter{

    ApplicationContextProvider appContext = new ApplicationContextProvider();


    @Override
    @AspectLogger
    public String convert(ILoggingEvent event) {

        try {
            RequestBean request = appContext.getApplicationContext().getBean("requestScopedBean", RequestBean.class);
            if (request != null) {
                return ("" + request.getUser());
            }
        } catch (Exception e) {
            if (event != null && event.getArgumentArray() != null) {
                ObjectAppendingMarker tmp;
                for (Object bean : event.getArgumentArray()) {
                    if (bean instanceof ObjectAppendingMarker) {
                        tmp = (ObjectAppendingMarker) bean;
                        if (USER_ID.equals(tmp.getFieldName())) {
                            return "" + tmp.getFieldValue();
                        }
                    }
                }
            }
        }
        return NOT_DEF;
    }

}
