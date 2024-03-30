package com.hit.community.service;

import com.hit.community.constant.SearchCategory;
import com.hit.community.dto.SearchBoardResponse;
import com.hit.community.entity.Board;
import com.hit.community.repository.SearchQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchQueryDslRepository searchRepository;

    public Page<SearchBoardResponse> search(SearchCategory category, String text, Pageable pageable){
        Page<Board> page = searchRepository.searchAndPaging(category, text, pageable);
        return page.map(Board::toSearchBoardRequest);
    }

}
