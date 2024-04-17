package dev.backend.wakuwaku.domain.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class StatusEntity extends BaseEntity {
    @Column(name = "check_status")
    private String checkStatus;
}
