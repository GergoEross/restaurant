package com.restaurant.waiter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ModifyDTO {
    private long menuID;
    private String description;
    private byte pcs;
}
