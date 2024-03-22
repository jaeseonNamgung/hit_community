package com.hit.community.repository;

import com.hit.community.entity.Board;
import com.hit.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<Comment> findAllByBoardOrderByIdDesc(Board board);
}
