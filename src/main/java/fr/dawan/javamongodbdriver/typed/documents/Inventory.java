package fr.dawan.javamongodbdriver.typed.documents;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    private ObjectId id; // Le char "_" n'est pas à mettre ici (sinon l'id sera toujours null)

    private String item;
    private String status;
    private Size size;
    private List<Stock> instock;
}
