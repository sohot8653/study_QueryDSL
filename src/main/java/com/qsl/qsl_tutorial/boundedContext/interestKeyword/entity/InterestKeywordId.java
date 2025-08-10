package com.qsl.qsl_tutorial.boundedContext.interestKeyword.entity;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestKeywordId implements Serializable {    // Serializable은 복합키 생성을 위한 규칙
    private SiteUser siteUser;
    private String content;
}
