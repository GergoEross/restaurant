package com.restaurant.waiter.dto;

import com.restaurant.waiter.datamodel.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SaveDTO {
    @Schema(description = "Asztal ID")
    @Min(value = 0)
    private long tableID;
    @Schema(description = "Csoport neve")
    @NotBlank(message = "error.order.group.notset")
    @Size(max = 42, min = 3, message = "error.order.group.long")
    private String groupName;
    @Schema(description = "Menü ID")
    @Min(value = 0)
    private long menuID;
    @Schema(description = "Menü név")
    @NotBlank(message = "error.order.group.notset")
    @Size(max = 100, min = 3, message = "error.order.group.long")
    private String menuName;
    @Schema(description = "Leírás")
    @NotBlank(message = "error.order.group.notset")
    @Size(max = 200, min = 3, message = "error.order.group.long")
    private String description;
    @Schema(description = "Mennyiség")
    @Min(value = 1, message = "error.order.pcs.few")
    private byte pcs;
    @Schema(description = "Rendelés státusza")
    private Status status;
    @Schema(description = "Hely")
    @Size(min = 3, max = 84, message = "error.order.place.long")
    private String place;
    @Schema(description = "Egységár")
    @Min(value = 7)
    private double unitPrice;
    @Schema(description = "Összeg")
    @Min(value = 7)
    private double sum;
}
