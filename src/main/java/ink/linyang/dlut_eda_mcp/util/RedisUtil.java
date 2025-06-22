package ink.linyang.dlut_eda_mcp.util;


import jakarta.annotation.Resource;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    public static final String REDIS_KEY_PREFIX = "dlut-eda-mcp:"; // Redis key前缀
    public static final String XC_PREFIX = REDIS_KEY_PREFIX + "xc"; // 形策相关的Redis key前缀
    public static final String XC_COURSE_LIST_PREFIX = XC_PREFIX + ":courseList"; // 形策开课清单的Redis key
    public static final String User_API_KEY_PREFIX = REDIS_KEY_PREFIX + "user:apiKey"; // 用户

    @Resource
    private RedissonClient redissonClient;

    // 写入字符串（可设置过期时间）
    public void setString(String key, String value, long timeout, TimeUnit unit) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        if (timeout > 0 && unit != null) {
            Duration duration = Duration.ofMillis(unit.toMillis(timeout));
            bucket.set(value, duration);
        } else if (timeout == -1) {
            bucket.set(value); // 如果过期时间为-1, 则永久存储
        }
    }

    public void setString(String key, String value, long timeout) {
        // 默认使用秒为单位设置过期时间
        setString(key, value, timeout, TimeUnit.SECONDS);
    }

    public void setString(String key, String value) {
        // 不设置过期时间，永久存储
        setString(key, value, -1, null);
    }


    // 读取字符串
    public String getString(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    // 写入 Set
    public void setStringToSet(String key, String value, long timeout, TimeUnit unit) {
        RSet<String> rSet = redissonClient.getSet(key);
        rSet.add(value);
        if (timeout > 0 && unit != null) {
            Duration duration = Duration.ofMillis(unit.toMillis(timeout));
            rSet.expire(duration);
        }
    }

    // 获取 Set
    public Set<String> getSet(String key) {
        RSet<String> rSet = redissonClient.getSet(key);
        return rSet.readAll();
    }

    // 删除 key
    public boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }

    // 判断 key 是否存在
    public boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }
}