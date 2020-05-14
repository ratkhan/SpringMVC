package com;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceVO {

    @NotBlank(message = "userId cannot be empty")
    private String userId;

    @Min(value=10, message="Minimum amount is 10")
    @Max(value=50, message="Maximum amount is 50")
    private Integer amount;
}
