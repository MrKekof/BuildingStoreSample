package com.navisdevs.buildingstore.news.internal;

import com.navisdevs.buildingstore.shared.MainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="news")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

class NewsEntity extends MainEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String newsImageUrl;

    private boolean published = false;

}
