package org.example.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    private Map<String, User> dataSource = new HashMap<>();

    @ResponseBody
    @GetMapping("/user/signup")
    public ResponseEntity signUp(
        String email,
        String password,
        String passwordReInput
    ){
        // 패스워드 검증
        if(!password.equals(passwordReInput)){
            throw new IllegalArgumentException("패스워드 검증에 실패했습니다.");
        }

        // 사용자 생성
        User user = new User(email, password);

        // 사용자 저장
        dataSource.put(generateNewUserKey(), user);

        // 사용자 총 목록 응답
        return ResponseEntity.ok(dataSource);
    }

    private static String generateNewUserKey() {
        return UUID.randomUUID().toString();
    }


}
