package top.kiriya.regSys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.kiriya.regSys.entity.Admin;
import top.kiriya.regSys.mapper.AdminMapper;
import top.kiriya.regSys.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Kiriya
 * @date 2023/1/11 18:35
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;


    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return token md5(username+timestamp)
     */
    @Override
    public String login(String username, String password) {
        Admin admin = adminMapper.getAdminByUsername(username);
        String token = "";
        String md5 = "";
        // 密码转md5 并全部大写
        md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)).toUpperCase();
        if (admin != null && md5.equals(admin.getPassword())) {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            // 生成token
            token = DigestUtils.md5DigestAsHex((username + timestamp).getBytes(StandardCharsets.UTF_8)).toUpperCase();
            return token;
        } else return null;
    }

    /**
     * 验证token
     */
    @Override
    public Boolean verifyToken(HttpSession session, HttpServletRequest request) {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        // 获取session中的token
        String sessionToken = (String) session.getAttribute("token");
        System.out.println("校验token：" + token + " session token:" + sessionToken);
        // 判断token
        return sessionToken != null && sessionToken.equals(token);

    }
}
