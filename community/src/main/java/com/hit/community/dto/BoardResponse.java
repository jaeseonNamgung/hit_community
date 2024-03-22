package com.hit.community.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardResponse {
    private Long id;
    private Long userId;
    private String boardTitle;
    private String boardContents;
    private int boardHits;                      // 조회수
    private LocalDateTime boardCreatedTime;     // 작성시간
    private LocalDateTime boardUpdatedTime;     // 수정시간
    private String originalFileName;        // 원본 파일 이름
    private String storedFileName;          // 서버 저장용 파일 이름
    private int fileAttached;               // 파일 첨부 여부(첨부 1, 미첨부 0)

    @Builder
    public BoardResponse(Long id,
                         Long userId,
                         String boardTitle,
                         String boardContents,
                         int boardHits,
                         LocalDateTime boardCreatedTime,
                         LocalDateTime boardUpdatedTime,
                         String originalFileName,
                         String storedFileName,
                         int fileAttached) {
        this.id = id;
        this.userId = userId;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardUpdatedTime = boardUpdatedTime;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileAttached = fileAttached;
    }
}
