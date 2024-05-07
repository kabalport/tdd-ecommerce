package com.example.tddecommerce.order.business.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long customerId; // 고객 ID

    private String userId; // 고객 ID
    private String name;     // 고객 이름
    private String email;    // 고객 이메일 주소
    private String phone;    // 고객 전화번호


}
