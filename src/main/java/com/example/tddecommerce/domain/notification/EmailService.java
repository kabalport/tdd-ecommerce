package com.example.tddecommerce.domain.notification;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailService {
    public void sendOrderConfirmationEmail(String email, ProductOrder order) {

    }
}
