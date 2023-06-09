package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser {
    private Long id;
    private String name;
    private String blog;
    private String email;
    private Integer public_repos;

    @Override
    public String toString(){
        return "Github Information {id = " + getId() + ", name = " + getName() +
                ", blog = " + getBlog() + ", number of repos = " + getPublic_repos() + "}";
    }

}
