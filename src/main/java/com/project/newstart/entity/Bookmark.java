package com.project.newstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@Setter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmark_id;

    @Column(nullable = false)
    private Long user_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Headline headline;
}
