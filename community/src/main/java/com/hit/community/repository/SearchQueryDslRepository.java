package com.hit.community.repository;

import com.hit.community.constant.ActivityCategory;
import com.hit.community.constant.SearchCategory;
import com.hit.community.entity.Board;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hit.community.entity.QBoard.board;
import static com.hit.community.entity.QMember.member;

@RequiredArgsConstructor
@Repository
public class SearchQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Board> searchAndPaging(SearchCategory category, String text, Pageable pageable) {
        List<Board> content = queryFactory
                .selectFrom(board)
                .join(board.member, member).fetchJoin()
                .where(eqCategory(category), eqText(text))
                .orderBy(searchSort(pageable))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(board.id.count())
                .from(board)
                .where(eqCategory(category), eqText(text));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchFirst);
    }



    private OrderSpecifier<?> searchSort(Pageable pageable) {
        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            return switch (order.getProperty()) {
                case "name" -> new OrderSpecifier<>(direction, board.member.name);
                case "boardTitle" -> new OrderSpecifier<>(direction, board.boardTitle);
                case "boardContents" -> new OrderSpecifier<>(direction, board.boardContents);
                case "STUDY", "EXERCISE", "HOBBIES", "MORE" -> new OrderSpecifier<>(direction, board.activityCategory);
                case "createdTime" -> new OrderSpecifier<>(direction, board.createdTime);
                default -> new OrderSpecifier<>(Order.DESC, board.createdTime);
            };
        }
        return null;
    }

    private BooleanExpression eqText(String text) {
        return text != null ? board.boardContents.contains(text) : null;
    }

    private BooleanExpression eqCategory(SearchCategory category) {
        if(category == null){
            return null;
        }
        switch (category) {
            case WRITER -> {
                return board.member.name.eq(category.name());
            }
            case TITLE -> {
                return board.boardTitle.eq(category.name());
            }
            case STUDY, EXERCISE, HOBBIES, MORE -> {
                return board.activityCategory.eq(ActivityCategory.getCategory(category));
            }
            default -> {
                return null;
            }
        }
    }
}
