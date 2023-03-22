package com.restaurant.waiter.datamodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Egy rendelés
 */

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@Schema(description = "Rendelés")
@Entity
//@Table(name = "orderTable")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long ID;
    @Column(name = "table_id")
    @Schema(description = "Asztal ID")
    private long tableID;
    @Schema(description = "Csoport neve")
    @Column(name = "group_name")
    @NotBlank(message = "error.order.group.notset")
    @Size(max = 42, min = 3, message = "error.order.group.long")
    private String groupName;
    @Column(name = "menu_id")
    @Schema(description = "Menü ID")
    private long menuID;
    @Column(name = "menu_name")
    @Schema(description = "Menü név")
    private String menuName;
    @Column(name = "order_desc")
    @Schema(description = "Leírás")
    private String description;
    @Schema(description = "Mennyiség")
    @Min(value = 1, message = "error.order.pcs.few")
    private byte pcs;
    @Schema(description = "Rendelés státusza")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Schema(description = "Hely")
    @Size(min = 3, max = 84, message = "error.order.place.long")
    private String place;
    @Column(name = "unit_price")
    @Schema(description = "Egységár")
    @Min(value = 7)
    private double unitPrice;
    @Schema(description = "Összeg")
    @Min(value = 7)
    private double sum;
    @Column(name = "created_time_stamp")
    @Schema(description = "Létrehozás időbélyege")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Timestamp createdTimeStamp;
    @Column(name = "modified_time_stamp")
    @Schema(description = "Módosítás időbélyege")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Timestamp modifiedTimeStamp;

    @Builder
    public OrderTable(long tableID, String groupName, long menuID, String menuName, String description, byte pcs, Status status, String place, double unitPrice, double sum) {
        this.tableID = tableID;
        this.groupName = groupName;
        this.menuID = menuID;
        this.menuName = menuName;
        this.description = description;
        this.pcs = pcs;
        this.status = status;
        this.place = place;
        this.unitPrice = unitPrice;
        this.sum = sum;
    }
}
