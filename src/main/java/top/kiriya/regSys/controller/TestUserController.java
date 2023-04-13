package top.kiriya.regSys.controller;

import top.kiriya.regSys.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kiriya
 * @date 2023年01月04日 17:45
 * RESTful风格的用户测试controller
 */
@RestController
@RequestMapping("/testUser")
public class TestUserController {
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable int id){
        return "根据ID获取用户:"+id;
    }
    @PostMapping("/users" )
    public String save(User user){
        return "添加用户:"+user.toString();
    }
    @PutMapping ("/users")
    public String update(User user){
        return "更新用户:"+user.toString();
    }
    @DeleteMapping ("/users/{id}")
    public String deleteById(@PathVariable int id){
        return "根据ID删除用户:"+id;
    }
}
