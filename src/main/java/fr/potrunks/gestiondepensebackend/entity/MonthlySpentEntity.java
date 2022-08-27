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
@Table(name = "monthly_spent")
public class MonthlySpentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthly_spent")
    private Long idMonthlySpent;
    @Column(name = "value_monthly_spent")
    private Float valueMonthlySpent;
    @Column(name = "name_monthly_spent")
    private String nameMonthlySpent;
    @Column(name = "comment_monthly_spent")
    private String commentMonthlySpent;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "id_spent_category")
    private SpentCategoryEntity spentCategoryEntity;
    @OneToMany(mappedBy = "monthlySpentEntity", cascade = CascadeType.PERSIST)
    private List<SpentEntity> spentEntityList;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;
}
