package com.restaurant.waiter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayDTO {
    long tableID;
    String group;
}
