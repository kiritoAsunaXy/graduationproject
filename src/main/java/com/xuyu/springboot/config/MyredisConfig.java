package com.xuyu.springboot.config;


import com.xuyu.springboot.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class MyredisConfig {

    @Bean
    public RedisTemplate<Object, User> empRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, User> template = new RedisTemplate<Object, User>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<User> ser = new Jackson2JsonRedisSerializer<User>(User.class);
        template.setDefaultSerializer(ser);
        return template;
    }


    @Bean
    public RedisCacheManager empCacheManage(RedisTemplate<Object, User> empRedisTemplate){
        RedisCacheManager cacheManager=new RedisCacheManager(empRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }

}
