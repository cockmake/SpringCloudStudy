<script setup>
import {changeSaleState, get_search_result, isRoot, search_result} from "../vars.js";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {Cart2} from "@nutui/icons-vue";
import {ElMessage} from "element-plus";
const dialogFormVisible = ref(false)


function updateProductInfo(row) {
  form.product_id = row.product_id
  form.price = row.price
  form.origin = row.origin
  form.description = row.description
  form.category = row.category
  form.img_url = row.img_url
  form.stock = row.stock
  dialogFormVisible.value = true
}
const form = reactive({
  product_id: null,
  price: null,
  origin: null,
  description: null,
  category: null,
  img_url: null,
  stock: null
})
function updateConfirm() {
  axios.post('/api/product/update', form).then(res => {
    get_search_result()
    dialogFormVisible.value = false
  })
}
onMounted(() => {

})
function buy(item) {
  let username = localStorage.getItem("username")
  if(username !== null){
    axios.post("/api/order/add",{
      product_id: item.product_id,
      username: username,
      price: item.price
    })
        .then((resp) => {
          if(resp.data === "操作成功！"){
            ElMessage({
              type: "success",
              message: "购买成功请查看订单！",
              duration: 2000
            })
          }else{
            ElMessage({
              type: "error",
              message: "服务器故障，购买失败！",
              duration: 2000
            })
          }
        })
        .catch((err) => {

        })
  }else{
    ElMessage({
      type: 'error',
      message: "请重新登录",
      duration: 3000
    })
  }
}
</script>

<template>
  <div style="width: 100%; display: flex; flex-direction: row; flex-wrap: wrap">
    <nut-card
        v-for="item in search_result"
        :id="item.product_id"
        style="max-width: 48%; min-width: 360px; margin: 5px; height: auto"
        :img-url="item.img_url"
        :title="item.description"
        :price="String(item.price)"
        :vipPrice="String(item.price)"
        :shopDesc="item.origin"
        :delivery="item.category"
        shopName="计2001自营店>">
      <template #footer>
        <div style="display: flex; flex-direction: row; flex-wrap: nowrap;">
          <div v-if="isRoot">
            <span style="justify-self: center; align-self: center; white-space: nowrap">剩余{{item.stock}}</span>
            <nut-button @click="updateProductInfo(item)">修改</nut-button>
            <nut-button @click="changeSaleState(item)" type="danger">
              {{item.on_sale === 1 ? '下架' : '重新上架'}}
            </nut-button>
          </div>
          <div v-else>
            <nut-button type="danger" @click="buy(item)">
              <template #icon>
                <Cart2/>
              </template>
              购买
            </nut-button>
          </div>

        </div>
      </template>
      <template>
        <div class="search_prolist_attr">
          <span class="word">{{item.category}}</span>
        </div>
      </template>

    </nut-card>
  </div>
  <el-dialog v-model="dialogFormVisible" title="修改商品信息">
    <el-form :model="form" label-position="left" label-width="100px">
      <el-form-item label="商品编号">
        <el-input disabled v-model="form.product_id" autocomplete="off" />
      </el-form-item>
      <el-form-item label="商品描述" >
        <el-input v-model="form.description" autocomplete="off" />
      </el-form-item>
      <el-form-item label="原产地">
        <el-input v-model="form.origin" autocomplete="off" />
      </el-form-item>
      <el-form-item label="类别">
        <el-input v-model="form.category" autocomplete="off" />
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number :min="0" :precision="2" v-model="form.price" autocomplete="off" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number :min="0" v-model="form.stock" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图片链接">
        <el-input v-model="form.img_url" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updateConfirm">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.search_prolist_attr {
  margin: 3px 0 1px;
  height: 15px;
  overflow: hidden;
}
.search_prolist_attr > span {
  float: left;
  padding: 0 5px;
  border-radius: 1px;
  font-size: 10px;
  height: 15px;
  line-height: 15px;
  color: #999;
  background-color: #f2f2f7;
  margin-right: 5px;
}
</style>