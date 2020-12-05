package com.learn.microservices.gamification.game.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeCard {
    @Id
    @GeneratedValue
    private Long badgeId;
    private Long userId;
    private long badgeTimestamp;

    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;
}
