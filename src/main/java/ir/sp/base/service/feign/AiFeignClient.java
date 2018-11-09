package ir.sp.base.service.feign;

import ir.sp.base.service.dto.GetPlanDTO;
import ir.sp.base.service.dto.PlanDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "node-eureka")
public interface AiFeignClient {
    @RequestMapping(value = "/institution/planing", method = RequestMethod.POST)
    String startPlaning(@RequestBody PlanDTO plan);

    @RequestMapping(value = "/institution/planing/{institutionId}", method = RequestMethod.GET)
    GetPlanDTO getPlaning(@PathVariable(value = "institutionId", name = "institutionId") Long institutionId);
}
