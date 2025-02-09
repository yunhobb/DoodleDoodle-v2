package com.doodledoodle.backend.result.repository;

import com.doodledoodle.backend.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaResultRepository extends JpaRepository<Result, UUID>, ResultRepositoryStandard {
    @Query("SELECT r FROM Result r LEFT JOIN FETCH r.game g LEFT JOIN FETCH g.dictionary WHERE r.draw.id = :drawId ORDER BY r.similarity DESC")
    List<Result> findByDrawId(final UUID drawId);

    @Query("SELECT r FROM Result r LEFT JOIN FETCH r.game g LEFT JOIN FETCH g.dictionary WHERE r.game.id = :gameId ORDER BY r.similarity DESC")
    List<Result> findByGameId(final UUID gameId);
}
