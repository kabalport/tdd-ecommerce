package com.example.tddecommerce.order;

public class Customer {
    private Long customerId; // 고객 ID
    private String name;     // 고객 이름
    private String email;    // 고객 이메일 주소
    private String phone;    // 고객 전화번호

    // 기본 생성자
    public Customer() {
    }

    // 모든 필드를 포함하는 생성자
    public Customer(Long customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // 고객 ID를 반환하는 getter 메소드
    public Long getCustomerId() {
        return customerId;
    }

    // 고객 ID를 설정하는 setter 메소드
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    // 고객 이름을 반환하는 getter 메소드
    public String getName() {
        return name;
    }

    // 고객 이름을 설정하는 setter 메소드
    public void setName(String name) {
        this.name = name;
    }

    // 고객 이메일을 반환하는 getter 메소드
    public String getEmail() {
        return email;
    }

    // 고객 이메일을 설정하는 setter 메소드
    public void setEmail(String email) {
        this.email = email;
    }

    // 고객 전화번호를 반환하는 getter 메소드
    public String getPhone() {
        return phone;
    }

    // 고객 전화번호를 설정하는 setter 메소드
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
