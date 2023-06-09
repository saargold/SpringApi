package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime creationDate;
    private String fullName;

    /*
    create relationship between a Profile and a Post
    i.e., a Profile's object primary key will be stored as a foreign key in POSTS table
     */

    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    // Eager loading to avoid NullPointerException
    private List<Post> posts = new ArrayList<>();

    public Profile(String fullName){
        this.creationDate = LocalDateTime.now();
        this.fullName = fullName;
    }

}
