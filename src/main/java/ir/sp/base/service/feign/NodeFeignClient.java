package ir.sp.base.service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "node-eureka")
public interface NodeFeignClient {
    @RequestMapping("/info")
     String test();
}
