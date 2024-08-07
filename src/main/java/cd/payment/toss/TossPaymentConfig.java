package cd.payment.toss;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {
    @Value("${payment.toss.test_client_api_key}")
    private String testClientAipKey;

    @Value("${payment.toss.test_secrete_api_key}")
    private String testSecretKey;

    @Value("${payment.toss.success.url}")
    private String successUrl;

    @Value("${payment.toss.fail.url}")
    private String failUrl;

    public static final String URL = "https://api.tosspayments.com/v1/payments/";
}
