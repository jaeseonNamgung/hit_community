package com.hit.community.controller;

import com.hit.community.dto.BoardDTO;
import com.hit.community.service.BoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class RestBoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDTO> saveBoard(@RequestBody BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return ResponseEntity.ok(boardDTO);
    }

    // Get a single board by ID
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id,
                                                 @PageableDefault(page = 1) Pageable pageable,
                                                 Model model) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return boardDTO != null ? ResponseEntity.ok(boardDTO) : ResponseEntity.notFound().build();
    }
    

    // Update a board
    @GetMapping("/test/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id,
                                                 @PageableDefault(page = 1) Pageable pageable,
                                                 Model model, HttpSession session) {
        boardService.updateHits(id, session);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return boardDTO != null ? ResponseEntity.ok(boardDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }

    // GET /boards?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<BoardDTO>> getAllBoards(Pageable pageable, Model model) {
        Page<BoardDTO> boardPage = boardService.findAllPaged(pageable);
        int blockLimit = 3; // 게시글 3개로 제한
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), boardPage.getTotalPages());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return ResponseEntity.ok(boardPage);
    }

}
