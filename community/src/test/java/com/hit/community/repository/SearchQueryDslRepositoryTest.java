package com.hit.community.repository;

import com.hit.community.config.TestJpaQueryDslConfig;
import com.hit.community.constant.ActivityCategory;
import com.hit.community.constant.SearchCategory;
import com.hit.community.entity.Board;
import com.hit.community.entity.Category;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestJpaQueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@DataJpaTest
class SearchQueryDslRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private SearchQueryDslRepository repository;

    @DisplayName("검색 페이징 테스트 - 검색 결과 있음")
    @Test
    void searchAndPagingTest() throws Exception
    {
        //given
        saveMemberAndBoard();
        Sort sort = Sort.by("createdTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        //when
        Page<Board> page =
                repository.searchAndPaging(SearchCategory.STUDY, "con", pageRequest);
        //then
        assertThat(page.getSize()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.isFirst()).isTrue();
        for (int i = 0; i < page.getContent().size(); i++) {
            assertThat(page.getContent().get(i).getBoardContents())
                    .isEqualTo("content" + (10-i));
            assertThat(page.getContent().get(i).getMember().getName())
                    .isEqualTo("member" + (10-i));
            System.out.println(page.getContent().get(i).getBoardContents());
        }
    }
    @DisplayName("검색 페이징 테스트 - 검색 결과 없음 - Study, 검색 null")
    @Test
    void searchAndPagingTest2() throws Exception
    {
        //given
        saveMemberAndBoard();
        Sort sort = Sort.by("createdTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        //when
        Page<Board> page =
                repository.searchAndPaging(SearchCategory.STUDY, "잘못 된 입력 값", pageRequest);
        //then
        assertThat(page.getSize()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo( 0);
        assertThat(page.getContent()).isEmpty();
    }
    @DisplayName("검색 페이징 테스트 - 전체 검색")
    @Test
    void searchAndPagingTest3() throws Exception
    {
        //given
        saveMemberAndBoard();
        Sort sort = Sort.by("createdTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        //when
        Page<Board> page =
                repository.searchAndPaging(null, null, pageRequest);
        //then
        assertThat(page.getSize()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.isFirst()).isTrue();
        for (int i = 0; i < page.getContent().size(); i++) {
            assertThat(page.getContent().get(i).getBoardContents())
                    .isEqualTo("content" + (10-i));
            assertThat(page.getContent().get(i).getMember().getName())
                    .isEqualTo("member" + (10-i));
        }
    }
    @DisplayName("검색 페이징 테스트 - 제목 검색 - 빈 게시물 반환")
    @Test
    void searchAndPagingTest4() throws Exception
    {
        //given
        saveMemberAndBoard();
        Sort sort = Sort.by("boardContents").descending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        //when
        Page<Board> page =
                repository.searchAndPaging(SearchCategory.TITLE, null, pageRequest);
        //then
        assertThat(page.getSize()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(0);
        assertThat(page.getContent()).isEmpty();
    }

    @DisplayName("검색 페이징 테스트 - 제목 검색 - 게시물 반환, 날짜 빠른 순")
    @Test
    void searchAndPagingTest5() throws Exception
    {
        //given
        saveMemberAndBoard();
        Sort sort = Sort.by("createdTime").ascending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        //when
        Page<Board> page =
                repository.searchAndPaging(SearchCategory.STUDY, "con", pageRequest);
        //then
        assertThat(page.getSize()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.isFirst()).isTrue();
        for (int i = 0; i < page.getContent().size(); i++) {
            assertThat(page.getContent().get(i).getBoardContents())
                    .isEqualTo("content" + (i+1));
            assertThat(page.getContent().get(i).getMember().getName())
                    .isEqualTo("member" + (i+1));
        }
    }

    private void saveMemberAndBoard(){
        for (int i = 1; i <= 10; i++) {
            Member member = createMember(i);
            Board board = createBoard(member, i);
            em.persist(member);
            em.persist(board);
        }
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