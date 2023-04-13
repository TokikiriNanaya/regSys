package top.kiriya.regSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.kiriya.regSys.entity.Admin;

import java.util.List;

/**
 * 继承 BaseMapper 使用 Mybatis Plus 自动实现curd
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin")
    public List<Admin> getAllAdmin();

    @Select("select * from admin where username=#{username}")
    public Admin getAdminByUsername(String username);

}
