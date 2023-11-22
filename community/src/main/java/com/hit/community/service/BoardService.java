package com.hit.community.service;

import com.hit.community.dto.BoardDTO;
import com.hit.community.entity.Board;
import com.hit.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// dto <-> entity

@Service
@RequiredArgsConstructor
public class BoardService {

    //@Autowired
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
    }

    public List<BoardDTO> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(Board board : boardList){
            boardDTOList.add(board.toResponseDto());
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {

        return null;
    }
}
