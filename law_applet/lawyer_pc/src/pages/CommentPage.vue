<template>
  <div style="width: 100%">
    <el-table
        :data="commentsList"
        stripe
        highlight-current-row
        style="width: 100%; height: 72vh"
    >
      <el-table-column
          label="评论内容"
          align="center"
          prop="content"
          min-width="200"
      ></el-table-column>
      <el-table-column
          label="订单编号"
          align="center"
          prop="orderNo"
          min-width="150"
      ></el-table-column>
      <el-table-column
          label="打分"
          align="center"
          prop="star"
          min-width="100"
      ></el-table-column>
      <el-table-column
          label="状态"
          align="center"
          prop="status"
          min-width="100"
      >
        <template #default="scope">
          <span>{{ getStatusText(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          fixed="right"
          align="center"
          min-width="140"
      >
        <template #default="scope">
          <el-button v-if="scope.row.status === 0"
              size="small"
              type="success"
              plain
              @click="approveComment(scope.row.id)"
          >通过审核</el-button
          >
          <el-button
              size="small"
              type="danger"
              plain
              @click="deleteComment(scope.row.id)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import $network from "@/commons/network";

export default {
  data() {
    return {
      commentsList: [],
      totalCount:1,
      currentPage: 1,
      pageSize:500
    };
  },
  created() {
    this.initPageData();
  },
  methods: {
    initPageData(currentPage) {
      let params = {
        current: !currentPage ? 1 : currentPage,
        size: this.pageSize,
      };
      $network.commentPage(
          params.current,
          params.size,
          (res) => {
            if (res.code == 200){
              this.commentListSuccess(res.data)
            }
          },
          (res) => this.lawInfoListFail(res)
      );
    },
    commentListSuccess(res) {
      if (res.records.length <= 0) {
        this.records = [];
        return;
      }
      this.commentsList = res.records;
      this.totalCount = res.total;
    },
    getStatusText(status) {
      switch (status) {
        case 0:
          return "待审核";
        case 1:
          return "已通过";
        case 2:
          return "未通过";
        default:
          return "";
      }
    },
    approveComment(id) {
      const comment = this.commentsList.find((c) => c.id === id);
      if (comment) {
        comment.status = 1; // 设置为已通过
        // 调用 commentUpdate 方法更新状态
        $network.commentUpdate(
            { id: comment.id, status: comment.status },
            () => {
              this.$message.success("评论已通过审核");
            },
            (error) => {
              console.error("更新评论状态失败:", error);
              this.$message.error("更新评论状态失败");
              // 恢复状态以防更新失败
              comment.status = 0;
            }
        );
      }
    },
    deleteComment(id) {
      const comment = this.commentsList.find((c) => c.id === id);
      if (comment) {
        $network.deleteComment(
            { id: comment.id},
            () => {
              this.$message.success("评论已删除");
              this.initPageData();
            },
            (error) => {
              console.error("删除失败:", error);
              this.$message.error("删除评论失败");
              comment.status = 0;
            }
        );
      }
    },
  },
};
</script>

<style>
/* 这里可以添加自定义样式 */
</style>
