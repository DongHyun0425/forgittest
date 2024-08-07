package cd.payment.toss;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPaymentRepository extends JpaRepository<Payment,Long> {

    //주문id찾기
    Optional<Payment> findByOrderId(String orderId);

    //paymnetKye와 고객email로 Paymnet객체 찾기
    Optional<Payment> findByPaymentKeyAndCustomer_Email(String paymentKey, String email);
    //내 주문 전체 조회
    Slice<Payment> findAllByCustomer_Email(String email, Pageable pageable);
}
