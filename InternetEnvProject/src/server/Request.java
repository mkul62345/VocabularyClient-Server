package server;

import java.util.Map;

public class Request implements java.io.Serializable{
    private String Action;

    private final Map<String, Object> Head;
    private final Map<String, Object> Body;

    public Request(String action, Map<String,Object> head, Map<String,Object> body){
        this.Action = action;
        this.Head = head;
        this.Body = body;
    }

    public String getAction(){
        return this.Action;
    }

    public Map<String, Object> getHead(){
        return this.Head;
    }

    public Map<String, Object> getBody(){
        return this.Body;
    }

    public void setAction(String inputAction) {
        this.Action = inputAction;
    }



}
