package com.hit.community.service;

import com.hit.community.dto.BoardDTO;
import com.hit.community.entity.Board;
import com.hit.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
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
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            return board.toResponseDto();
        }
        else
            return null;
    }

    public BoardDTO update(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int size = 3;   // 한 페이지에 보여줄 글 갯수
                        // 한 페이지당 size 개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
                        // page 위치에 있는 값은 0부터 시작
        Page<Board> boards =
                boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boards.getContent());               // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boards.getTotalElements());   // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boards.getNumber());                 // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boards.getTotalPages());         // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boards.getSize());                     // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boards.hasPrevious());             // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boards.isFirst());                     // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boards.isLast());                       // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOs =
                boards.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(),
                        board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOs;
    }

    public Page<BoardDTO> findAllPaged(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("createdTime").descending());
        Page<Board> bardPage = boardRepository.findAll(pageable);

        System.out.println("boardEntities.getContent() = " + bardPage.getContent());               // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + bardPage.getTotalElements());   // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + bardPage.getNumber());                 // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + bardPage.getTotalPages());         // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + bardPage.getSize());                     // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + bardPage.hasPrevious());             // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + bardPage.isFirst());                     // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + bardPage.isLast());                       // 마지막 페이지 여부

        return bardPage.map(Board::toResponseDto);
    }
}
