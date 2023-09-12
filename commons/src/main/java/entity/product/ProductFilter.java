package entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Vector;

@Data
public class ProductFilter {
    @JsonProperty(value = "searchContent")
    public String searchContent;
    @JsonProperty(value = "remained")
    public Boolean remained;
    @JsonProperty(value = "categories")
    public Vector<String> categories;
    @JsonProperty(value = "origins")
    public Vector<String> origins;
    @JsonProperty(value = "min_price")
    public Double minPrice;
    @JsonProperty(value = "max_price")
    public Double maxPrice;
}
