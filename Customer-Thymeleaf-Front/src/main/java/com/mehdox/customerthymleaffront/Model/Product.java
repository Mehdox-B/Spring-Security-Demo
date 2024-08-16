package com.mehdox.customerthymleaffront.Model;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    private  String id;
    private   String name;
    private double price;
    private int quantity;
}
