package com.restaurant.waiter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveDTO {
    private long tableID;
    private String group;
    private long menuID;
    private String description;
    private byte pcs;
}
