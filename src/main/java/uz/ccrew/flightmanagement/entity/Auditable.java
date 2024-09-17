package uz.ccrew.flightmanagement.entity;

import lombok.Getter;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class Auditable {
    @CreatedBy
    protected Long createdBy;

    @CreatedDate
    protected LocalDateTime createdDate;

    @LastModifiedBy
    protected Long lastModifiedBy;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}