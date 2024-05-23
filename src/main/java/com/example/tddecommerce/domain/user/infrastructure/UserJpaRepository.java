package com.example.tddecommerce.domain.user.infrastructure;

import com.example.tddecommerce.domain.user.business.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, Long> {
}
