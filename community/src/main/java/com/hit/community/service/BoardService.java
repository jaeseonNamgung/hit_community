package com.hit.community.service;

import com.hit.community.dto.BoardDTO;
import com.hit.community.dto.MemberRequest;
import com.hit.community.dto.MemberResponse;
import com.hit.community.entity.Board;
import com.hit.community.entity.BoardFile;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import com.hit.community.repository.BoardFileRepository;
import com.hit.community.repository.BoardRepository;
import com.hit.community.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    //@Autowired
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDTO boardDTO, MemberResponse memberResponse) throws IOException {
        //
        Member member = Member.builder()
                .name(memberResponse.name())
                .email(memberResponse.email())
                .profile(memberResponse.profile())
                .role(Role.USER)
                .build();

        if (boardDTO.getFileAttached() == 0) {
            // 첨부 파일 없음.
            Board board = boardDTO.toEntity(member);
            boardRepository.save(board);
        } else {
            // 첨부 파일 있음.
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            MultipartFile multipartFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = multipartFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "/Users/hyun/Documents/test" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
            multipartFile.transferTo(new File(savePath)); // 5.
            Board boardEntity = boardDTO.toEntity(member);
            Long savedId = boardRepository.save(boardEntity).getId();
            Board board = boardRepository.findById(savedId).get();

            BoardFile boardFile = BoardFile.toBoardFileEntity(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFile);
        }

        Board board = boardDTO.toEntity(member);
        boardRepository.save(board);
    }

    public void save(BoardDTO boardDTO) {
        Optional<Member> optionalUserAccount = memberRepository.findById(boardDTO.getUserId());

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
    public void updateHits(Long id, HttpSession session) {
        HashSet<Long> viewed = (HashSet<Long>) session.getAttribute("viewed");
        if (viewed == null) {
            viewed = new HashSet<>();
        }

        if (!viewed.contains(id)) {
            boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
            boardRepository.updateHits(id);
            viewed.add(id);
            session.setAttribute("viewed", viewed);
        }
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

    public BoardDTO update(BoardDTO boardDTO, MemberRequest memberRequest) {
        //
        Member member = Member.builder()
                .name(memberRequest.name())
                .email(memberRequest.email())
                .profile(memberRequest.profile())
                .role(Role.USER)
                .build();
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
