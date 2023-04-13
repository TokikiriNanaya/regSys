package top.kiriya.regSys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.kiriya.regSys.mapper")
public class RegSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegSysApplication.class, args);
    }

}
