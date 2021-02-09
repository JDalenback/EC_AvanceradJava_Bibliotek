package models;

import java.io.Serializable;

public class LibraryEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    public String getEvent() {
        return event;
    }

    private String event;
    private Object data;

    public LibraryEvent() {
    }

    public LibraryEvent(String event, Object data) {
        this.event = event;
        this.data = data;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
