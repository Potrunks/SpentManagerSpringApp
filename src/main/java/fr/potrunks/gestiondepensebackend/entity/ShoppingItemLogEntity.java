package fr.potrunks.gestiondepensebackend.entity;

import fr.potrunks.gestiondepensebackend.resources.ShoppingItemLogAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_item_log")
@DiscriminatorValue("shopping_item")
public class ShoppingItemLogEntity extends LogEntity {

    @Column(name = "action")
    private ShoppingItemLogAction action;

    @ManyToOne
    @JoinColumn(name = "shopping_item_id")
    private ShoppingItemEntity shoppingItem;
}
