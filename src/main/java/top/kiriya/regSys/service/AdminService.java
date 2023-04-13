package top.kiriya.regSys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AdminService {
    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return token md5(username+timestamp)
     */
    String login(String username, String password);

    /**
     * 验证token
     */
    Boolean verifyToken(HttpSession session, HttpServletRequest request);
}
