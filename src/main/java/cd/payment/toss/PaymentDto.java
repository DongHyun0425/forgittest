package cd.payment.toss;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

//클라이언트가 해당 DTO 로 결재 요청
@Setter
@Getter
@Builder
@AllArgsConstructor
public class PaymentDto {
    @NotNull
    private PayType payType;
    @NonNull
    private Long amount;
    @NonNull
    private String orderName;

    private String yourSuccessUrl;
    private String yourFailUrl;

    public Payment toEntity(){
        return Payment.builder()
                .payType(payType)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .build();
    }
}
