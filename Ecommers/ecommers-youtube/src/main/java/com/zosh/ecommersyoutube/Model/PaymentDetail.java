package com.zosh.ecommersyoutube.Model;

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
public class PaymentDetail {
    
    private String paymentMethod;

    private String paymentStatus;

    private String paymentId;

    private String razorpayPaymentId;

    private String razorpayPaymentLinkId;

    private String razorpayPaymentReferenceId;

    private String razorpayPaymentLinkStatus;

}
