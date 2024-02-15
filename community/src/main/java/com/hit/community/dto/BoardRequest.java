package com.hit.community.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class BoardRequest {
    private Long userId;
    private Integer boardPass;
    private String boardTitle;
    private String boardContents;
    private MultipartFile boardFile;        // save.html -> Controller 파일 담는 용도
    private String originalFileName;        // 원본 파일 이름
    private String storedFileName;          // 서버 저장용 파일 이름
    private int fileAttached=0;             // 파일 첨부 여부(첨부 1, 미첨부 0)

    @Builder
    public BoardRequest(Long userId,
                        Integer boardPass,
                        String boardTitle,
                        String boardContents,
                        MultipartFile boardFile,
                        String originalFileName,
                        String storedFileName,
                        int fileAttached) {
        this.userId = userId;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardFile = boardFile;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileAttached = fileAttached;
    }
}