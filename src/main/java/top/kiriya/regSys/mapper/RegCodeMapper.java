package top.kiriya.regSys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.kiriya.regSys.entity.RegCode;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface RegCodeMapper extends BaseMapper<RegCode> {
    @Select("select * from reg_code where code=#{code}")
    public RegCode getRegCodeByCode(String code);
    @Insert("insert into code_use_record(code_id,use_time,ip) values(#{codeId},#{useTime},#{ip})")
    public int insertCodeUseRecord(long codeId, Timestamp useTime, String ip);
    @Select("select * from reg_code limit #{start},#{size}")
    public List<RegCode> getRegCode(int start, int size);
    @Select("select count(*) from reg_code")
    public int getRegCodeCount();
    @Select("SELECT cur.code_id, rc.code, cur.use_time, cur.ip, rc.email \n" +
            "FROM reg_code AS rc, code_use_record AS cur \n" +
            "WHERE rc.id = cur.code_id \n" +
            "LIMIT #{start},#{size}")
    public List<Map<String,String>> getCodeUseRecord(int start, int size);
    @Select("select count(*) from code_use_record")
    public int getCodeUseRecordCount();

}
