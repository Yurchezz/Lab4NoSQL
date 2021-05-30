package ua.lviv.iot.services;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.models.*;
import com.azure.cosmos.CosmosDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.google.gson.JsonElement;

import ua.lviv.iot.loggings.Loggable;
import ua.lviv.iot.models.DataItem;
import ua.lviv.iot.utils.PropertyValues;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Loggable loggable;
    private JsonArray inputtedData;
    private PropertyValues propertyValues;
    private CosmosClient client;


    private final String databaseName = "Alianz";
    private final  String containerName = "Items";

    private CosmosDatabase database;
    private CosmosContainer container;
    private ArrayList<String> data;
    Gson gson = new Gson();
    public Controller(Loggable loggable,JsonArray inputtedData) throws Exception {
        this.loggable = loggable;
        this.inputtedData = inputtedData;
        this.propertyValues = new PropertyValues();

         ArrayList<String> preferredRegions = new ArrayList<String>();
        preferredRegions.add("West US");
        try {    System.out.println("Hello");
            client = new CosmosClientBuilder()
                    .endpoint(propertyValues.getHost())
                    .key(propertyValues.getPassword())
                    .preferredRegions(preferredRegions)
                    .userAgentSuffix("CosmosDBJavaQuickstart")
                    .consistencyLevel(ConsistencyLevel.EVENTUAL)
                    .buildClient();

        }catch (Exception e){
            System.out.println(e);
        }
        createDatabaseIfNotExists();
        createContainerIfNotExists();
        writArrayOfStringsToDatabase();
    }

    public void work(){
            loggable.execute(this.inputtedData);
    }


    private void createDatabaseIfNotExists() throws Exception {
        System.out.println("Create database " + databaseName + " if not exists.");

        //  Create database if not exists
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(databaseName);
        database = client.getDatabase(databaseResponse.getProperties().getId());

        System.out.println("Checking database " + database.getId() + " completed!\n");
    }
    private void createContainerIfNotExists() throws Exception {
        System.out.println("Create container " + containerName + " if not exists.");

        //  Create container if not exists
        CosmosContainerProperties containerProperties =
                new CosmosContainerProperties(containerName, "/partitionKey");

        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        container = database.getContainer(containerResponse.getProperties().getId());

        System.out.println("Checking container " + container.getId() + " completed!\n");
    }
    public void writArrayOfStringsToDatabase() throws JsonProcessingException {

//        JsonNode jsonNode = gson.toJsonTree("Json String");

        double totalRequestCharge = 0;
        int i = 0;
        for (JsonElement dataItem: this.inputtedData){

            DataItem tmpItem = new DataItem();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(gson.toJson(dataItem));
//        //perform operations on node
//        jsonNode = (JsonNode) new ObjectMapper().readTree(node.toString());
            tmpItem.setData(node);
            tmpItem.setId("item "+i);
            tmpItem.setPartitionKey("data");
            CosmosItemResponse item = container.createItem(tmpItem, new PartitionKey(tmpItem.getPartitionKey()),null);
            totalRequestCharge += item.getRequestCharge();
            i++;
        }

        System.out.println(String.format("Created %d items with total request charge of %.2f",
                    data.size(), totalRequestCharge));

    }
}
