package com.restaurant.waiter.datamodel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Egy rendelés
 */

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Order {
    private long ID;
    private long tableID;
    private String group;
    private long menuID;
    private String menuName;
    private String description;
    private byte pcs;
    private Status status;
    private String place;
    private double unitPrice;
    private double sum;
    private Timestamp createdTimeStamp;
    private Timestamp modifiedTimeStamp;

    @Builder
    public Order(long ID, long tableID, String group, long menuID, String menuName, String description, byte pcs, Status status, String place, double unitPrice, double sum, Timestamp createdTimeStamp, Timestamp modifiedTimeStamp) {
        this.ID = ID;
        this.tableID = tableID;
        this.group = group;
        this.menuID = menuID;
        this.menuName = menuName;
        this.description = description;
        this.pcs = pcs;
        this.status = status;
        this.place = place;
        this.unitPrice = unitPrice;
        this.sum = sum;
        this.createdTimeStamp = createdTimeStamp;
        this.modifiedTimeStamp = modifiedTimeStamp;
    }
}
