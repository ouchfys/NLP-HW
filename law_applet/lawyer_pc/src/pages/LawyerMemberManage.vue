<template>
  <div style="width: 100%">
    <div style="width: 98%; text-align: right; margin-top: 20px">
      <el-button style="text-align: right" type="primary" @click="addNews"
        >新增</el-button
      >
    </div>
    <el-table
      :data="laywerList"
      stripe
      highlight-current-row="true"
      style="width: 100%;height: 72vh">
      <el-table-column
        label="律师姓名"
        align="center"
        prop="userName"
        min-width="80">
      </el-table-column>
      <el-table-column
        prop="account"
        label="手机号"
        align="center"
        width="120">
      </el-table-column>
      <el-table-column
        prop="password"
        label="密码"
        align="center"
        width="100">
      </el-table-column>
      <el-table-column label="头像" align="center" min-width="130">
        <template #default="scope">
          <el-image
            style="width: 40px; height: 40px"
            :src="buildImg(scope.row.imgUrl)"
            :fit="fit" />
        </template>
      </el-table-column>
      <el-table-column
        label="所属机构"
        align="center"
        prop="institution"
        min-width="100">
      </el-table-column>
      <el-table-column label="职位" align="center" prop="position" min-width="80">
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="简介"
        align="center"
        show-overflow-tooltip="true"
        min-width="150">
      </el-table-column>
      <el-table-column
        prop="adept"
        label="擅长"
        align="center"
        show-overflow-tooltip="true"
        min-width="200">
      </el-table-column>
      <el-table-column
        prop="consultPrice"
        label="咨询价格"
        align="center"
        min-width="80">
      </el-table-column>
      <el-table-column
        label="操作"
        fixed="right"
        align="center"
        min-width="140">
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            plain
            @click="handleSave(scope.row)"
            >修改</el-button
          >
          <el-button
            size="small"
            type="danger"
            plain
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="totalCount">
      </el-pagination>
    </div>
    <el-dialog
      v-model="dialogFormVisible"
      :title="!form.account ? '新增律师' : '编辑律师'">
      <el-form :model="form" :rules="rules" ref="ruleForm">
        <el-form-item label="律师姓名" :label-width="formLabelWidth" prop="userName">
          <el-input v-model="form.userName" placeholder="请填写姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth" prop="account">
            <el-input v-model="form.account" placeholder="请填写手机号"></el-input>
          </el-form-item>
          <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
            <el-input v-model="form.password" placeholder="请填写登录密码"></el-input>
          </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth" prop="imgUrl">
          <img
            v-if="form.imgUrl"
            :src="buildImg(form.imgUrl)"
            alt="Preview Image"
            style="height: 150px; width: 150px; margin-right: 10px" />
          <el-upload
            class="avatar-uploader"
            action=""
            list-type="picture-card"
            :auto-upload="false"
            :limit="1"
            ref="upload"
            :on-change="handleChange"
            :on-preview="handlePictureCardPreview"
            :file-list="fileList">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog v-model="dialogVisibleimg">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>
        <el-form-item
          label="所属机构"
          :label-width="formLabelWidth"
          prop="institution">
          <el-input v-model="form.institution" placeholder="请填写机构"></el-input>
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth" prop="position">
          <el-input v-model="form.position" placeholder="请填写职位"></el-input>
        </el-form-item>
        <el-form-item
          label="简介"
          :label-width="formLabelWidth"
          prop="introduction">
          <el-input
            type="textarea"
            :rows="3"
            v-model="form.introduction"
            placeholder="请填写简介"></el-input>
        </el-form-item>
        <el-form-item label="擅长" :label-width="formLabelWidth" prop="adept">
          <el-input
            type="textarea"
            :rows="3"
            v-model="form.adept"
            placeholder="请填写擅长"></el-input>
        </el-form-item>
        <el-form-item label="咨询价格" :label-width="formLabelWidth" prop="consultPrice">
          <el-input v-model="form.consultPrice" placeholder="请填写咨询价格"></el-input>
        </el-form-item>
        <div class="form_btn">
          <el-button @click="resetForm('ruleForm')">取消</el-button>
          <el-button type="primary" @click="addNewSetting('ruleForm')"
            >确定</el-button
          >
        </div>
      </el-form>
      <template #footer> </template>
    </el-dialog>
  </div>
</template>

<script>
import $network from "@/commons/network";
import { ElMessageBox } from "element-plus";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      dialogFormVisible: false,
      formLabelWidth: "100px",
      fileList: [],
      dialogImageUrl: "",
      dialogVisibleimg: false,
      currentPage: 1,
      totalCount: 1,
      pageSize: 20,
      form: {
        userName: "",
        account: "",
        password: "",
        imgUrl: "",
        institution: "",
        position: "",
        introduction: "",
        adept: "",
        consultPrice: "",
      },
      rules: {
        userName: [{ required: true, message: "请输入律师姓名", trigger: "blur" }],
        consultPrice: [{ required: true, message: "咨询价格不能为空", trigger: "blur" }],
        account: [{ required: true, message: "手机号不能为空", trigger: "blur" }],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }],
        institution: [{ required: true, message: "所属机构不能为空", trigger: "blur" }],
        position: [{ required: true, message: "职位不能为空", trigger: "blur" }],
      },
      laywerList: [],
      fits: ["fill", "contain", "cover", "none", "scale-down"],
    };
  },

  /**
   * 组件创建时
   */
  created() {
    this.initPageData();
  },

  methods: {
    initPageData(currentPage) {
        let params = {
            current: !currentPage ? 1 : currentPage,
            size: this.pageSize,
            role: 2,
        }
        $network.userPage(params.current,params.size,params.role, (res) => {
          res.code == 200
            ? this.getUseListSuccess(res.data)
            : this.showToast(res.msg || "请求失败");
        },(res) => this.showToast(res));
      
    },

    getUseListSuccess(res) {
      if (res.records.length <= 0) {
        this.records = [];
        return;
      }
      this.laywerList = res.records;
      this.totalCount = res.total;
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.initPageData(val)
    },

    addNews() {
      this.fileList = [];
      this.dialogFormVisible = true;
      this.form = {
        userName: "",
        account: "",
        password: "",
        imgUrl: "",
        institution: "",
        position: "",
        introduction: "",
        adept: "",
        consultPrice: "",
        roleId: 2,
      }
    },

    handleSave(row) {
      this.fileList = [];
      this.dialogFormVisible = true;
      this.form = row;
    },

    addLawyerInfo(form) {
      $network.userRegister(form, (res) => {
        if (res.code == 200) {
          ElMessage({
              message: "新增成功",
              type: "success",
            });
          this.initPageData();
        } else this.showToast(res.msg || "请求失败");
      });
    },

    updateLawyerInfo(form) {
      $network.userUpdate(form, (res) => {
        if (res.code == 200) {
          ElMessage({
              message: "修改成功",
              type: "success",
            });
          this.initPageData();
        } else this.showToast(res.msg || "请求失败");
      });
    },

    addNewSetting(ruleForm) {
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          if (!this.formDataFile && !this.form.imgUrl) {
            ElMessage({
              message: "图片不能为空",
              type: "warning",
            });
            return;
          }
          this.dialogFormVisible = false;
          if (this.form.id) {
            if(this.formDataFile) this.uploadImg(this.form, this.form.id);
            else this.updateLawyerInfo(this.form)
          }
          else this.uploadImg(this.form);
        } else {
          return false;
        }
      });
    },

    uploadImg(form, id) {
      $network.uploadImage(
        this.formDataFile,
        (res) => {
          if (res.code === 200) {
            form.imgUrl = res.data;
            if (!id) this.addLawyerInfo(form);
            else this.updateLawyerInfo(form, id);
          } else this.showToast(res.msg || "图片上传失败");
        },
        (fail) => this.showToast(res, errMsg)
      );
    },

    resetForm() {
      this.dialogFormVisible = false;
    },

    // 添加图片
    handleChange(file, fileList) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isJPG) {
        ElMessage({
          message: "上传头像图片只能是 JPG 、png 格式!",
          type: "error",
        });
        fileList.splice(-1, 1); //移除错误文件
        return false;
      }
      if (!isLt5M) {
        ElMessage({
          message: "上传头像图片大小不能超过 5MB!",
          type: "error",
        });
        fileList.splice(-1, 1);
        return false;
      }
      this.formDataFile = fileList[0].raw;
      this.form.imgUrl = "";
    },

    // 展示图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisibleimg = true;
    },

    handleDelete(row) {
      ElMessageBox.confirm("您确定要删除该律师相关信息吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then((res) => {
          this.deleteSetting(row.id);
        })
        .catch((res) => {});
    },

    deleteSetting(id) {
      $network.userRemove(id, (res) => {
        console.log(res);

        if (res.code == 200) {
          ElMessage({
            message: "删除成功",
            type: "success",
          });
          this.initPageData();
        } else this.startWorkFail(res.msg || "请求失败");
      });
    },

    buildImg(img) {
      if (!img) return "";
      else return `http://127.0.0.1:8088/${img}`;
    },

    /**
     * 显示提示框
     * @param {string | {}} msg
     */
    showToast(msg) {
      this.$toast.show({
        msg: typeof msg === "string" ? msg : JSON.stringify(msg),
      });
    },
  },
};
</script>
<style>
.form_btn {
  float: right;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.pagination {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
