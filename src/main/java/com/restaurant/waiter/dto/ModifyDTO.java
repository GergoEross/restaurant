package com.restaurant.waiter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyDTO {
    private long menuID;
    private String description;
    private byte pcs;
}
