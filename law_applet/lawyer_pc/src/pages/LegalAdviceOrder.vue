<template>
  <div style="width: 100%">
    <div style="width: 98%; text-align: right; margin-top: 20px"></div>
    <el-table
      :data="orderList"
      stripe
      highlight-current-row="true"
      style="width: 100%; height: 72vh">
      <el-table-column
        label="订单编号"
        align="center"
        prop="orderNo"
        min-width="100">
      </el-table-column>
      <!-- <el-table-column
        label="律师用户id"
        align="center"
        prop="lawyerUserId"
        min-width="60">
      </el-table-column> -->
      <el-table-column
        label="用户id"
        align="center"
        prop="userId"
        min-width="60">
      </el-table-column>
      <el-table-column
        label="下单时间"
        align="center"
        prop="insertTime"
        min-width="120">
      </el-table-column>
      <el-table-column
        label="完成时间"
        align="center"
        prop="finishTime"
        min-width="120">
      </el-table-column>
      <el-table-column
        label="订单价格"
        align="center"
        prop="price"
        min-width="80">
      </el-table-column>
      <el-table-column label="订单状态" align="center" min-width="60">
        <template #default="scope">
          <div v-if="scope.row.status == 1" style="color:red;border: 1px solid red;">待付款</div>
          <div v-if="scope.row.status == 2"  style="color:#f0ad4e;border: 1px solid #f0ad4e;">已付款</div>
          <div v-if="scope.row.status == 3" style="color:#409eff;border: 1px solid #409eff;">已完成</div>
        </template>
      </el-table-column>
      <el-table-column label="未读消息" align="center" min-width="90">
        <template #default="scope">
          <div class="unkown_messgae" v-if="scope.row.unreadNum > 0">{{scope.row.unreadNum}}</div>
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
            v-if="scope.row.status === 2"
            plain
            @click="handleSave(scope.row)"
            >咨询回复</el-button
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
  </div>
</template>

<script>
import $network from "@/commons/network";
import $utils from "@/commons/utils";
import { ElMessageBox } from "element-plus";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      userId: null,
      dialogFormVisible: false,
      formLabelWidth: "100px",
      currentPage: 1,
      totalCount: 1,
      pageSize: 20,
      orderList: [],
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
      $network.orderPage(
        params.current,
        params.size,
        (res) => {
          res.code == 200
            ? this.startWorkSuccess(res.data)
            : this.startWorkFail(res.msg || "拉取订单列表失败");
        },
        (res) => this.startWorkFail(res)
      );
    },

    startWorkSuccess(res) {
      if (res.records.length <= 0) {
        this.orderList = [];
        return;
      }
      this.orderList = res.records;
    },

    startWorkFail(msg) {
      this.showToast(`${msg}`);
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.initPageData(val)
    },

    handleSave(item) {
      this.$router.push({ name: 'OrderChat', query: {...item} });
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

.unkown_messgae {
  width: 25px;
  height: 25px;
  line-height: 25px;
  text-align: center;
  background-color: red;
  color: #fff;
  border-radius: 50%;
  margin: auto;
  font-size: 15px;
  font-weight: 700;
}

.pagination {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
