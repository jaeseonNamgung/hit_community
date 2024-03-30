package com.hit.community.service;


import com.hit.community.constant.ActivityCategory;
import com.hit.community.constant.SearchCategory;
import com.hit.community.dto.SearchBoardResponse;
import com.hit.community.entity.Board;
import com.hit.community.entity.Category;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import com.hit.community.repository.SearchQueryDslRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchQueryDslRepository repository;
    @InjectMocks
    private SearchService service;


    @Test
    @DisplayName("검색 서비스 테스트")
    void searchTest () throws Exception
    {
        //given
        String text = "ti";
        PageRequest pageRequest =
                PageRequest.of(0, 3, Sort.by("createdTime").descending());
        List<Board> boardList = saveMemberAndBoard();
        given(repository.searchAndPaging(any(), any(), any())).willReturn(new PageImpl<>(boardList, pageRequest, 10));
        //when
        Page<SearchBoardResponse> search = service.search(SearchCategory.TITLE, text, pageRequest);
        //then
        assertThat(search.isFirst()).isTrue();
        assertThat(search.getTotalElements()).isEqualTo(10);
        assertThat(search.getTotalPages()).isEqualTo(4);
        assertThat(search.getSize()).isEqualTo(3);
        assertThat(search.getNumber()).isEqualTo(0);

        then(repository).should().searchAndPaging(any(), any(), any());
    }

    private List<Board> saveMemberAndBoard(){
        List<Board> boardList = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            Member member = createMember(i);
            Board board = createBoard(member, i);
            boardList.add(board);
        }
        return boardList;
    }

    private Board createBoard(Member member, int i) {
        return Board.builder()
                .boardTitle("title" + i)
                .boardContents("content" + i)
                .boardPass(1)
                .boardCreatedTime(LocalDateTime.now())
                .boardUpdatedTime(LocalDateTime.now())
                .category(Category.FREE)
                .activityCategory(ActivityCategory.STUDY)
                .member(member)
                .build();
    }

    private Member createMember(int i) {

        return Member.builder()
                .name("member" + i)
                .email("test@email.com")
                .profile("profile")
                .role(Role.USER)
                .build();
    }
}