<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="输入订单号" size="small" v-model="queryInfo.keyword"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="small" @click="getTableData">查询</el-button>
        </el-col>

      </el-row>
      <el-row style="margin-top: 15px;" :gutter="20">
        <el-col :span="2">
          <span style="font-size: 14px;">订单状态：</span>
        </el-col>
        <el-col :span="10">
          <el-checkbox-group v-model="queryInfo.orderStatus">
            <el-checkbox label="0">待付款</el-checkbox>
            <el-checkbox label="1">待发货</el-checkbox>
            <el-checkbox label="2">待确认</el-checkbox>
            <el-checkbox label="3">交易成功</el-checkbox>
            <el-checkbox label="4">交易关闭</el-checkbox>
          </el-checkbox-group>
        </el-col>

      </el-row>
      <el-row :gutter="20">
        <el-col>
          <el-divider></el-divider>
        </el-col>
      </el-row>

      <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column label="订单号" align="center">
          <template slot-scope="{row}">{{ row.productorderCode }}</template>
        </el-table-column>
        <el-table-column label="邮政编码" align="center">
          <template slot-scope="{row}">{{ row.productorderPost }}</template>
        </el-table-column>
        <el-table-column label="收货人" align="center">
          <template slot-scope="{row}">{{ row.productorderReceiver }}</template>
        </el-table-column>
        <el-table-column label="联系方式" align="center">
          <template slot-scope="{row}">{{ row.productorderMobile }}</template>
        </el-table-column>
        <el-table-column label="订单状态" align="center">
          <template slot-scope="{row}">
            <el-button type="primary" size="mini" v-if="row.productorderStatus == 0">等待买家付款</el-button>
            <el-button type="warning" size="mini" v-else-if="row.productorderStatus == 1">等待卖家发货</el-button>
            <el-button type="info" size="mini" v-else-if="row.productorderStatus == 2">等待买家确认</el-button>
            <el-button type="success" size="mini" v-else-if="row.productorderStatus == 3">交易成功</el-button>
            <el-button type="danger" size="mini" v-else-if="row.productorderStatus == 4">交易关闭</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <!--  @click="gotoOrderInfo(scope.row.productorderId)" -->
            <el-link type="success" @click="showEditDialog(scope.row.productorderId)" target="_parent">详情</el-link>
            <!-- <el-tooltip class="item" effect="dark" content="修改" placement="top">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.categoryId)"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button type="warning" icon="el-icon-delete" size="mini" @click="deleteDialog(scope.row.categoryId)"></el-button>
          </el-tooltip> -->
          </template>


        </el-table-column>
      </el-table>
      <div style="display: flex;justify-content: center;padding: 20px 20px 0px 0px;">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNum" :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </el-card>



    <el-dialog title="订单详情" :visible.sync="editDialogVisible" @close="editDialogClosed" width="70%">
      <el-row :gutter="20" style=" margin-left: 10px;">
          <el-col :span="3">
            订单号：
          </el-col>
          <el-col :span="5">
            <span style="font-weight: bold;">{{ productOrder.productorderCode }}</span>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
          <el-col :span="3">
            所属用户：
          </el-col>
          <el-col :span="2">
            {{ productOrder.user.userName }}
          </el-col>
        </el-row>
        <el-divider></el-divider>
        基本信息
        <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
          <el-col :span="3">
            收货人姓名：
          </el-col>
          <el-col :span="2">
            {{ productOrder.user.userRealname }}
          </el-col>
        </el-row>
        <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
          <el-col :span="3">
            收货地址：
          </el-col>
          <el-col :span="10">
            {{ productOrder.productorderAddressObj.addressName + " " + productOrder.productorderDetailAddress }}
          </el-col>
        </el-row>
        <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
          <el-col :span="3">
            邮政编码：
          </el-col>
          <el-col :span="5">
            {{ productOrder.productorderPost }}
          </el-col>

          <el-col :span="3">
            联系电话：
          </el-col>
          <el-col :span="5">
            {{ productOrder.productorderMobile }}
          </el-col>
        </el-row>
        <el-divider></el-divider>
        订单状态
        <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
          <el-steps :space="200" :active="productOrder.productorderStatus == 4 ? 0 : productOrder.productorderStatus"
            finish-status="success" :align-center=true
            :process-status="productOrder.productorderStatus == 4 ? 'error' : 'finish'">
            <el-step :title="productOrder.productorderStatus == 4 ? '交易关闭' : '等待买家付款'"></el-step>
            <el-step title="等待卖家发货"></el-step>
            <el-step title="等待买家确认"></el-step>
            <el-step title="订单交易完成"></el-step>
          </el-steps>
        </el-row>
        <el-divider></el-divider>
        订单项信息
        <el-row :gutter="25">
          <el-col :span="23">
            <el-table :data="productOrder.productorderitems" style="width: 100%;margin-top: 20px;margin-left: 20px;"
              height="250">
              <el-table-column label="商品图片">
                <template slot-scope="{row}">
                  <img
                    :src="row.product.singlePic[0] ? 'http://localhost:666/' + row.product.singlePic[0].productimageSrc : ''"
                    width="50" />
                </template>
              </el-table-column>
              <el-table-column label="商品名称" width="350">
                <template slot-scope="{row}">
                  {{ row.product.productName }}
                </template>
              </el-table-column>

              <el-table-column label="单价">
                <template slot-scope="{row}">
                  {{ row.productorderitemPrice.toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="数量">
                <template slot-scope="{row}">
                  {{ row.productorderitemNumber }}
                </template>
              </el-table-column>
              <el-table-column label="价格">
                <template slot-scope="{row}">
                  {{ (row.productorderitemPrice * row.productorderitemNumber).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="备注">
                <template slot-scope="{row}">
                  {{ row.productorderitemUsermessage }}
                </template>
              </el-table-column>
            </el-table>
          </el-col>

        </el-row>
        <div v-if="productOrder.productorderPayDate">
          <el-divider></el-divider>
          流程时间
          <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;">
            <el-col :span="3">
              支付时间：
            </el-col>
            <el-col :span="5">
              {{ productOrder.productorderPayDate }}
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;" v-if="productOrder.productorderDeliveryDate">
            <el-col :span="3">
              发货时间：
            </el-col>
            <el-col :span="5">
              {{ productOrder.productorderDeliveryDate }}
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 25px; margin-left: 10px;" v-if="productOrder.productorderConfirmDate">
            <el-col :span="3">
              确认时间：
            </el-col>
            <el-col :span="5">
              {{ productOrder.productorderConfirmDate }}
            </el-col>
          </el-row>
        </div>

        <el-row>
          <el-divider></el-divider>
          <el-button type="primary" v-if="productOrder.productorderStatus == 1" @click="send(productOrder.productorderId)">发货</el-button>
          <el-button type="info" @click="cancel">取消</el-button>
        </el-row>
    </el-dialog>








  </div>
</template>

<script>
import { saveAs } from 'file-saver'
import qs from 'qs'
import { GetOrderListAPI, GetOrderInfoAPI,OrderSendAPI } from '@/request/api'
export default {
  data() {
    return {
      tableData: [],
      queryInfo: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        orderStatus: ["0", "1", "2", "3", "4"]
      },
      total: 0,
      editDialogVisible: false
      ,

      productOrder: {
        productorderCode:'',
        productorderAddressObj:{},
        user:{},
        productorderConfirmDate:'',
        productorderDeliveryDate:'',
        productorderPayDate:'',
        productorderitems:[
          {
            productorderitemPrice:'',
            productorderitemNumber:'',
            productorderitemUsermessage:'',
            product:{
              productName:'',
              singlePic:[]
            }
          }
        ]

      }
    }
  },
  created() {
    this.getTableData();
  },
  methods: {


    editDialogClosed() {
    },

    async showEditDialog(id) {

      const res = await GetOrderInfoAPI(id)
      if (res.code != 200) {
        this.$message.error("查询订单信息失败");
      } else {
        this.$message.success(res.message);
        this.productOrder = res.data;
        this.productOrder.productorderPayDate = this.productOrder.productorderPayDate ? this.productOrder.productorderPayDate.replaceAll('T', ' ') : ''
        this.productOrder.productorderDeliveryDate = this.productOrder.productorderDeliveryDate ? this.productOrder.productorderDeliveryDate.replaceAll('T', ' ') : ''
        this.productOrder.productorderConfirmDate = this.productOrder.productorderConfirmDate ? this.productOrder.productorderConfirmDate.replaceAll('T', ' ') : ''
      }
      this.editDialogVisible = true

    },
    cancel(){
      this.editDialogVisible = false;
    },
    async send(id){
      const orderSendRes = await OrderSendAPI(id)
      if(orderSendRes.code != 200){
        this.$message.error("发货失败")
        return;
      }
      this.$message.success("订单发货成功")
      this.editDialogVisible = false;
      this.getTableData();
    },
    async getTableData() {
      console.log(this.queryInfo.orderStatus);

      const orderListRes = await GetOrderListAPI({
        pageNum: this.queryInfo.pageNum,
        pageSize: this.queryInfo.pageSize,
        keyword: this.queryInfo.keyword,
        orderStatus: this.queryInfo.orderStatus
      });
      if (!orderListRes) return;

      console.log(orderListRes);
      this.total = orderListRes.data.total;
      this.tableData = orderListRes.data.records;

    },
    handleSelectionChange() {

    },
    handleSizeChange(val) {
      //当每页数据量改变时执行
      console.log(`每页 ${val} 条`);
      this.queryInfo.pageSize = val;
      this.getTableData();
    },
    handleCurrentChange(val) {
      //当前页数改变时执行
      console.log(`当前页: ${val}`);
      this.queryInfo.pageNum = val;
      this.getTableData();
    }
  }
}
</script>
 
<style lang = "less" scoped>
.el-divider--horizontal {
  margin: 12px 0;
}
</style>