package com.example.tddecommerce.notification;

import com.example.tddecommerce.order.business.model.ProductOrder;
import com.example.tddecommerce.user.business.domain.User;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    public void sendOrderConfirmationEmail(User user, ProductOrder order) {

    }
}
