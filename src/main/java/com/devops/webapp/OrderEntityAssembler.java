package com.devops.webapp;

public class OrderEntityAssembler extends  SimpleIdentifiableRepresentationModelAssembler<Order>{
    public OrderEntityAssembler() {
        super(OrderController.class);
    }

}
