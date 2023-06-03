<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="输入分类名称" size="small" v-model="queryInfo.keyword"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="small" @click="getTableData">查询</el-button>
        </el-col>
        
      </el-row>
      <el-row :gutter="20">
        <el-col :span="1">
          <el-button type="success" size="mini" style="margin-top: 5px;" @click="dialogFormVisible = true">添加一个分类</el-button>
        </el-col>
      </el-row>
   
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column label="分类名称" align="center">
        <template slot-scope="{row}">{{ row.categoryName }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="修改" placement="top">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.categoryId)"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button type="warning" icon="el-icon-delete" size="mini" @click="deleteDialog(scope.row.categoryId)"></el-button>
          </el-tooltip>
        </template>


      </el-table-column>
    </el-table>
    <div style="display: flex;justify-content: center;padding: 20px 20px 0px 0px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
        :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </el-card>

    <el-dialog title="添加分类" :visible.sync="dialogFormVisible" @close="addDialogClosed" :close-on-click-modal=false
      :close-on-press-escape=false :before-close="handleClose">
      <el-form :model="addForm" ref="addFormRefs" label-width="70px">
        <el-form-item label="分类名称" label-width="120px" prop="name">
          <el-input v-model="addForm.categoryName"></el-input>
        </el-form-item>

        <el-form-item label="分类属性" label-width="120px" prop="name">
          <el-tag :key="tag" v-for="tag in addForm.props" closable :disable-transitions="false"
            @close="handleCloseTagAdd(tag)">
            {{ tag }}
          </el-tag>
          <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInputUpdate" size="small"
            @keyup.enter.native="handleInputConfirmAdd" @blur="handleInputConfirmAdd">
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 属性</el-button>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addCategory">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="修改分类"
      :visible.sync="editDialogVisible"
      @close="editDialogClosed"
    >
      <el-form :model="editForm" ref="editFormRefs" label-width="70px">
        <el-form-item label="分类名称" label-width="120px" prop="name">
          <el-input v-model="editForm.categoryName"></el-input>
        </el-form-item>
        <el-form-item label="分类" label-width="120px" prop="sn">
          <el-tag :key="tag.propertyName" v-for="tag,index in editForm.props" closable :disable-transitions="false"
            @close="handleCloseTagUpdate(index)">
            {{ tag.propertyName }}
          </el-tag>
          <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInputUpdate" size="small"
            @keyup.enter.native="handleInputConfirmUpdate" @blur="handleInputConfirmUpdate">
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 属性</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editCategory">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { saveAs } from 'file-saver'
import { GetCategoryListAPI, CustomerExportApi,GetCategoryInfoAPI,DeleteCategoryInfoAPI,UpdateCategoryAPI,InsertCategoryAPI } from '@/request/api'
export default {
  data() {
    return {
      tableData: [],
      multipleSelection: [],
     
      total: null,
      dialogFormVisible: false,
      addForm: {
        categoryName: "",
        props: [],
      },
      inputVisible: false,
      inputValue: '',
      editDialogVisible:false,
      editForm: {
        categoryName: "",
        props: [],
      },
      queryInfo:{
          pageNum: 1,
          pageSize: 10,
          keyword:''
      }
    }
  },
  created() {
    this.getTableData();
  },
  methods: {
    async getTableData() {
      
      const customerListRes = await GetCategoryListAPI(this.queryInfo);
      if (!customerListRes) return;
      console.log(customerListRes);
      this.total = customerListRes.data.total;
      this.tableData = customerListRes.data.records;
    },
    handleSelectionChange() {

    },
    handleSizeChange(val) {
      //当每页数据量改变时执行
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.getTableData();
    },
    handleCurrentChange(val) {
      //当前页数改变时执行
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.getTableData();
    },
    async addCategory() {
      const res = await InsertCategoryAPI(this.addForm);
      console.log(res);
      if (res.code != 200) {
        this.$message.error("添加分类失败");
      } else {
        this.$message.success(res.message);
        this.getTableData();
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


    handleCloseTagAdd(tag) {
      this.addForm.props.splice(this.addForm.props.indexOf(tag), 1);
    },

    handleCloseTagUpdate(tag) {
      console.log("tag",tag);
      this.editForm.props.splice(tag, 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInputUpdate.$refs.input.focus();
      });
    },

    handleInputConfirmAdd() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if (!this.addForm.props.includes(inputValue))
          this.addForm.props.push(inputValue);
        else
          this.$message.error("该属性已存在")
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    handleInputConfirmUpdate() {
      let inputValue = this.inputValue;
      // alert(inputValue)
      if (inputValue) {
          var res = this.editForm.props.filter(item => item.propertyName === inputValue)
          console.log(res);
          if(res.length > 0)
            this.$message.error("该属性已存在")
          else
            this.editForm.props.push({propertyName:inputValue,propertyCategoryId:this.editForm.categoryId});
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    editDialogClosed() {
      this.$refs.editFormRefs.resetFields();
    },
    async showEditDialog(id) {
      console.log(id);
      const res = await GetCategoryInfoAPI(id);
      if (res.code != 200) {
        this.$message.error("查询分类信息失败");
      } else {
        this.editDialogVisible = true;
        this.editForm = res.data;
      }
      console.log(this.editForm);
    },
    async editCategory() {
      const  res  = await UpdateCategoryAPI(this.editForm);
      if (res.code != 200) {
        this.$message.error("修改分类信息失败");
      } else {
        this.$message.success("修改分类信息成功");
        this.getTableData();
      }
      this.editDialogVisible=false;	
    },
    async deleteDialog(id){
        const result=await this.$confirm('此操作将永久删除该分类信息, 是否继续?','提示',{
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).catch(e=>e);
        console.log(result);
        if(result=="cancel"){
            this.$message.info("取消删除");
        }
        if(result=="confirm"){
            const res =await DeleteCategoryInfoAPI(id);
            if(res.code!=200){
                this.$message.error("删除分类信息失败");
            }else{
                this.$message.success("删除分类信息成功");
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
</style>