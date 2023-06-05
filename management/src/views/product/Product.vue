<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input label="商品名称" placeholder="输入商品名称" size="mini" v-model="queryInfo.productName"></el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="queryInfo.categoryId" size="mini">
            <el-option label="请选择商品分类" :value="-1">
            </el-option>
            <el-option v-for="item in categoryList" :key="item.categoryId" :label="item.categoryName"
              :value="item.categoryId">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row style="margin-top: 15px;" :gutter="20">
        <el-col :span="4">
          <el-select v-model="queryInfo.isUp" size="mini">
            <el-option label="请选择上架状态" :value="-1">
            </el-option>
            <el-option label="已上架" :value="1">
            </el-option>
            <el-option label="未上架" :value="0">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-input style="width: 84px;" placeholder="最低价" size="mini" v-model="queryInfo.minPrice"></el-input> ~
          <el-input style="width: 84px;" placeholder="最高价" size="mini" v-model="queryInfo.maxPrice"></el-input>
        </el-col>

        <el-col :span="1">
          <el-button type="primary" size="mini" @click="getTableData" style="margin-left: 5px;">查询</el-button>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="1">
          <el-button type="success" size="mini" style="margin-top: 15px;"
            @click="dialogFormVisible = true">添加一个商品</el-button>
        </el-col>
      </el-row>

      <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column label="商品名称" align="center" :show-overflow-tooltip="true">
          <template slot-scope="{row}">{{ row.productName }}</template>
        </el-table-column>
        <el-table-column label="商品标题" align="center" :show-overflow-tooltip="true">
          <template slot-scope="{row}">{{ row.productTitle }}</template>
        </el-table-column>
        <el-table-column label="原价" align="center" property="productPrice">
          <template slot-scope="{row}">{{ row.productPrice.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column label="促销价" align="center">
          <template slot-scope="{row}">{{ row.productSalePrice.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column label="数量" align="center">
          <template slot-scope="{row}">{{ row.productCount }}</template>
        </el-table-column>
        <el-table-column label="是否上架" align="center">
          <template slot-scope="{row}">
            <!-- {{ row.productIsenabled }} -->
            <el-button type="success" size="mini" v-if="row.productIsUp">已上架</el-button>
            <el-button type="danger" size="mini" v-else>未上架</el-button>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                @click="showEditDialog(scope.row.productId)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除" placement="top">
              <el-button type="warning" icon="el-icon-delete" size="mini"
                @click="deleteDialog(scope.row.productId)"></el-button>
            </el-tooltip>
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

    <el-dialog title="添加商品" :visible.sync="dialogFormVisible" @close="addDialogClosed" :close-on-click-modal=false
      :close-on-press-escape=false :before-close="handleClose" width="60%">
      <el-form :model="addForm" ref="addFormRefs" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品类型:" prop="name" size="mini">
              <el-select v-model="addForm.productCategoryId" size="mini" @change="addProductChangeCategoryId">
                <el-option v-for="item in categoryList" :key="item.categoryId" :label="item.categoryName"
                  :value="item.categoryId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="商品状态:" prop="name" size="mini">
              <el-radio v-model="addForm.productIsUp" label="1">上架</el-radio>
              <el-radio v-model="addForm.productIsUp" label="0">下架</el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">基本信息</div>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品名称:" prop="name" size="mini">
              <el-input v-model="addForm.productName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="商品标题:" prop="name" size="mini">
              <el-input v-model="addForm.productTitle"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品原价:" prop="name" size="mini">
              <el-input v-model="addForm.productPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="促销价:" prop="name" size="mini">
              <el-input v-model="addForm.productSalePrice"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品数量:" prop="name" size="mini">
              <el-input v-model="addForm.productCount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">概述图片</div>
        <el-row :gutter="20">
          <el-form-item label="" label-width="20px" prop="name">
            <el-upload action="#" list-type="picture-card" :auto-upload="false" :on-change="handleChange1">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                <span class="el-upload-list__item-actions">
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove1(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">详细图片</div>

        <el-row :gutter="20">
          <el-form-item label="" label-width="20px" prop="name">
            <el-upload action="*" list-type="picture-card" :auto-upload="false" :on-change="handleChange2">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                <span class="el-upload-list__item-actions">
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove2(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">属性值信息</div>
        <template v-if="addForm.propvalues.length > 0">
          <el-row :gutter="20" v-for="(item, index) in addForm.propvalues">
            <el-col :span="10">
              <el-form-item :label="item.propertyName" prop="name" size="mini">
                <el-input v-model="item.propertyvalueValue"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addProduct">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改商品" :visible.sync="editDialogVisible" @close="editDialogClosed">
      <el-form :model="editForm" ref="editFormRefs" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品类型:" prop="name" size="mini">
              <el-select v-model="editForm.productCategoryId" size="mini" @change="updateProductChangeCategoryId">
                <el-option v-for="item in categoryList" :key="item.categoryId" :label="item.categoryName"
                  :value="item.categoryId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="商品状态:" prop="name" size="mini">
              <el-radio v-model="editForm.productIsUp" label=1>上架</el-radio>
              <el-radio v-model="editForm.productIsUp" label=0>下架</el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">基本信息</div>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品名称:" prop="name" size="mini">
              <el-input v-model="editForm.productName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="商品标题:" prop="name" size="mini">
              <el-input v-model="editForm.productTitle"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品原价:" prop="name" size="mini">
              <el-input v-model="editForm.productPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="促销价:" prop="name" size="mini">
              <el-input v-model="editForm.productSalePrice"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="商品数量:" prop="name" size="mini">
              <el-input v-model="editForm.productCount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">概述图片</div>
        <el-row :gutter="20">
          <el-form-item label="" label-width="20px" prop="name">
            <el-upload action="*" list-type="picture-card" :auto-upload="false" :file-list="editForm.singlePic"
              :on-change="handleChange3">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="'http://localhost:666/' + file.productimageSrc" alt="">
                <span class="el-upload-list__item-actions">
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove3(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">详细图片</div>

        <el-row :gutter="20">
          <el-form-item label="" label-width="20px" prop="name">
            <el-upload action="*" list-type="picture-card" :auto-upload="false" :file-list="editForm.detailPic"
              :on-change="handleChange4">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="'http://localhost:666/' + file.productimageSrc" alt="">
                <span class="el-upload-list__item-actions">
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove4(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
        </el-row>

        <el-divider></el-divider>
        <div style="font-weight: bold; margin-bottom: 20px;">属性值信息</div>
        <template v-if="editForm.propvalues.length > 0">
          <el-row :gutter="20" v-for="(item) in editForm.propvalues">
            <el-col :span="10">
              <el-form-item :label="item.propertyName" prop="name" size="mini">
                <el-input v-model="item.propertyvalueValue"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editProduct">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { saveAs } from 'file-saver'
import { IMG_BASEURL } from '@/utils/baseurl'
import { GetProductListAPI, GetCategoryListAllAPI, GetPropertyByCategoryIdAPI, ProductImageUpLoadAPI, GetProductInfoAPI, DeleteProductInfoAPI, UpdateProductAPI, InsertProductAPI } from '@/request/api'
export default {
  data() {
    return {
      tableData: [],
      multipleSelection: [],
      total: null,
      dialogFormVisible: false,
      addForm: {
        productCategoryId: "",
        productIsUp: '1',
        productName: '',
        productTitle: '',
        productPrice: '',
        productSalePrice: '',
        productCount: '',
        singlePic: [],
        detailPic: [],
        propvalues: [],
      },
      inputVisible: false,
      inputValue: '',
      editDialogVisible: false,
      editForm: {
        productId: '',
        productCategoryId: '',
        productIsUp: '1',
        productName: '',
        productTitle: '',
        productPrice: '',
        productSalePrice: '',
        productCount: '',
        singlePic: [],
        detailPic: [],
        propvalues: [],
      },
      queryInfo: {
        pageNum: 1,
        pageSize: 10,
        productName: '',
        categoryId: -1,
        isUp: -1,
        maxPrice: '',
        minPrice: ''
      },

      propertyList: [],
      categoryList: [],
      value: '',
      disabled: false,
      fileList: [],
      fileList2: [],
      singlePic: [],
      detailPic: [],
    }
  },
  created() {
    this.getTableData();
    this.getCategoryListAll();
  },
  methods: {
    handleRemove1(file) {
      console.log(file);
      console.log(this.fileList)
      this.addForm.singlePic.splice(IMG_BASEURL + this.addForm.singlePic.indexOf(file.url), 1);
      this.fileList.splice(this.fileList.indexOf(file), 1)
    },
    handleRemove2(file) {
      console.log(file);
      console.log(this.fileList)
      this.addForm.detailPic.splice(IMG_BASEURL + this.addForm.detailPic.indexOf(file.url), 1);
      this.fileList.splice(this.fileList.indexOf(file), 1)
    },
    handleRemove3(file) {
      console.log(file);
      console.log(this.singlePic, "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=0")
      // this.editForm.detailPic.splice(IMG_BASEURL + this.editForm.singlePic.indexOf(file.url),1);
      this.editForm.singlePic.splice(this.editForm.singlePic.indexOf(file), 1);
      this.singlePic.splice(this.singlePic.indexOf(file.productimageSrc), 1)
    },
    handleRemove4(file) {
      console.log(file);
      console.log(this.detailPic, "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
      // this.editForm.detailPic.splice(IMG_BASEURL + this.editForm.detailPic.indexOf(file.url),1);
      this.editForm.detailPic.splice(this.editForm.detailPic.indexOf(file), 1);
      this.detailPic.splice(this.detailPic.indexOf(file.productimageSrc), 1)
    },
    async handleChange1(file, fileList) {
      this.fileList = fileList

      var res = await ProductImageUpLoadAPI({
        file: file.raw,
        imageType: 'single'
      }, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      if (res.code == 200) {
        this.$message.success("图片上传成功");
        file.url = IMG_BASEURL + res.data;
        this.addForm.singlePic.push({ productimageType: 0, productimageSrc: res.data })
      } else {
        this.$message.error("图片上传失败");
      }
    },
    async handleChange2(file, fileList) {
      this.fileList = fileList

      var res = await ProductImageUpLoadAPI({
        file: file.raw,
        imageType: 'detail'
      }, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      if (res.code == 200) {
        this.$message.success("图片上传成功");
        file.url = IMG_BASEURL + res.data;
        this.addForm.detailPic.push({ productimageType: 1, productimageSrc: res.data })
      } else {
        this.$message.error("图片上传失败");
      }
    },




    async handleChange3(file, fileList) {
      console.log("``````````````````````````````````````````````````````````````````````````");
      var res = await ProductImageUpLoadAPI({
        file: file.raw,
        imageType: 'single'
      }, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      if (res.code == 200) {
        this.$message.success("图片上传成功");
        console.log("图片上传成功");
        this.editForm.singlePic.push({ productimageType: 0, productimageSrc: res.data })

        console.log(this.editForm.singlePic, ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
      } else {
        this.$message.error("图片上传失败");
      }
    },
    async handleChange4(file, fileList) {
      console.log("``````````````````````````````````````````````````````````````````````````0");

      var res = await ProductImageUpLoadAPI({
        file: file.raw,
        imageType: 'detail'
      }, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      if (res.code == 200) {
        this.$message.success("图片上传成功");
        this.editForm.detailPic.push({ productimageType: 1, productimageSrc: res.data })
        console.log(this.editForm.detailPic, ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
      } else {
        this.$message.error("图片上传失败");
      }
    },

    async addProductChangeCategoryId(val) {
      const propertyListRes = await GetPropertyByCategoryIdAPI(val)
      const propertyList = propertyListRes.data
      console.log(propertyList, "=========================================");
      this.addForm.propvalues = []
      if (propertyList.length > 0) {

        propertyList.forEach(item => {
          this.addForm.propvalues.push({ propertyvaluePropertyId: item.propertyId, propertyvalueValue: '', propertyName: item.propertyName })
        });
        console.log(this.addForm.propvalues);
      }

    },
    async updateProductChangeCategoryId(val) {
      const propertyListRes = await GetPropertyByCategoryIdAPI(val)
      const propertyList = propertyListRes.data
      console.log(propertyList, ".............................");
      this.editForm.propvalues = []
      if (propertyList.length > 0) {

        propertyList.forEach(item => {
          this.editForm.propvalues.push({ propertyvaluePropertyId: item.propertyId, propertyvalueValue: '', propertyName: item.propertyName })
        });
      }

    },
    async getTableData() {
      const productListRes = await GetProductListAPI(this.queryInfo);
      if (!productListRes) return;
      console.log(productListRes);
      this.total = productListRes.data.total;
      this.tableData = productListRes.data.records;
    },
    async getCategoryListAll() {
      const categoryListRes = await GetCategoryListAllAPI();
      this.categoryList = categoryListRes.data;
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
    },
    async addProduct() {
      const res = await InsertProductAPI(this.addForm);
      console.log(res);
      if (res.code != 200) {
        this.$message.error("添加商品 失败");
      } else {
        this.$message.success(res.message);
        this.getTableData();
      }
      this.addForm = {
        productCategoryId: "",
        productIsUp: '1',
        productName: '',
        productTitle: '',
        productPrice: '',
        productSalePrice: '',
        productCount: '',
        singlePic: [],
        detailPic: [],
        propvalues: [],
      }
      this.dialogFormVisible = false;
    },
    handleClose() {
      this.dialogFormVisible = false;
    },
    addDialogClosed: function () {
      this.$refs.addFormRefs.resetFields();
      this.addForm.props = []
    },
    editDialogClosed() {
      this.$refs.editFormRefs.resetFields();
    },
    async showEditDialog(id) {
      console.log(id);
      const res = await GetProductInfoAPI(id);
      if (res.code != 200) {
        this.$message.error("查询分类信息失败");
      } else {
        this.editDialogVisible = true;
        this.editForm = res.data;
      }
      console.log(this.editForm);
      this.editForm.productIsUp = this.editForm.productIsUp + ""
      console.log(typeof this.editForm.productIsUp);
    },
    async editProduct() {
      const res = await UpdateProductAPI(this.editForm);
      if (res.code != 200) {
        this.$message.error("修改商品信息失败");
      } else {
        this.$message.success(res.message);
        this.editForm = {
          productId: '',
          productCategoryId: '',
          productIsUp: '1',
          productName: '',
          productTitle: '',
          productPrice: '',
          productSalePrice: '',
          productCount: '',
          singlePic: [],
          detailPic: [],
          propvalues: [],
        }
        this.getTableData();
      }
      this.editDialogVisible = false;
    },
    async deleteDialog(id) {
      const result = await this.$confirm('此操作将永久删除该商品信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(e => e);
      console.log(result);
      if (result == "cancel") {
        this.$message.info("取消删除");
      }
      if (result == "confirm") {
        const res = await DeleteProductInfoAPI(id);
        if (res.code != 200) {
          this.$message.error("删除商品信息失败");
        } else {
          this.$message.success(res.message);
          if (--this.total % this.queryInfo.pageSize == 0) {
            this.queryInfo.pageNum--;
          }
          this.getTableData();
        }
      }
    }

  }
}
</script>
 
<style lang = "less" scoped>
.el-tag+.el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.el-divider--horizontal {
  margin: 10px 0;
}
</style>