package top.kiriya.regSys.controller;

import top.kiriya.regSys.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kiriya
 * @date 2023年01月04日 15:20
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 基础映射
     * 只接受get请求 以下两种注释等价
     */
    //@RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping("/hello")
    public String hello(){
        return "success";
    }


    /**
     * get请求从前端获取参数
     * 多个参数用&拼接 例如：/getTest1?data=xxx&a=a&b=b
     * @param data 需与前端传递参数名保持一致 例如：/getTest1?data=xxx
     *                 不一致可以用注解：@RequestParam(value = "data",required = false)
     *                 如果不加required = false 则参数是必须的 没有参数会报错
     */
    @GetMapping("/getTest1")
    public String getTest1(String data){
        return "get:"+data;
    }

    /**
     * post请求从前端获取参数
     */
    @PostMapping("/postTest1")
    public String postTest1(String data){
        return "post:"+data;
    }

    /**
     * 接收实体类参数
     * 确保前端参数名与user实体类一一对应
     */
    @PostMapping("/postTest2")
    public String postTest2(User user){
        return "post-entity:"+user.toString();
    }

    /**
     * 接收json参数
     * 确保前端参数名与user实体类一一对应
     * 参数需要加@RequestBody
     */
    @PostMapping("/postTest3")
    public String postTest3(@RequestBody User user){
        return "post-json:"+user.toString();
    }
}
