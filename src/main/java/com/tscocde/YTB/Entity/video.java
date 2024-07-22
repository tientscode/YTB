package com.tscocde.YTB.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="video")
public class video {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String url;
    private int likes;
    private int dislike;
    @Lob // Chỉ định rằng cột sẽ lưu trữ dữ liệu lớn
    private String comment;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "videos")
    private Set<Users> users;
}
