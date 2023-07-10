package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
    @JsonPropertyOrder({"id","name","orders"})
    public class UserDTO {
        @JsonIgnore
        private final User user;

        public Long getId(){
            return this.user.getId();
        }

        public String getUserName(){
            return this.user.getUserName();
        }
    public List<Order> getOrders(){return this.user.getOrders();}
    }