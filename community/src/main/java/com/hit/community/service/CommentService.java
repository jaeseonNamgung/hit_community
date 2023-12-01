package com.hit.community.service;

import com.hit.community.dto.CommentDTO;
import com.hit.community.entity.Board;
import com.hit.community.entity.Comment;
import com.hit.community.entity.Member;
import com.hit.community.repository.BoardRepository;
import com.hit.community.repository.CommentRepository;
import com.hit.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 추후 요구사항에 따라 리턴값을 다르게 바꾼다.
    // 일단 void 로
    public void save(CommentDTO commentDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());
        Optional<Member> optionalUserAccount = userRepository.findById(commentDTO.getUserId());
        // exception 으로 다르게 처리할 수 있을거같다.
        if (optionalBoard.isPresent() && optionalUserAccount.isPresent()) {
            Board board = optionalBoard.get();
            Member member = optionalUserAccount.get();
            Comment comment = commentDTO.toEntity(member, board);
            commentRepository.save(comment);
        } else {
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        // exception 으로 다르게 처리할 수 있을거같다.
        if(optionalBoard.isPresent()){
            Board boardEntity = optionalBoard.get();
            List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);
            /* EntityList -> DTOList */
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (Comment comment: commentEntityList) {
                CommentDTO commentDTO = comment.toResponseDTO();
                commentDTOList.add(commentDTO);
            }
            return commentDTOList;
        }
        return null;
    }

}
