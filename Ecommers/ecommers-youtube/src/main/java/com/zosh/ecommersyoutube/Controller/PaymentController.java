package com.zosh.ecommersyoutube.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.zosh.ecommersyoutube.Exception.OrderException;
import com.zosh.ecommersyoutube.Model.Order;
import com.zosh.ecommersyoutube.Repository.OrderRepository;
import com.zosh.ecommersyoutube.Response.ApiResponse;
import com.zosh.ecommersyoutube.Response.PaymentLinkResponse;
import com.zosh.ecommersyoutube.Service.OrderService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {
    
    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    // creating payment link
    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable("orderId")Long orderId,@RequestHeader("Authorization")String jwt)throws OrderException,RazorpayException{
        
        Order order = orderService.findOrderById(orderId);

        try {
            
            RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);


        //    System.out.println("apiKey: "+apiKey);
        //    System.out.println("apiSecret: "+apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();

            paymentLinkRequest.put("amount", order.getTotalPrice()*100);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("SMS",true);
            notify.put("email",true);

            paymentLinkRequest.put("notify",notify);

            // Call Back Page
            
            // paymentLinkRequest.put("callBackUrl","http://127.0.0.1:8080/api/paymentSuccess");
            // paymentLinkRequest.put("callBackMethod","get");
            
            System.out.println("paymentLinkRequest: "+paymentLinkRequest);
            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            System.out.println("payment: "+payment);

            PaymentLinkResponse response = new PaymentLinkResponse();

            response.setPaymentLinkId(paymentLinkId);
            response.setPaymentLinkUrl(paymentLinkUrl);

            return new ResponseEntity<PaymentLinkResponse>(response,HttpStatus.CREATED);

        } catch (Exception e) {
           throw new RazorpayException(e.getMessage());
        }

    }

    @GetMapping("/paymentSuccess")
    public ResponseEntity<ApiResponse> paymentSuccess(){

        ApiResponse response = new ApiResponse();
        response.setMsg("Payment Successfull......!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name="paymentId")String paymentId,@RequestParam(name="orderId")Long orderId)throws OrderException,RazorpayException{

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);

        try {
            
            Payment payment = razorpayClient.payments.fetch(paymentId);

            if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setPaymentStatus("COMPLETED");
                order.setOrderStatus("PLACED");

                orderRepository.save(order);
            }

            ApiResponse response = new ApiResponse();
            response.setMsg("Your Order is Placed...!");
            response.setStatus(true);

            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

}
