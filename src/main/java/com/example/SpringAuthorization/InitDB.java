package com.example.SpringAuthorization;

import com.example.SpringAuthorization.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        public void dbInit1() {
            User user = createUser("loose", "1234", "ROLE_ADMIN");
            em.persist(user);
        }

        private User createUser(String username, String password, String role){
            User user = new User();
            user.setUsername(username);
            String encPassword = bCryptPasswordEncoder.encode(password);
            //user.setPassword(encPassword);
            user.setPassword(bCryptPasswordEncoder.encode("1234"));
            user.setRoles(role);
            return user;
        }

    }
}
