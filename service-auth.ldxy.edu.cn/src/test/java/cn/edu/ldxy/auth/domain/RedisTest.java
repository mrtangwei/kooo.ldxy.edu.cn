package cn.edu.ldxy.auth.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private RedisTemplate redisTemplate;

        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        @Test
        public void test1(){
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set("first","hello word");
            System.out.println(valueOperations.get("first"));
        }

        @Test
        public void test2(){
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set("second","hello everyone");
            System.out.println(stringStringValueOperations.get("second"));
        }


        @Test
        public void test3(){
            RedisConnection connection = redisConnectionFactory.getConnection();
            connection.set("second".getBytes(),"hellow second!!!!".getBytes());
            //stringStringValueOperations.set("secound","hello everyone");
            //System.out.println(stringStringValueOperations.get("secound"));
        }
}
