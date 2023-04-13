package top.kiriya.regSys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kiriya.regSys.annotation.RequestLimit;
import top.kiriya.regSys.entity.RegCode;
import top.kiriya.regSys.service.AdminService;
import top.kiriya.regSys.service.MailService;
import top.kiriya.regSys.service.RegCodeService;
import top.kiriya.regSys.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author Kiriya
 * @date 2023/1/9 17:58
 * 注册码控制器
 */
@RestController()
@RequestMapping("/regCode")
public class RegCodeController {

    @Autowired
    RegCodeService regCodeService;
    @Autowired
    AdminService adminService;
    @Autowired
    MailService mailService;

    /**
     * 通过有效期创建注册码
     */
    @PostMapping("/createRegCode")
    public JsonResult createRegCode(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
        // token校验
        if (!adminService.verifyToken(session, request))
            return new JsonResult().setSuccess(false).setMessage("token校验失败");

        int expiryTime = Integer.parseInt(map.get("expiryTime"));
        String qq = map.get("qq");
        String email = map.get("email");
        RegCode regCode = regCodeService.createCode(expiryTime, email, qq);
        if (regCode != null) {
            // 邮箱不为空则发送
            System.out.println(email);
            if (email != null) {
                try {
                    mailService.sendSimpleMail("kiriya@kiriya.top",
                            email,
                            "300tool 注册码",
                            "你的注册码是:" + regCode.getCode() + "\n脚本问题请反馈@时桐七夜(QQ:1406497296)");
                    System.out.println("成功发送邮件");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return new JsonResult().setSuccess(true).setData(regCode).setMessage("获取成功 但发送邮件过程中出现错误");
                }
            }
            return new JsonResult().setSuccess(true).setData(regCode).setMessage("获取成功");
        }
        return new JsonResult().setSuccess(false).setData(regCode).setMessage("获取失败");

    }

    /**
     * 校验注册码
     *
     * @param code   注册码
     * @param pcInfo 电脑信息
     */
    @GetMapping("/verify")
    // 限制限制同一ip访问频率（需要request参数）
    @RequestLimit(count = 20, time = 60000)
    public Boolean verifyCode(@RequestParam(value = "code") String code,
                              @RequestParam(value = "pcInfo") String pcInfo,
                              HttpServletRequest request) {
        return regCodeService.verifyCode(code, pcInfo, getIpAddr(request));
    }

    /**
     * 校验注册码 返回值为json
     */
    @GetMapping("/verify2")
    // 限制限制同一ip访问频率（需要request参数）
    @RequestLimit(count = 20, time = 60000)
    public JsonResult verifyCode2(@RequestParam(value = "code") String code,
                                  @RequestParam(value = "pcInfo") String pcInfo,
                                  @RequestParam(value = "version") String version,
                              HttpServletRequest request) {
        //同样先验证注册码
        if ( regCodeService.verifyCode(code, pcInfo, getIpAddr(request))){

        }
        return new JsonResult();
    }

    /**
     * 获取请求ip
     *
     * @return ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 获取注册码
     */
    @PostMapping("/getRegCode")
    public JsonResult getRegCode(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
        // token校验
        if (!adminService.verifyToken(session, request))
            return new JsonResult().setSuccess(false).setMessage("token校验失败");
        int current = Integer.parseInt(map.get("current"));
        int size = Integer.parseInt(map.get("size"));
        try {
            return new JsonResult().setSuccess(true).setData(regCodeService.getRegCode(current, size));
        } catch (Exception e) {
            return new JsonResult().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 获取注册码使用记录
     */
    @PostMapping("/getCodeUseRecord")
    public JsonResult getCodeUseRecord(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
        // token校验
        if (!adminService.verifyToken(session, request))
            return new JsonResult().setSuccess(false).setMessage("token校验失败");
        int current = Integer.parseInt(map.get("current"));
        int size = Integer.parseInt(map.get("size"));
        try {
            return new JsonResult().setSuccess(true).setData(regCodeService.getCodeUseRecord(current, size));
        } catch (Exception e) {
            return new JsonResult().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 更新注册码
     */
    @PostMapping("/updateRegCode")
    public JsonResult updateRegCode(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
        // token校验
        if (!adminService.verifyToken(session, request))
            return new JsonResult().setSuccess(false).setMessage("token校验失败");

        int id = Integer.parseInt(map.get("id"));
        String pcInfo = map.get("pcInfo");
        String qq = map.get("qq");
        String email = map.get("email");
        int banned = Integer.parseInt(map.get("banned"));
        RegCode regCode = regCodeService.getRegCodeById(id);
        if (regCode == null) {
            return new JsonResult().setSuccess(false).setMessage("注册码不存在！");
        }
        try {
            Timestamp expiryTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .parse(map.get("expiryTime")).getTime());
            System.out.println("执行更新注册码：id=" + id + " pcInfo=" + pcInfo + " qq=" + qq
                    + " email=" + email + " expiryTime=" + expiryTime + " banned=" + banned);
            regCode.setPcInfo(pcInfo);
            regCode.setQq(qq);
            regCode.setEmail(email);
            regCode.setbanned(banned);
            regCode.setExpiryTime(expiryTime);
            if (regCodeService.updateRegCode(regCode) > 0) {
                System.out.println("保存成功");
                return new JsonResult().setSuccess(true).setMessage("保存成功");
            }
            return new JsonResult().setSuccess(false).setMessage("保存失败！服务端出错");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new JsonResult().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 删除注册码
     */
    @PostMapping("/delRegCode")
    public JsonResult delRegCode(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
        // token校验
        if (!adminService.verifyToken(session, request))
            return new JsonResult().setSuccess(false).setMessage("token校验失败");

        int id = Integer.parseInt(map.get("id"));
        RegCode regCode = regCodeService.getRegCodeById(id);
        if (regCode == null) {
            return new JsonResult().setSuccess(false).setMessage("注册码不存在！");
        }
        try {
            System.out.println("执行删除注册码：id=" + id);
            if (regCodeService.delRegCode(id) > 0) {
                System.out.println("删除成功");
                return new JsonResult().setSuccess(true).setMessage("删除成功");
            }
            return new JsonResult().setSuccess(false).setMessage("删除失败！服务端出错");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new JsonResult().setSuccess(false).setMessage(e.getMessage());
        }
    }


}
