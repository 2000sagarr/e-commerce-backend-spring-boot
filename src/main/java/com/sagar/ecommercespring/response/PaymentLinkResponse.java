package com.sagar.ecommercespring.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentLinkResponse {
    private String payment_link_url;
    private String payment_link_id;

    public PaymentLinkResponse() { }

    public PaymentLinkResponse(String payment_link_url, String payment_link_id) {
        super();
        this.payment_link_url = payment_link_url;
        this.payment_link_id = payment_link_id;
    }
}
