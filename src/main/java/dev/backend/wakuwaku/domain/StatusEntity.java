package dev.backend.wakuwaku.domain;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class StatusEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    protected Status status;
}
