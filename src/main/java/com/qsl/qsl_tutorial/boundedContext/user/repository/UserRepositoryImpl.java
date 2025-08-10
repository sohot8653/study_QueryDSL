package com.qsl.qsl_tutorial.boundedContext.user.repository;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.qsl.qsl_tutorial.boundedContext.user.entity.QSiteUser.siteUser;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {

        return jpaQueryFactory
                .selectFrom(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public long getQslCount() {
        Long total = jpaQueryFactory
                    .select(siteUser.id.count())
                    .from(siteUser)
                    .fetchOne();
        return total != null ? total : 0L;
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
        return jpaQueryFactory
                .selectFrom(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc() {
        return jpaQueryFactory
                .selectFrom(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> searchQsl(String text) {
        return jpaQueryFactory
                .selectFrom(siteUser)
                .where(
                    siteUser.username.contains(text)
                        .or(siteUser.email.contains(text))
                )
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String text, Pageable pageable) {
        BooleanExpression predicate = StringUtils.hasText(text)
                ? siteUser.username.containsIgnoreCase(text)
                    .or(siteUser.email.containsIgnoreCase(text))
                : null; // null이면 where에서 무시됨

        // 데이터 목록 (정렬 고정: id)
        JPAQuery<SiteUser> usersQuery = jpaQueryFactory
                .selectFrom(siteUser)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
            usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<SiteUser> users = usersQuery.fetch();

        JPAQuery<Long> usersCountQuery = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(predicate);

        // 전체 개수
        Long total = jpaQueryFactory
                .select(siteUser.id.count())
                .from(siteUser)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(users, pageable, usersCountQuery.fetchOne());
    }

    @Override
    public List<SiteUser> getQslUserByInterestKeyword(String keyword) {
        return null;
    }
}
