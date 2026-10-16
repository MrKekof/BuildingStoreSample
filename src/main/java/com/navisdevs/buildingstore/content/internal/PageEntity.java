package com.navisdevs.buildingstore.content.internal;

import com.navisdevs.buildingstore.shared.MainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="pages")
@NoArgsConstructor @AllArgsConstructor
public class PageEntity extends MainEntity {
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition ="TEXT")
    private String content;

}
