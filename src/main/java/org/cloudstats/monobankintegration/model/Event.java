package org.cloudstats.monobankintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    public EventType type;
    public Object data;
}
