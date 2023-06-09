package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime creationDate;
    private String title;
    private String content;

    @JsonIgnore
    @OneToOne
    private Profile profile;

    public Post(String title, String content, Profile profile) {
        this.creationDate = LocalDateTime.now();
        this.title = title;
        this.content = content;
        this.profile = profile;
    }
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", title='" + title + '\'' +
                ", content='" + content +
                ",belongs to profile no. = " + profile.getId() +
                '}';
    }
}
