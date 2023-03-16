package com.restaurant.waiter.dto;

import com.restaurant.waiter.datamodel.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InformDTO {
    private long ID;
    private String group;
    private long menuID;
    private String description;
    private byte pcs;
    private Status status;
    private double unitPrice;
    private double sum;
}
