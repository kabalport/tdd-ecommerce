package com.example.tddecommerce.userpoint.business.component;

import com.example.tddecommerce.userpoint.IUserPointRepository;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPointReader {

    private final IUserPointRepository iUserPointRepository;

    public UserPointReader(IUserPointRepository iUserPointRepository) {
        this.iUserPointRepository = iUserPointRepository;
    }

    public UserPoint readByUserId(Long userId) {
        // 유저 잔금 조회
        Optional<UserPoint> result = iUserPointRepository.findByUserUserId(userId);
        // 유저의 잔금 결과 가져오기. 없으면 잔금을 0으로 반환하기
        return result.orElseGet(()->UserPoint.empty());

    }
}
