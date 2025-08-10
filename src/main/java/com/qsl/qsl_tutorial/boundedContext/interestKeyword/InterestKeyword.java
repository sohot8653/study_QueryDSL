package com.qsl.qsl_tutorial.boundedContext.interestKeyword;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestKeyword {

    private String keywordContent;

}
