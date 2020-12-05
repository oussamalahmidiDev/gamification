package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(long userId);
}
