package ua.lviv.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import redis.clients.jedis.Jedis;
import ua.lviv.iot.config.RedisConfig;
import ua.lviv.iot.loggings.Console;
import ua.lviv.iot.loggings.Redis;
import ua.lviv.iot.services.Controller;
import ua.lviv.iot.services.Parser;
import ua.lviv.iot.utils.StrategyOptions;

public class Main {


    public static void main(String[] args) {


        try {
            String url = "https://controllerdata.lacity.org/resource/pazn-qyym.json";
            Jedis jedis = RedisConfig.getRedisClient(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Response from Redis:");
            System.out.println(jedis.ping());
            System.out.println("Specify strategy (remote vs console): ");
            String inputtedData = null;
            JsonArray parsedDate = Parser.parse(url);
            inputtedData = reader.readLine();


            System.out.println(inputtedData);
            Controller controller;

            if (inputtedData.toUpperCase().equals(StrategyOptions.REMOTE.toString())){
                System.out.println("Remote have been chosen");
                Redis redis2 = new Redis(jedis);
                controller = new Controller(redis2,parsedDate);
            }else {
                System.out.println("Console have been chosen");
                Console console = new Console();
              controller = new Controller(console,parsedDate);
            }

           controller.work();


        }catch(Exception e){
            e.printStackTrace();
            System.err.println(String.format("Something went wrong, %s", e));
        }finally {
            System.out.println("Closing the client");
        }

        System.exit(0);
    }
}