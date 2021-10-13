package com.fxz.springcloud.controller;
import com.fxz.springcloud.entities.CommonResult;
import com.fxz.springcloud.entities.Payment;
import com.fxz.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")   //为什么要用@RequestBody呢，因为前端是get请求，你又是Post请求调用，前端用的RestTemplate，传过来的json对象，需要用注解转换成对应的
    // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的);
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("***********插入结果**************"+result);
        if (result>0){
            return new CommonResult(200,"插入数据库成功,"+serverPort,result);
        }
        else {
            return new CommonResult(404,"插入数据路失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("***********插入结果**************"+payment);
        if (payment!=null){
            return new CommonResult(200,"查询成功"+serverPort,payment);
        }
        else {
            return new CommonResult(404,"没有对应记录，查询ID： "+id,null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for(String element : services){
            log.info("*****element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return serverPort;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
