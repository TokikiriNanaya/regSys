package top.kiriya.regSys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kiriya.regSys.entity.RegCode;
import top.kiriya.regSys.exception.AppException;
import top.kiriya.regSys.exception.AppExceptionCodeMsg;
import top.kiriya.regSys.mapper.RegCodeMapper;
import top.kiriya.regSys.service.RegCodeService;
import top.kiriya.regSys.util.JsonResult;
import top.kiriya.regSys.util.Page;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


/**
 * @author Kiriya
 * @date 2023/1/10 16:02
 */
@Service
public class RegCodeServiceImpl implements RegCodeService {

    private static final Logger logger = LoggerFactory.getLogger(RegCodeServiceImpl.class);

    @Autowired
    RegCodeMapper regCodeMapper;

    @Override
    public RegCode createCode(int expiryDays, String email,String qq) {
        //长期有效时间
        if (expiryDays == 0) expiryDays = 3650;

        //初始化注册码对象
        RegCode regCode = new RegCode();
        regCode.setEmail(email);
        regCode.setQq(qq);

        //生成随机注册码
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        regCode.setCode(uuid);

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        regCode.setCreateTime(timestamp);

        Calendar c = Calendar.getInstance();
        c.setTime(timestamp);
        //在当前时间上计算过期时间
        c.add(Calendar.DATE, expiryDays);
        timestamp = new Timestamp(c.getTimeInMillis());
        regCode.setExpiryTime(timestamp);

        //插入数据库
        int rows = regCodeMapper.insert(regCode);
        if (rows > 0) {
            return regCodeMapper.getRegCodeByCode(uuid);
        }
        return null;
    }

    @Override
    public JsonResult verifyCode(String code, String pcInfo, String ip) {
        System.out.println("开始校验注册码:" + code);
        RegCode regCode = regCodeMapper.getRegCodeByCode(code);
        // 注册码是否存在
        if (regCode == null) {
            // 注册码不存在
            logger.info("注册码不存在");
            throw new AppException(AppExceptionCodeMsg.VERIFYCODE_NOTEXIST);
        }
        // 是否banned
        if (regCode.getbanned() != 0) {
            logger.info("注册码已被禁用");
            throw new AppException(AppExceptionCodeMsg.VERIFYCODE_DISABLED);
        }

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Timestamp expiryTime = regCode.getExpiryTime();
        // 校验是否过期
        if (timestamp.getTime() > expiryTime.getTime()) {
            logger.info("注册码已过期");
            throw new AppException(AppExceptionCodeMsg.VERIFYCODE_EXPIRED);
        }
        // 校验是否首次激活
        if (regCode.getActived() == 0) {
            regCode.setPcInfo(pcInfo);
            // 设置激活时间
            regCode.setActiveTime(timestamp);
            regCode.setActived(1);
            logger.info("首次激活");
        } else if (!pcInfo.equals(regCode.getPcInfo())) {
            logger.info("电脑信息与注册码不匹配");
            throw new AppException(AppExceptionCodeMsg.VERIFYCODE_NOTMATCH);
        }
        // 设置上次使用时间
        regCode.setLastUseTime(timestamp);
        //使用次数+1
        long useCount = regCode.getUseCount();
        regCode.setUseCount(++useCount);
        // 更新注册码 插入使用记录
        if (regCodeMapper.updateById(regCode) > 0 && regCodeMapper.insertCodeUseRecord(regCode.getId(), timestamp, ip) > 0) {
            logger.info("校验成功，注册码{}",regCode.getCode());
            return new JsonResult().setSuccess(true).setMessage("校验成功");
        }
        throw new AppException(AppExceptionCodeMsg.SEVER_ERROR);
    }

    @Override
    public Boolean verifyCode2(String code, String pcInfo, String ip) {

        return null;
    }

    /**
     * 获取注册码
     */
    @Override
    public Page<RegCode> getRegCode(int current, int size) {
        Page<RegCode> page = new Page<>();
        int total = regCodeMapper.getRegCodeCount();
        page.setSize(size);
        page.setTotal(total);
        if (total == 0) {
            page.setRows(null);
            return page;
        }
        //页码为1 则从（1-1）*size=0开始查询
        //页码为2 则从（2-1）*size=size开始查询 以此类推
        page.setRows(regCodeMapper.getRegCode((current - 1) * size, size));
        page.setCurrent(current);
        return page;
    }

    /**
     * 分页获取注册码使用记录
     */
    @Override
    public Page<Map<String,String>> getCodeUseRecord(int current, int size) {
        Page<Map<String,String>> page = new Page<>();
        int total = regCodeMapper.getCodeUseRecordCount();
        page.setSize(size);
        page.setTotal(total);
        if (total == 0) {
            page.setRows(null);
            return page;
        }
        page.setRows(regCodeMapper.getCodeUseRecord((current - 1) * size, size));
        page.setCurrent(current);
        return page;
    }

    /**
     * 通过id获取注册码
     * @param id 注册码id
     */
    @Override
    public RegCode getRegCodeById(int id) {
        return regCodeMapper.selectById(id);
    }

    /**
     * 更新注册码
     */
    @Override
    public int updateRegCode(RegCode regCode) {
        return regCodeMapper.updateById(regCode);
    }

    /**
     * 删除注册码
     */
    @Override
    public int delRegCode(int id) {
        return regCodeMapper.deleteById(id);
    }
}
