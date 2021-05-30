package ua.lviv.iot.loggings;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public  class Console implements Loggable{

    @Override
    public void execute(JsonArray data){

        List<JsonElement> tmpdata = new ArrayList<JsonElement>();
        for (int i = 0; i< data.size();i++){
            System.out.println(data.get(i));
            tmpdata.add(data.get(i));
        }
    }
}
