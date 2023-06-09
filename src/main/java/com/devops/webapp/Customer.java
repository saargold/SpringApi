package com.devops.webapp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Customer implements Comparable<Customer> {

    private @Id
    @GeneratedValue Long customerId;
    private String name;

    @JsonIgnore @OneToMany(mappedBy = "customer")
    private List<Order> storeOrders = new ArrayList<>();

    public Customer(String name){
        this.name = name;
    }

    @Override
    public int compareTo(Customer o) {
        return Long.compare(this.customerId, o.customerId);
    }
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof Customer)) {
            return false;
        }

        Customer storeUser = (Customer) o;

        return this.getCustomerId() == storeUser.getCustomerId();
    }
}
