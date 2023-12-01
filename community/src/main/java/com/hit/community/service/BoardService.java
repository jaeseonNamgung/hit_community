package com.hit.community.service;

import com.hit.community.dto.BoardDTO;
import com.hit.community.dto.MemberDTO;
import com.hit.community.entity.Board;
import com.hit.community.entity.Member;
import com.hit.community.repository.BoardRepository;
import com.hit.community.repository.UserRepository;
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


@Service
@RequiredArgsConstructor
public class BoardService {

    //@Autowired
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void save(BoardDTO boardDTO, MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        Board board = boardDTO.toEntity(member);
        boardRepository.save(board);
    }

    public void save(BoardDTO boardDTO){
        Optional<Member> optionalUserAccount = userRepository.findById(boardDTO.getUserId());

        if(optionalUserAccount.isPresent()){
            Member member = optionalUserAccount.get();
            boardRepository.save(boardDTO.toEntity(member));
        }
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

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()) {
            Board board = optionalBoard.get();  // get 사용 문제점 확인
            return board.toResponseDto();
        }
        else
            return null;
    }

    public BoardDTO update(BoardDTO boardDTO, MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        Board board = boardDTO.toEntity(member);
        boardRepository.save(board);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
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
