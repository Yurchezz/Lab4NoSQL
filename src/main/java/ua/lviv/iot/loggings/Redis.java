package ua.lviv.iot.loggings;

import com.google.gson.JsonArray;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicInteger;

public class Redis implements Loggable{

    private Jedis jedis;
    public Redis(Jedis jedis ){
        this.jedis = jedis;
    }
    @Override
    public void execute(JsonArray data){
        AtomicInteger i = new AtomicInteger();
        data.forEach(jsonElement -> {
            jedis.rpush( i.toString(), jsonElement.toString());
            i.getAndIncrement();
        });

    }
}
