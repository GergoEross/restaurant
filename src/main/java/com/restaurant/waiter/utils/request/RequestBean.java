package com.restaurant.waiter.utils.request;

import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Data
@Slf4j
public class RequestBean {

    private String requestId ;

    private String user;

    private String token;

    private String client;

    private StopWatch watcher;

    public RequestBean(){
        this.watcher= new StopWatch();
        this.watcher.start();
    }

}
