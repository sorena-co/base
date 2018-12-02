package ir.sp.base.service.feign;

import ir.sp.base.client.AuthorizedFeignClient;
import ir.sp.base.service.dto.custom.FeignPlanDTO;
import ir.sp.base.service.dto.feign.GaModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "node-eureka")
@AuthorizedFeignClient(name = "ai")
public interface AiFeignClient {
//    @RequestMapping(value = "/institution/planing", method = RequestMethod.POST)
//    String startPlaning(@RequestBody PlanDTO plan);

    @RequestMapping(value = "/api/planning", method = RequestMethod.POST)
    String startPlaning(@RequestBody FeignPlanDTO plan);

//    @RequestMapping(value = "/institution/planing/{institutionId}", method = RequestMethod.GET)
//    GetPlanDTO getPlaning(@PathVariable(value = "institutionId", name = "institutionId") Long institutionId);

    @RequestMapping(value = "/api/planning/{institutionId}")
    GaModel getPlaning(@PathVariable(value = "institutionId", name = "institutionId") Long institutionId);

}
