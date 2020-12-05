package com.learn.microservices.multiplication.challenge;

import com.learn.microservices.multiplication.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeAttempt {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private int factorA, factorB;
    private int resultAttempt;

    private boolean isCorrect;
}
