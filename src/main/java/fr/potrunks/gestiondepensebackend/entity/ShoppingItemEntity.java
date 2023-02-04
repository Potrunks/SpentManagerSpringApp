package fr.potrunks.gestiondepensebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_item")
public class ShoppingItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_bought")
    private Boolean isBought;

    @OneToMany(mappedBy = "shoppingItem", cascade = CascadeType.PERSIST)
    private List<ShoppingItemLogEntity> log;
}
