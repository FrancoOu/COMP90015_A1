package COMP90015.unimelb.edu.Request;

import COMP90015.unimelb.edu.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    private RequestType requestType;
    private Item item;


    public Request(@JsonProperty("requestType") RequestType requestType, @JsonProperty("item") Item item){
        this.requestType = requestType;
        this.item = item;
    }

    public Request(Item item){
        this.requestType = RequestType.SEARCH;
        this.item = item;

    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", item=" + item +
                '}';
    }
}
