<script setup>
import {order_table_result, order_filter_changed, total, tableHeaderClass} from "../vars.js";
import {Calendar, Search} from "@element-plus/icons-vue";
import {order_table_args} from "../vars.js";
import {onMounted} from "vue";
import axios from "axios";
import PersonCenter from "./PersonCenter.vue";

const tableRowClassName = ({row, rowIndex}) => {
  if (row.status === '已完成') {
    return 'success-row'
  }
  return 'warning-row'
}

function sortChange({column, prop, order}) {
  if(prop === 'createdData'){
    order_table_args.dateOrder = order
  }else if(prop === 'price'){
    order_table_args.priceOrder = order
  }
  order_filter_changed()
}
onMounted(() => {
  order_filter_changed()
})
function dateFormat(a) {
  return a.createdData.replace('T', ' ').replace('Z', '')
}
function showConfirm(status) {
  return status === '待收货'
}
function handleConfirm(row) {
  axios.get('/api/order/confirm', {
    params: {
      order_id: row.order_id
    }
  }).then(res => {
    console.log(res)
    order_filter_changed()
  })
}
function handleDelete(row) {
  axios.get('/api/order/delete', {
    params: {
      order_id: row.order_id
    }
  }).then(res => {
    console.log(res)
    order_filter_changed()
  })
}
function aaa(a) {
  console.log(a)
}
</script>

<template>
  <div style="display: flex; flex-direction: column">
    <div style="display: flex; flex-direction: row;">
      <el-input
          v-model="order_table_args.searchKey"
          style="margin: 0 auto; width: 35%;"
          placeholder="请输入商品信息">
        <template #append>
          <el-button style="color: black" :icon="Search" @click="order_filter_changed"/>
        </template>
      </el-input>
      <PersonCenter/>
    </div>
    <div style="margin:10px">
      <el-pagination
          v-model:current-page="order_table_args.currentPage"
          v-model:page-size="order_table_args.currentSize"
          :page-sizes="[7, 15, 30, 50]"
          :background="true"
          layout="total, sizes, prev, pager, next"
          :total="total"
          @size-change="order_filter_changed"
          @current-change="order_filter_changed"/>
    </div>
    <el-space :fill="true" wrap>
      <el-card class="box-card">
        <template #header>
          <el-radio-group @change="order_filter_changed" v-model="order_table_args.status" size="large">
            <el-radio-button label="全部订单">
              <el-icon size="12">
                <calendar/>
              </el-icon>
              <span>全部订单</span>
            </el-radio-button>
            <el-radio-button label="待收货">
              <span>待收货</span>
            </el-radio-button>
            <el-radio-button label="已完成">
              <span>已完成</span>
            </el-radio-button>
          </el-radio-group>
        </template>
        <div>
          <!-- 注意多列排序的方法 -->
          <el-table
              :data="order_table_result"
              style="width: 100%"
              @sort-change="sortChange"
              :header-cell-class-name="tableHeaderClass"
              :row-class-name="tableRowClassName">
            <el-table-column prop="order_id" label="订单编号"></el-table-column>
            <el-table-column label="商品图片">
              <template #default="scope">
                <div>
                  <nut-image
                      :src=scope.row.img_url
                      width="150"
                      height="150"
                      fit="contain"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="description" label="商品描述"></el-table-column>
            <el-table-column prop="createdData" sortable label="下单时间" :formatter="dateFormat"/>
            <el-table-column prop="price" sortable label="订单金额"/>
            <el-table-column prop="status" label="订单状态"/>
            <el-table-column fixed="right" label="Operations" width="290">
              <template #default="scope">
                <div style="display: flex; flex-direction: row; justify-content: space-around">
                  <el-popover
                      v-if="showConfirm(scope.row.status)"
                      title="提示"
                      placement="top-start"
                      width="auto"
                      trigger="hover">
                    <div style="display: flex; flex-direction: column">
                      <p>确定收货？</p>
                      <el-button style="align-self: end; width: 50%" plain type="success" @click="handleConfirm(scope.row)">确定</el-button>
                    </div>
                    <template #reference>
                      <el-button type="success">确定收货</el-button>
                    </template>
                  </el-popover>

                  <el-popover
                      title="提示"
                      placement="top-start"
                      width="auto"
                      trigger="hover">
                    <div style="display: flex; flex-direction: column">
                      <p>确认删除订单？</p>
                      <el-button style="align-self: end; width: 50%" plain type="danger" @click="handleDelete(scope.row)">确定</el-button>
                    </div>
                    <template #reference>
                      <el-button type="danger">删除订单</el-button>
                    </template>
                  </el-popover>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </el-space>
  </div>
</template>

<style>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
<style scoped>

.demo-pagination-block + .demo-pagination-block {
  margin-top: 10px;
}
.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}
</style>