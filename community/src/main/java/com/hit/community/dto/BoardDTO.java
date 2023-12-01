package com.hit.community.dto;


import com.hit.community.entity.Board;
import com.hit.community.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private Long userId;
    private Integer boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits=0;                        // 조회수
    private LocalDateTime boardCreatedTime;         // 작성시간
    private LocalDateTime boardUpdatedTime=null;    // 수정시간

    private MultipartFile boardFile;        // save.html -> Controller 파일 담는 용도
    private String originalFileName;        // 원본 파일 이름
    private String storedFileName;          // 서버 저장용 파일 이름
    private int fileAttached=0;             // 파일 첨부 여부(첨부 1, 미첨부 0)

    @Builder
    private BoardDTO(Long id,
                    Long userId,
                    Integer boardPass,
                    String boardTitle,
                    String boardContents,
                    int boardHits,
                    LocalDateTime boardCreatedTime,
                    LocalDateTime boardUpdatedTime) {
        this.id = id;
        this.userId = userId;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardUpdatedTime = boardUpdatedTime;
    }

    @Builder
    public BoardDTO(Long id,
                    Long userId,
                    String boardWriter,
                    String boardTitle,
                    int boardHits,
                    LocalDateTime createdTime) {
        this.id = id;
        this.userId = userId;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = createdTime;
    }

    public Board toEntity(Member member){
        return Board.builder()
                .id(id)
                .member(member)
                .boardPass(boardPass)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .boardHits(boardHits)
                .boardCreatedTime(boardCreatedTime)
                .boardUpdatedTime(boardUpdatedTime)
                .build();
    }

}
