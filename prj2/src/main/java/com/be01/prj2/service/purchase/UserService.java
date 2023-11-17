package com.be01.prj2.service.purchase;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.repository.purchase.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getUserBudget(Long userId) {
        Customer user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 예산 반환
        return user.getBudget();
    }
}
