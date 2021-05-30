package ua.lviv.iot.models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataItem  {
    private String id;
    private String partitionKey;
    private ObjectNode data;

    public DataItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }
}
