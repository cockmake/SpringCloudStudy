package entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId(value = "order_id")
    public @Unsigned BigInteger orderId;

    @TableField(value = "order_price")
    public @Unsigned Double price;

    @TableField(value = "created_date")
    public Timestamp createdData;

    @TableField(value = "status")
    public String status;

    @TableField(value = "updated_date")
    public Timestamp updatedData;

    @TableField(value = "product_id")
    public Integer productId;
}
