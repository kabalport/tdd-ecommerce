package com.example.tddecommerce.domain.userpoint.api;

import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/userpoints")
@RequiredArgsConstructor
public class UserPointController {
    private final UserPointService userPointService;

    @PostMapping("/charge")
    public ResponseEntity<UserPoint> chargeUserPoint(@RequestParam long userId, @RequestParam BigDecimal chargeAmount) {
        UserPoint chargedUserPoint = userPointService.chargeUserPoint(userId, chargeAmount);
        return ResponseEntity.ok(chargedUserPoint);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserPoint> getUserPoint(@PathVariable long userId) {
        UserPoint userPoint = userPointService.getUserPoint(userId);
        return ResponseEntity.ok(userPoint);
    }
}
