package entity.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("products")
public class Product {
    @TableId(value = "product_id", type = IdType.AUTO)
    @JsonProperty("product_id")
    public BigInteger id;
    @TableField("description")
    public String description;
    @TableField("price")
    public Double price;
    @TableField("category")
    public String category;
    @TableField("stock")
    public Integer stock;
    @TableField(value = "created_at")
    public Timestamp createdAt;
    @TableField(value = "updated_at", update = "now()")
    public Timestamp updatedAt;
    @TableField("origin")
    public String origin;
    @TableField("img_url")
    @JsonProperty("img_url")
    public String imgUrl;
    @TableField("sales")
    public Integer sales;
    @TableField("on_sale")
    public Integer onSale;
}
