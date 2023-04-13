package top.kiriya.regSys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kiriya.regSys.entity.RegCode;
import top.kiriya.regSys.util.JsonResult;
import top.kiriya.regSys.util.Page;

import java.util.Map;

public interface RegCodeService  {
    /**
     * 生成注册码
     *
     * @param expiryTime 到期时间（天）
     * @param email      邮箱
     */
    RegCode createCode(int expiryTime, String email, String qq);

    /**
     * 校验注册码
     */
    JsonResult verifyCode(String code, String pcInfo, String ip,String version);

    /**
     * 分页获取注册码
     */
    Page<RegCode> getRegCode(int current, int size);

    /**
     * 分页获取注册码使用记录
     */
    Page<Map<String, String>> getCodeUseRecord(int current, int size);

    /**
     * 通过id获取注册码
     */
    RegCode getRegCodeById(int id);

    /**
     * 更新注册码
     */
    int updateRegCode(RegCode regCode);

    /**
     * 删除注册码
     */
    int delRegCode(int id);
}
