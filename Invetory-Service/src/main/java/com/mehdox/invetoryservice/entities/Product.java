package com.mehdox.invetoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter@Setter
public class Product {
   @Id
   private  String id;
   private   String name;
   private double price;
   private int quantity;
}