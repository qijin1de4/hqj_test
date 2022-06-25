package com.hqj.test.springboot.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Tomcat的Servelet请求监听器。
 * 当请求进来通过第一个Servlet或者Filter的时候触发：requestInitialized
 * 当请求出去通过最后一个Servlet或者第一个Filter的时候触发：requestDestroyed
 */
@Slf4j
@Component
@WebListener
public class MyTomcatServletRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("Byebye : ContentLength {}", sre.getServletRequest().getContentLength());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("Hello, ContentLength : {}", sre.getServletRequest().getContentLength());
    }

}
