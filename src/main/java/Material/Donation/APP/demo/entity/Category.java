package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    private String name; // e.g., Clothing, Furniture, Books

    // Optional: list of donations in this category
    @OneToMany(mappedBy = "category")
    private List<Donation> donations;
}