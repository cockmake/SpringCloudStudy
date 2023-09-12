package entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Order {
    @TableId(value = "order_id", type = IdType.AUTO)
    public @Unsigned BigInteger orderId;

    @TableField(value = "order_price")
    @JsonProperty("price")
    public @Unsigned Double price;

    @TableField(value = "created_date")
    public Timestamp createdData;

    @TableField(value = "status")
    public String status;

    @TableField(value = "updated_date")
    public Timestamp updatedData;

    @JsonProperty("product_id")
    @TableField(value = "product_id")
    public BigInteger productId;

    @TableField(value = "username")
    @JsonProperty("username")
    public String username;
}
