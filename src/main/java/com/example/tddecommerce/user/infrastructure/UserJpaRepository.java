package com.example.tddecommerce.user.infrastructure;

import com.example.tddecommerce.user.business.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, Long> {
}
