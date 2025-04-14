package com.club.badminton.entity.attachment;

import com.club.badminton.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "attachment_id")
    private Long id;

    private String originalName;
    private String storedName;

    public Attachment(String originalName, String storedName) {
        this.originalName = originalName;
        this.storedName = storedName;
    }

}
