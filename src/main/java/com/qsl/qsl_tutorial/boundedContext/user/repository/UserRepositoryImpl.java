package com.qsl.qsl_tutorial.boundedContext.user.repository;

import com.qsl.qsl_tutorial.boundedContext.user.entity.QSiteUser;
import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {

        QSiteUser siteUser = QSiteUser.siteUser;

        return jpaQueryFactory
                .selectFrom(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }
}
