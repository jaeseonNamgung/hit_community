package com.hit.community.service;

import com.hit.community.dto.BoardDTO;
import com.hit.community.dto.CommentDTO;
import com.hit.community.dto.UserDTO;
import com.hit.community.entity.Board;
import com.hit.community.entity.Comment;
import com.hit.community.entity.UserAccount;
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

//    public Long save(UserDTO userDTO, CommentDTO commentDTO) {
//        /* 부모엔티티(BoardEntity) 조회 */
//        Optional<Board> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
//        if (optionalBoardEntity.isPresent()) {
//            Board baord = optionalBoardEntity.get();
//            Comment comment = commentDTO.toEntity(userDTO.toEntity(), baord);
//            return commentRepository.save(comment).getId();
//        } else {
//            return null;
//        }
//    }

    public Long save(CommentDTO commentDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());
        Optional<UserAccount> optionalUserAccount = userRepository.findById(commentDTO.getUserId());

        if (optionalBoard.isPresent() && optionalUserAccount.isPresent()) {
            Board baord = optionalBoard.get();
            UserAccount userAccount = optionalUserAccount.get();
            Comment comment = commentDTO.toEntity(userAccount, baord);
            return commentRepository.save(comment).getId();
        } else {
            return null;
        }
    }

//    public List<CommentDTO> findAll(Long boardId) {
//        Board boardEntity = boardRepository.findById(boardId).get();
//        List<Comment> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
//        /* EntityList -> DTOList */
//        List<CommentDTO> commentDTOList = new ArrayList<>();
//        for (Comment comment: commentEntityList) {
//            //CommentDTO commentDTO = CommentDTO.toCommentDTO(comment, boardId);
//            CommentDTO commentDTO = comment.toResponseDTO();
//            commentDTOList.add(commentDTO);
//        }
//        return commentDTOList;
//    }

}
