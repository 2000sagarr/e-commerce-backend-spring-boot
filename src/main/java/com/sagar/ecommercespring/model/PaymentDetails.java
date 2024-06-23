package com.sagar.ecommercespring.model;

import com.sagar.ecommercespring.user.domain.PaymentMethod;
import com.sagar.ecommercespring.user.domain.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {

    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId​;

    public PaymentDetails() {
    }


}

