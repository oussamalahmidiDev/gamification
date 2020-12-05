package com.learn.microservices.multiplication.challenge;

import com.learn.microservices.multiplication.user.User;
import com.learn.microservices.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChallengeAttemptController.class)
@AutoConfigureJsonTesters
class ChallengeAttemptControllerTest {

    @MockBean
    private IChallengeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> attemptRequest;

    @Autowired
    private JacksonTester<ChallengeAttempt> attemptResult;

    @Test
    public void postCorrectResultsTest() throws Exception {
        User user = new User(1L, "oussama");
        Long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, user.getAlias(), 3500);
        ChallengeAttempt attempt = new ChallengeAttempt(
            attemptId,
            user,
            attemptDTO.getFactorA(),
            attemptDTO.getFactorB(),
            3500,
            true
        );

        given(service
            .verifyAttempt(eq(attemptDTO)))
            .willReturn(attempt);

//        when
        MockHttpServletResponse response = mockMvc.perform(
            post("/attempts").contentType(MediaType.APPLICATION_JSON)
                .content(attemptRequest.write(attemptDTO).getJson())
        ).andReturn().getResponse();

//        then
        then(response.getStatus())
            .isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
            .isEqualTo(attemptResult.write(attempt).getJson());
    }


    @Test
    public void postInvalidResultsTest() throws Exception {
        User user = new User(1L, "oussama");
        Long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, -70, user.getAlias(), 3500);
        ChallengeAttempt attempt = new ChallengeAttempt(
            attemptId,
            user,
            attemptDTO.getFactorA(),
            attemptDTO.getFactorB(),
            3500,
            true
        );


//        when
        MockHttpServletResponse response = mockMvc.perform(
            post("/attempts").contentType(MediaType.APPLICATION_JSON)
                .content(attemptRequest.write(attemptDTO).getJson())
        ).andReturn().getResponse();

//        then
        then(response.getStatus())
            .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
