package cd.payment.toss;

import lombok.*;

//클라이언트가 보낸 PaymentDto로 받은 정보를 검증후,
//실제 토스에서 결재 요청을 하기위한 필요한 값을 포함해서 PaymenttResDto로 반환

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {
    private String payType;
    private Long amount;
    private String orderName;
    private String orderId;
    private String customerEmail;
    private String customerName;
    private String successUrl;
    private String failUrl;

    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private String createdAt;
}