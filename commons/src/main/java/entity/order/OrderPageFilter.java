package entity.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderPageFilter {
    @JsonProperty(value = "currentPage")
    public Integer page;
    @JsonProperty(value = "currentSize")
    public Integer size;
    @JsonProperty(value = "username")
    public String username;
    @JsonProperty(value = "searchKey")
    public String searchKey;
    @JsonProperty(value = "status")
    public String status;
    @JsonProperty(value = "priceOrder")
    public String priceOrder;
    @JsonProperty(value = "dateOrder")
    public String dateOrder;
}
