package com.restaurant.waiter.dto;

import com.restaurant.waiter.datamodel.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveDTO {
    @Schema(description = "Asztal ID")
    private long tableID;
    @Schema(description = "Csoport neve")
    private String groupName;
    @Schema(description = "Menü ID")
    private long menuID;
    @Schema(description = "Menü név")
    private String menuName;
    @Schema(description = "Leírás")
    private String description;
    @Schema(description = "Mennyiség")
    private byte pcs;
    @Schema(description = "Rendelés státusza")
    private Status status;
    @Schema(description = "Hely")
    private String place;
    @Schema(description = "Egységár")
    private double unitPrice;
    @Schema(description = "Összeg")
    private double sum;
}
