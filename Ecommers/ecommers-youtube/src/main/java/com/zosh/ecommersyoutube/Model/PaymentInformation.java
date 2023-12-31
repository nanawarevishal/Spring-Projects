package com.zosh.ecommersyoutube.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentInformation {

    private String cardHolderName;

    private String cardNumber;

    private LocalDateTime expirationDate;

    private String cvv;
}
