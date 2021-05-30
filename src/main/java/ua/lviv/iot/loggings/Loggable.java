package ua.lviv.iot.loggings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public interface Loggable {
    public void execute(JsonArray data);
}
