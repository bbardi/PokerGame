package edu.bbardi.pokerbackend.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import edu.bbardi.pokerbackend.payment.dto.ChargeRequest;
import edu.bbardi.pokerbackend.payment.dto.CurrentBalance;
import edu.bbardi.pokerbackend.payment.dto.IntentResponse;
import edu.bbardi.pokerbackend.payment.dto.Payment;
import edu.bbardi.pokerbackend.user.model.User;
import edu.bbardi.pokerbackend.user.repository.UserRepository;
import edu.bbardi.pokerbackend.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${app.stripesecret}")
    private String secretKey;
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final UserManagementService userManagementService;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public IntentResponse createIntent(ChargeRequest chargeRequest) throws StripeException {
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency(chargeRequest.getCurrency())
                .setAmount(chargeRequest.getAmount())
                .setDescription(chargeRequest.getDescription())
                .addPaymentMethodType("card")
                .build();
        return IntentResponse.builder().clientSecret(PaymentIntent.create(createParams).getClientSecret()).build();
    }

    public Boolean validatePayment(Payment payment){
        try {
            PaymentIntent intent = PaymentIntent.retrieve(payment.getPaymentIntent());
            if(intent.getStatus().equals("succeeded")) {
                User user = userManagementService.findByUsername(payment.getUsername());
                user.setBalance(user.getBalance() + (intent.getAmount()/100));
                userManagementService.save(user);
                return true;
            }
        }catch (StripeException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public CurrentBalance getBalance(Long id) {
        return CurrentBalance.builder()
                .amount(userManagementService.findByID(id).getBalance())
                .build();
    }
}
