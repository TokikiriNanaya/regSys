package top.kiriya.regSys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kiriya.regSys.annotation.RequestLimit;
import top.kiriya.regSys.service.AdminService;
import top.kiriya.regSys.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Kiriya
 * @date 2023年01月05日 15:08
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    /**
     * 管理员登录
     */
    @PostMapping("/login")
    @RequestLimit(count = 5, time = 60000)
    public JsonResult login(@RequestBody Map<String, String> map,
                            HttpSession session,
                            HttpServletRequest request) {
        // 用户已登录
        if (adminService.verifyToken(session, request)) {
            return new JsonResult().setSuccess(false).setMessage("当前已登录，请勿重复登录");
        }

        String token = adminService.login(map.get("username"), map.get("password"));
        if (token != null) {
            // session 过期时间
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("token", token);
            System.out.println("登录成功 token：" + token);
            return new JsonResult().setSuccess(true).setData(token).setMessage("登录成功");
        }
        return new JsonResult().setSuccess(false).setMessage("账号或密码错误");
    }

    /**
     * token验证
     */
    @GetMapping("/verifyToken")
    public Boolean verifyToken(HttpSession session, HttpServletRequest request) {
        return adminService.verifyToken(session, request);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public Boolean logout(String token, HttpSession session) {
        String sessionToken = null;
        sessionToken = (String) session.getAttribute("token");
        System.out.println("登出 token：" + token + " session token:" + sessionToken);
        if (sessionToken != null && sessionToken.equals(token)) {
            session.removeAttribute("token");
            System.out.println("已登出token：" + token);
            return true;
        }
        System.out.println("token校验失败 登出失败：" + token);
        return false;
    }


}
