package com.hit.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_file_table")
public class BoardFile extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static BoardFile toBoardFileEntity(Board board, String originalFileName, String storedFileName) {
        return new BoardFile(board, originalFileName, storedFileName);
    }
    
    public BoardFile(Board board, String originalFileName, String storedFileName) {
        this.board = board;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }
}
