package com.nploi.zentrix.entity;

import com.nploi.zentrix.entity.base.BaseEntityAudit;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_contents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileContentEntity extends BaseEntityAudit {

    @OneToOne
    @JoinColumn(name = "note_object_id", nullable = false)
    private NoteObjectEntity noteObject;

    private String content;
}
