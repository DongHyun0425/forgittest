package cd.payment.toss;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl {
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final TossPaymentConfig tossPaymentConfig;
    public Payment requestTossPayment(Payment payment, String username){
        System.out.println("hello");
        Member member = memberRepository.findByUsername(username);
        System.out.println(username);
        payment.setCustomer(member);
        return paymentRepository.save(payment);
    }

    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) throws JSONException {
        Payment payment = verifyPayment(orderId, amount);
        PaymentSuccessDto result = requestPaymentAccept(paymentKey, orderId, amount);
        //이거 통과하면 이미 결제는 정상적으로 되었다는거임
        System.out.println("이거 통과하면 이미 결제는 정상적으로 되었다는거임");
        payment.setPaymentKey(paymentKey);//추후 결제 취소 / 결제 조회
        payment.setPaySuccessYN(true);
        Member member = payment.getCustomer();
        member.updateBalance(amount);
        return result;
    }


    private Payment verifyPayment(String orderId, Long amount) {
        System.out.println(orderId);
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new RuntimeException("결제요청 정보가 없습니다.");
        });
        if (!payment.getAmount().equals(amount)) {
            throw new RuntimeException("중간에 악의적으로 결재 금액 정보가 바뀌었습니다.");
        }
        return payment;

    }

    private PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        // Map으로 변경
        Map<String, Object> params = new HashMap<>();
        //JSONObject params = new JSONObject();//키/값 쌍을 문자열이 아닌 오브젝트로 보낼 수 있음
        params.put("orderId", orderId);
        params.put("amount", amount);

        System.out.println(paymentKey);
        System.out.println(orderId);
        System.out.println(amount);

        PaymentSuccessDto result = null;
        /*//post요청 (url , HTTP객체 ,응답 Dto)
            result = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                    new HttpEntity<>(params, headers),
                    PaymentSuccessDto.class);
*/
            result = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                    new HttpEntity<>(params, headers),
                    PaymentSuccessDto.class);

        return result;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTestSecretKey() + ":").getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;

    }
}
