package org.cloudstats.monobankintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event<T> {
    public EventType type;
    public T data;
}
