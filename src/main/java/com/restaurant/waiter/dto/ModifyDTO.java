package com.restaurant.waiter.dto;

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
public class ModifyDTO {
    @Schema(description = "Menü ID")
    @Min(value = 0)
    private long menuID;
    @Schema(description = "Leírás")
    @NotBlank(message = "error.order.group.notset")
    @Size(max = 200, min = 3, message = "error.order.group.long")
    private String description;
    @Schema(description = "Mennyiség")
    @Min(value = 1, message = "error.order.pcs.few")
    private byte pcs;
}
