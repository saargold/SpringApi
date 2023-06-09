package com.devops.webapp;

import org.springframework.stereotype.Component;

@Component
public class PostEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Post> {
    public PostEntityAssembler() {
        super(PostController.class);
    }
}
