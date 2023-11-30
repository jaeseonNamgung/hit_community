package com.hit.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.community.dto.BoardDTO;
import com.hit.community.dto.UserDTO;
import com.hit.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class RestBoardController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;

    // Create a new board
    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody Map<String, Object> requestData) {
        BoardDTO boardDTO = objectMapper.convertValue(requestData.get("boardDTO"), BoardDTO.class);
        UserDTO userDTO = objectMapper.convertValue(requestData.get("userDTO"), UserDTO.class);

        System.out.println("---");
        System.out.println("boardDTO = " + boardDTO);
        System.out.println("userDTO = " + userDTO);
        System.out.println("---");

        boardService.save(boardDTO, userDTO);
        return ResponseEntity.ok(boardDTO);
    }

//    // Get all boards
//    @GetMapping
//    public ResponseEntity<List<BoardDTO>> getAllBoards() {
//        List<BoardDTO> boardDTOList = boardService.findAll();
//        return ResponseEntity.ok(boardDTOList);
//    }

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
    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id,
                                                @RequestBody BoardDTO boardDTO,
                                                @RequestBody UserDTO userDTO) {
        BoardDTO existingBoard = boardService.findById(id);
        if (existingBoard == null) {
            return ResponseEntity.notFound().build();
        }
        int hits = existingBoard.getBoardHits();
        boardDTO.setId(id); boardDTO.setBoardHits(hits);
        BoardDTO updatedBoard = boardService.update(boardDTO, userDTO);
        return ResponseEntity.ok(updatedBoard);
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
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), boardPage.getTotalPages());

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return ResponseEntity.ok(boardPage);
    }

}
