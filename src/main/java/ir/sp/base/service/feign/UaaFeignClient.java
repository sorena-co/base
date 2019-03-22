package ir.sp.base.service.feign;

import ir.sp.base.client.AuthorizedFeignClient;
import ir.sp.base.service.dto.feign.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthorizedFeignClient(name = "uaa")
public interface UaaFeignClient {
    @PostMapping("/api/register")
    UserDTO register(@RequestBody UserDTO userDTO);

    @GetMapping("/api/get-user/{login}")
    UserDTO getUser(@PathVariable(value = "login") String login);
}
