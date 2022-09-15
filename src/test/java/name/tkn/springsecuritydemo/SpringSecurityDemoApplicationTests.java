package name.tkn.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Test
    public void test01(){
// 创建密码解析器
        BCryptPasswordEncoder bCryptPasswordEncoder = new
                BCryptPasswordEncoder();
// 对密码进行加密
        String atguigu = bCryptPasswordEncoder.encode("123");
// 打印加密之后的数据
        System.out.println("加密之后数据： \t"+atguigu);
//判断原字符加密后和加密之前是否匹配
        boolean result = bCryptPasswordEncoder.matches("123", atguigu);
// 打印比较结果
        System.out.println("比较结果： \t"+result);
    }

}
