package com.zoile.kdtboot.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BbsTimeEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime wdate;
}
