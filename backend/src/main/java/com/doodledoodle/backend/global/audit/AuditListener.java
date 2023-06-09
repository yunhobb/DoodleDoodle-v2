package com.doodledoodle.backend.global.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuditListener {
    @PrePersist
    public void setCreatedAt(Auditable auditable) {
        BaseTime baseTime = Optional.ofNullable(auditable.getBaseTime()).orElseGet(BaseTime::new);
        baseTime.setCreatedAt(LocalDateTime.now());
        auditable.setBaseTime(baseTime);
    }

    @PreUpdate
    public void setUpdatedAt(Auditable auditable) {
        BaseTime baseTime = auditable.getBaseTime();
        baseTime.setUpdatedAt(LocalDateTime.now());
    }

    @PreRemove
    public void setDeleteAt(Auditable auditable) {
        auditable.getBaseTime().setDeletedAt(LocalDateTime.now());
    }
}
