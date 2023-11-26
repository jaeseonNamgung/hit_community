package com.hit.community.repository;

import com.hit.community.entity.Board;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // update board_table set board_hits = board_hits+1 where id=?
    @Modifying
    @Query(value = "update Board b set b.boardHits=b.boardHits+1 where b.id=:id")   // entity 를 기준으로 작성한 쿼리
    void updateHits(@Param("id") Long id);

    Page<Board> findAll(Pageable pageable);

    //Page<Board> findByUserOrderByIdDesc(User user, Pageable pageable);
}
