package com.hit.community.controller;


import com.hit.community.dto.ApiDataResponse;
import com.hit.community.dto.SearchBoardRequest;
import com.hit.community.dto.SearchBoardResponse;
import com.hit.community.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestBoardSearchController {

    private final SearchService searchService;

    @GetMapping("/board/search")
    private ApiDataResponse<Page<SearchBoardResponse>> search(
            @ModelAttribute SearchBoardRequest searchBoardRequest
            ){
        Page<SearchBoardResponse> page =
                searchService.search(
                        searchBoardRequest.searchCategory(),
                        searchBoardRequest.text(),
                        searchBoardRequest.pageRequest());

        return ApiDataResponse.of(page);
    }
}
