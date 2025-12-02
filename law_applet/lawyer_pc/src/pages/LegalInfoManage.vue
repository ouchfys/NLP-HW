<template>
  <div style="width: 100%; position: relative">
    <div style="width: 98%; text-align: right; margin-top: 20px">
      <el-button style="text-align: right" type="primary" @click="addNews"
        >新增</el-button
      >
    </div>
    <el-table
      :data="records"
      stripe
      highlight-current-row="true"
      style="width: 100%; height: 72vh">
      <el-table-column
        prop="id"
        label="序列号"
        align="center"
        min-width="80">
      </el-table-column>
      <el-table-column
        label="标题"
        align="center"
        prop="title"
        show-overflow-tooltip="true"
        min-width="180">
      </el-table-column>
      <el-table-column
        prop="information"
        label="内容"
        align="center"
        show-overflow-tooltip="true"
        min-width="250">
      </el-table-column>
      <el-table-column label="图片" align="center" min-width="130">
        <template #default="scope">
          <el-image
            style="width: 40px; height: 40px"
            :src="buildImg(scope.row.imgUrl)"
            :fit="fit" />
        </template>
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
      :title="form.title ? '编辑法律信息' : '新增法律信息'">
      <el-form :model="form" :rules="rules" ref="ruleForm">
        <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
          <el-input type="textarea" v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item
          label="内容"
          :label-width="formLabelWidth"
          prop="information">
          <el-input
            type="textarea"
            :rows="6"
            v-model="form.information"></el-input>
        </el-form-item>
        <el-form-item label="图片" :label-width="formLabelWidth" prop="img">
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
      formDataFile: null,
      currentPage: 1,
      totalCount: 1,
      pageSize: 20,
      form: {
        title: "",
        information: "",
        imgUrl: "",
      },
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        information: [
          { required: true, message: "请输入内容", trigger: "blur" },
        ],
      },
      records: [],
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
      };
      $network.lawInfoList(
        params.current,
        params.size,
        (res) => {
          res.code == 200
            ? this.lawInfoListSuccess(res.data)
            : this.lawInfoListFail(res.msg || "拉取法律信息列表失败");
        },
        (res) => this.lawInfoListFail(res)
      );
    },

    lawInfoListSuccess(res) {
      if (res.records.length <= 0) {
        this.records = [];
        return;
      }
      this.records = res.records;
      this.totalCount = res.total;
    },

    lawInfoListFail(msg) {
      this.showToast(`${msg}`);
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.initPageData(val)
    },

    addNews() {
      this.fileList = [];
      this.dialogFormVisible = true;
      this.formDataFile = null;
      this.form = {
        title: "",
        details: "",
        imgUrl: "",
      };
    },

    handleSave(row) {
      this.fileList = [];
      this.dialogFormVisible = true;
      this.form = row;
    },

    addNewsInfo(form, id) {
      $network.lawInfoAddUpdate(form, (res) => {
        if (res.code == 200) {
          if (id) {
            ElMessage({
              message: "修改成功",
              type: "success",
            });
          } else {
            ElMessage({
              message: "新增成功",
              type: "success",
            });
          }
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
            else this.addNewsInfo(this.form, this.form.id)
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
            if (!id) this.addNewsInfo(form);
            else this.addNewsInfo(form, id);
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
      const isLt5M = file.size / 1024 / 1024 < 10;
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
          message: "上传头像图片大小不能超过 10MB!",
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
      let that = this;
      ElMessageBox.confirm("您确定要删除该条法律信息吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then((res) => {
          that.deleteNews(row.id);
        })
        .catch((res) => {});
    },

    deleteNews(id) {
      $network.lawInfoDelete(
        id,
        (res) => {
          console.log(res);
          if (res.code == 200) {
            ElMessage({
              message: "删除成功",
              type: "success",
            });
            this.initPageData();
          } else this.showToast(res.msg || "请求失败");
        },
        (res) => this.showToast(res)
      );
    },

    buildImg(img) {
      if (!img) return "";
      else return `http://127.0.0.1:8088${img}`;
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
