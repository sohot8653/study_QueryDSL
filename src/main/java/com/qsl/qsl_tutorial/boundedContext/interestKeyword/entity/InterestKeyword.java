package com.qsl.qsl_tutorial.boundedContext.interestKeyword.entity;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)   // 명시적으로 포함된 필드만 사용
@IdClass(InterestKeywordId.class)
public class InterestKeyword {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private SiteUser user;  // site_user_id

    @Id
    @EqualsAndHashCode.Include      // content 필드만 비교 및 해시에 포함
    private String content;

}
