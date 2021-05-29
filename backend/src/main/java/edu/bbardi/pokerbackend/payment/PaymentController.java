package edu.bbardi.pokerbackend.payment;

import com.stripe.exception.StripeException;
import edu.bbardi.pokerbackend.payment.dto.ChargeRequest;
import edu.bbardi.pokerbackend.payment.dto.CurrentBalance;
import edu.bbardi.pokerbackend.payment.dto.IntentResponse;
import edu.bbardi.pokerbackend.payment.dto.Payment;
import edu.bbardi.pokerbackend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static edu.bbardi.pokerbackend.UrlMapping.ENTITY;
import static edu.bbardi.pokerbackend.UrlMapping.PAYMENTS;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PAYMENTS)
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping(ENTITY)
    public CurrentBalance getBalance(@PathVariable Long id){
        return paymentService.getBalance(id);
    }

    @PostMapping
    public IntentResponse createIntent(@RequestBody ChargeRequest request) throws StripeException {
        return paymentService.createIntent(request);
    }

    @PutMapping
    public Boolean validatePayment(@RequestBody Payment payment){
        return paymentService.validatePayment(payment);
    }
}
