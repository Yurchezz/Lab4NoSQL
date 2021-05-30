package ua.lviv.iot.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisConfig {

    public static Jedis getRedisClient(boolean useSsl){

        String cacheHostname = "yurchezz-cash.redis.cache.windows.net";
        String cachekey = "BjKxAR75oY6lLt8YO1gdJMe9wjrJ4F7gFy0Jk9bZu14=";

        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        JedisShardInfo shardInfo = new JedisShardInfo(cacheHostname, 6380, useSsl);
        shardInfo.setPassword(cachekey); /* Use your access key. */

        return new Jedis(shardInfo);
    }
}
