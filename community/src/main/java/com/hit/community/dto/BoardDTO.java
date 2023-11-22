package com.hit.community.dto;


import com.hit.community.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private int boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;                  // 조회수
    private LocalDateTime boardCreatedTime; // 작성시간
    private LocalDateTime boardUpdatedTime; // 수정시간

    private MultipartFile boardFile;        // save.html -> Controller 파일 담는 용도
    private String originalFileName;        // 원본 파일 이름
    private String storedFileName;          // 서버 저장용 파일 이름
    private int fileAttached;               // 파일 첨부 여부(첨부 1, 미첨부 0)

    @Builder
    public BoardDTO(Long id,
                    String boardWriter,
                    int boardPass,
                    String boardTitle,
                    String boardContents,
                    int boardHits,
                    LocalDateTime boardCreatedTime,
                    LocalDateTime boardUpdatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = getBoardContents();
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardUpdatedTime = boardUpdatedTime;
    }

    public Board toEntity(){
        return Board.builder()
                .id(id)
                .boardWriter(boardWriter)
                .boardPass(boardPass)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .boardHits(boardHits)
                .boardCreatedTime(boardCreatedTime)
                .boardUpdatedTime(boardUpdatedTime)
                .build();
    }

}
