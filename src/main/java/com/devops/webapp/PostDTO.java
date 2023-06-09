package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"id","title","content","moreInfo"})
public class PostDTO {
    @JsonIgnore
    private final Post post;

    //use getters to get actual information
    public Long getId(){
        return this.post.getId();
    }

    public String getTitle(){
        return this.post.getTitle();
    }

    public String getContent(){
        return "This is the content of the post " +this.post.getContent();
    }

    public String getMoreInfo(){
        return "This a DTO of a specific post";
    }







}
