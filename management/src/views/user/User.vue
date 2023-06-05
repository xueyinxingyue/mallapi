<template>
    <div>
        <el-card class="box-card">
            <el-row :gutter="20">
                <el-col :span="4">
                    <el-input placeholder="输入用户名/昵称" size="small" v-model="queryInfo.keyword"></el-input>
                </el-col>
                <el-col :span="2">
                    <!-- <el-button type="primary" size="small" @click="getTableData">查询</el-button> -->
                </el-col>
                <el-col :span="1">
                    <el-button type="primary" size="mini" @click="getTableData" style="margin-left: 5px;">查询</el-button>
                </el-col>
            </el-row>

            <el-table :data="tableData" style="width: 100%">
                <el-table-column type="expand" >
                    <template slot-scope="{row}">

                        <div style="font-weight: bold; margin-bottom: 20px; margin-left: 20px;">购物车信息</div>
                        <el-divider></el-divider>
                        <div>
                            <el-table :data="row.carts" style="width: 100%;margin-top: 20px;margin-left: 20px;"
                                height="250">
                                <el-table-column label="商品图片" width="100">
                                    <template slot-scope="{row}">
                                        <img :src="row.product.singlePic[0] ? 'http://localhost:666/'+row.product.singlePic[0].productimageSrc: ''" width="50"/>
                                    </template>

                                </el-table-column>
                                <el-table-column prop="product.productName" label="商品名称" width="350">
                                </el-table-column>
                                <el-table-column label="单价" >
                                    <template slot-scope="{row}">{{ row.product.productSalePrice.toFixed(2) }}</template>
                                </el-table-column>
                                <el-table-column prop="cartCount" label="数量" >
                                </el-table-column>
                                <el-table-column  label="价格" >
                                    <template slot-scope="{row}">{{ (row.cartCount * row.product.productSalePrice).toFixed(2) }}</template>
                                </el-table-column>
                                <el-table-column  label="商品状态" >
                                    <template slot-scope="{row}">
                                        <el-button type="success" size="mini" v-if="row.product.productIsUp">售卖中</el-button>
                                        <el-button type="danger" size="mini" v-else>商品已下架</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>

                    </template>
                </el-table-column>
                <el-table-column label="用户名" prop="userName">
                </el-table-column>
                <el-table-column label="昵称" prop="userNickname">
                </el-table-column>
                <el-table-column label="姓名" prop="userRealname">
                </el-table-column>
                <el-table-column label="出生日期" prop="userBirthday">
                </el-table-column>
                <el-table-column label="性别" prop="userGender">
                    <template slot-scope="{row}">{{ row.userGender == 1 ? '男':'女' }}</template>
                </el-table-column>
                <el-table-column label="所在地"  :show-overflow-tooltip="true" prop="userAddressObj.addressName">
                </el-table-column>
                <el-table-column label="家乡" prop="userHomeAddressObj.addressName">
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: center;padding: 20px 20px 0px 0px;">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="queryInfo.pageNum" :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
        </el-card>
    </div>
</template>
  
<script>
import { saveAs } from 'file-saver'
import { GetUserListAPI, CustomerExportApi, GetCategoryInfoAPI, DeleteCategoryInfoAPI, UpdateCategoryAPI, InsertCategoryAPI } from '@/request/api'
export default {
    data() {
        return {
            tableData: [],
            queryInfo: {
                keyword: '',
                pageNum:1,
                pageSize:10
            },
            total: 0
        }
    },
    created() {
        this.getTableData();
    },
    methods: {
        async getTableData() {
            const userListRes = await GetUserListAPI(this.queryInfo);
            if (!userListRes) return;
            console.log(userListRes);
            this.total = userListRes.data.total;
            this.tableData = userListRes.data.records;
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
        },

    }
}
</script>
   
<style lang = "less" scoped>
.demo-table-expand {
    font-size: 0;
}

.demo-table-expand label {
    width: 90px;
    color: #99a9bf;
}

.demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
}

.el-divider--horizontal {
    margin: -10px 20px;
}
</style>