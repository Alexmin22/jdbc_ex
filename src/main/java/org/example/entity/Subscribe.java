package org.example.entity;

import java.util.Set;

public class Subscribe {
    long id;
    String name;
    Set<SubscriberCons> subscribers;

    public Subscribe(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Subscribe() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubscriberCons> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<SubscriberCons> subscribers) {
        this.subscribers = subscribers;
    }
}
