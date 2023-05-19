package com.restaurant.waiter.dto;

import com.restaurant.waiter.datamodel.Status;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
