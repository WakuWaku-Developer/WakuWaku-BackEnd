package dev.backend.wakuwaku.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * '@MappedSuperclass' : JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 합니다.
 * '@EntityListeners(AuditingEntityListener.class)': BaseTimeEntiy 클래스에 Auditing 기능을 포함시킵니다.
 * '@CreatedDate': Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
 * '@LastModifiedDate': 조회한 Entity의 값을 변경할 때 시간이 자동 저장됩니다.

 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModifiedDateTime;


    // 호출 될 때마다 최신화
    public BaseEntity() {
        this.createDateTime = LocalDateTime.now();
        this.lastModifiedDateTime = LocalDateTime.now();
    }

}