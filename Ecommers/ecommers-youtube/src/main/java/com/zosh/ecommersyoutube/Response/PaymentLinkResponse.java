package com.zosh.ecommersyoutube.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private String paymentLinkId;
    private String paymentLinkUrl;
}
