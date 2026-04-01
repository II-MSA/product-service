package org.iimsa.product_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 기술 제약
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Column(length=36, name="company_id", nullable = false)
    private UUID id;

    @Column(length=80, name="company_name", nullable = false)
    private String name;
}
