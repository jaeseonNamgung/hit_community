package com.hit.community.controller;

import com.hit.community.constant.ActivityCategory;
import com.hit.community.constant.ErrorCode;
import com.hit.community.constant.SearchCategory;
import com.hit.community.dto.SearchBoardResponse;
import com.hit.community.entity.Category;
import com.hit.community.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestBoardSearchController.class)
class RestBoardSearchControllerTest {

    @MockBean
    private SearchService searchService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("[API][GET] 검색 기능 테스트")
    void apiSearchTest() throws Exception
    {
        //given
        PageRequest pageRequest =
                PageRequest.of(0, 3, Sort.by("createdTime").descending());
        List<SearchBoardResponse> boardRequestList = saveMemberAndBoard();
        given(searchService.search(any(), any(), any())).willReturn(new PageImpl<>(boardRequestList, pageRequest, 10));
        //when&then
        mvc.perform(get("/board/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("searchCategory", String.valueOf(SearchCategory.STUDY))
                .param("text", "ti")
                .param("pageable", String.valueOf(pageRequest))
        ).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(ErrorCode.OK.getHttpStatus().name()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andExpect(jsonPath("$.data.content[0]").isNotEmpty())
                .andExpect(jsonPath("$.data.size").value(3))
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(false))
                .andExpect(jsonPath("$.data.totalElements").value(10))
                .andExpect(jsonPath("$.data.totalPages").value(4))
                .andExpect(jsonPath("$.data.number").value(0));

        then(searchService).should().search(any(), any(), any());
    }

    private List<SearchBoardResponse> saveMemberAndBoard(){
        List<SearchBoardResponse> boardList = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            SearchBoardResponse board = createBoard(i);
            boardList.add(board);
        }
        return boardList;
    }

    private SearchBoardResponse createBoard(int i) {
        return SearchBoardResponse.of(
                "name" + i,
                "title" + i,
                "content" + i,
                1,
                1,
                Category.FREE,
                ActivityCategory.STUDY,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}