package com.project.newstart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@SpringBootTest
@ActiveProfiles("test")
class NewstartApplicationTests {

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;


    @Test
    void contextLoads() {
    }

}
