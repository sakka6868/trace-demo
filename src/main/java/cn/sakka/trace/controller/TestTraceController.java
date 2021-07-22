package cn.sakka.trace.controller;

import cn.sakka.trace.MDCTraceThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/trace/")
@Slf4j
public class TestTraceController {

    private final MDCTraceThreadPoolExecutor executorService = new MDCTraceThreadPoolExecutor(2, 20, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1500));

    @GetMapping("traceLog")
    public String traceLog() {
        log.info("---接口调用了---");
        traceService();
        asyncTrace();
        return "success";
    }

    private void traceService() {
        log.error("## 执行traceService方法");
    }

    private void asyncTrace() {
        CompletableFuture.runAsync(() -> log.info("执行线程池中的方法asyncTrace，重写了线程池"), executorService);
    }
}
