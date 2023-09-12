package entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Vector;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProfile {
    public Vector<String> categories = new Vector<>();
    public Vector<Double> price = new Vector<>();
    public Vector<String> origins = new Vector<>();
}
