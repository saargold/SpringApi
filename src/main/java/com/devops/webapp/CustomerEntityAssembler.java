package com.devops.webapp;

public class CustomerEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Customer>{
    public CustomerEntityAssembler() {
        super(CustomerController.class);
    }
}
