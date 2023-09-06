package fr.dawan.javamongodbdriver.typed.documents;

import lombok.*;

/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@Data // reprends les 4 annotations précédentes
@NoArgsConstructor
@AllArgsConstructor
// @RequiredArgsConstructor constructeur avec uniquement les champs 'requis' (final)
public class Size {
    private double h;
    private double w;
    private String uom;
}
