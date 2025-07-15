package com.nploi.zentrix.entity;

import com.nploi.zentrix.entity.base.BaseEntityAudit;
import com.nploi.zentrix.enums.NoteObjectType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "note_objects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteObjectEntity extends BaseEntityAudit {

    private String title;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private NoteObjectEntity parent;

    @Enumerated(EnumType.STRING)
    private NoteObjectType type;
}
