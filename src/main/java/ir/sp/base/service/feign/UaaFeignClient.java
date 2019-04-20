package ir.sp.base.service.feign;

import ir.sp.base.client.AuthorizedFeignClient;
import ir.sp.base.service.dto.feign.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "uaa",url = "http://127.0.0.1:9999")
public interface UaaFeignClient {
    @PostMapping("/api/register")
    UserDTO register(@RequestBody UserDTO userDTO);

    @GetMapping("/api/get-user")
    UserDTO getUser(@RequestParam(value = "login") String login);
}
