package com.sagar.ecommercespring.controller;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sagar.ecommercespring.exception.OrderException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Cart;
import com.sagar.ecommercespring.model.CartItem;
import com.sagar.ecommercespring.model.Order;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.CartRepository;
import com.sagar.ecommercespring.repository.OrderRepository;
import com.sagar.ecommercespring.response.ApiResponse;
import com.sagar.ecommercespring.response.PaymentLinkResponse;
import com.sagar.ecommercespring.service.OrderService;
import com.sagar.ecommercespring.service.UserService;
import com.sagar.ecommercespring.user.domain.OrderStatus;
import com.sagar.ecommercespring.user.domain.PaymentStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${rozarpay.api.key}")
    private String apiKey;

    @Value("${rozarpay.api.secret}")
    private String apiSecret;

    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public PaymentController(OrderService orderService,UserService userService,OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderService=orderService;
        this.userService=userService;
        this.orderRepository=orderRepository;
        this.cartRepository = cartRepository;
    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
                                                                 @RequestHeader("Authorization")String jwt)
            throws RazorpayException, UserException, OrderException {

        Order order=orderService.findOrderById(orderId);
        try {
            // Instantiate a Razorpay client with your key ID and secret
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            // Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",order.getTotalPrice()* 100);
            paymentLinkRequest.put("currency","INR");


            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name",order.getUser().getFirstName()+" "+order.getUser().getLastName());
            customer.put("contact",order.getUser().getMobile());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable",true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            // Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res=new PaymentLinkResponse(paymentLinkUrl,paymentLinkId);

            PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);

            order.setOrderId(fetchedPayment.get("order_id"));
            orderRepository.save(order);

            User user=userService.findUserProfileByJwt(jwt);

            Cart cart = cartRepository.findByUserId(user.getId());
            if (cart != null) {
                Set<CartItem> cartItems = cart.getCartItems();
                cartItems.clear();
                cart.setTotalPrice(0);
                cart.setTotalItem(0);
                cartRepository.save(cart);
            }


            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

        } catch (RazorpayException e) {
            throw new RazorpayException(e.getMessage());
        }


//		order_id
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String paymentId, @RequestParam("order_id")Long orderId) throws RazorpayException, OrderException {
        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        Order order =orderService.findOrderById(orderId);

        try {


            Payment payment = razorpay.payments.fetch(paymentId);

            if(payment.get("status").equals("captured")) {

                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
                orderRepository.save(order);
            }
            ApiResponse res=new ApiResponse("your order get placed", true);
            return new ResponseEntity<>(res,HttpStatus.OK);

        } catch (Exception e) {
            new RedirectView("https://shopwithzosh.vercel.app/payment/failed");
            throw new RazorpayException(e.getMessage());
        }

    }

}
