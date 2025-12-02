<template>
  <div class="password_forget_page">
    <div class="container">
      <div class="title">修改密码</div>
      <div class="middle">
        <div class="row">
          <div class="key">手机号 <span class="master">*</span></div>
          <input
            class="input"
            type="tel"
            placeholder="11位手机号"
            v-model="userTel" />
        </div>
        <div class="row">
          <div class="key">新密码 <span class="master">*</span></div>
          <input
            class="input"
            type="password"
            placeholder="新密码"
            v-model="newPassword" />
        </div>
        <div class="row">
          <div class="key">重复新密码 <span class="master">*</span></div>
          <input
            class="input"
            type="password"
            placeholder="重复新密码"
            v-model="newPasswordRe" />
        </div>
      </div>
      <div class="btn_list">
        <div class="btn" @click.stop="pageBack()">返回</div>
        <div class="btn" @click.stop="checkUpdateParams()">确认修改</div>
      </div>
    </div>
  </div>
</template>
<script>
import $network from "../commons/network";
import $utils from "../commons/utils";
import { ElMessage } from "element-plus";

export default {
  name: "PasswordForgetPage",
  data() {
    return {
      userTel: $utils.getUserTel(),
      newPassword: "",
      newPasswordRe: "",
    };
  },
  methods: {
    /**
     * 页面返回
     */
    pageBack() {
      this.$router.go(-1);
    },
    /**
     * 检查更新参数
     */
    checkUpdateParams() {
      let msg = [];
      if (!this.userTel) msg.push("【手机号】");
      if (!this.newPassword) msg.push("【新密码】");
      if (this.newPasswordRe != this.newPassword) msg.push("【两次密码】");

      if (msg.length > 0) {
        ElMessage({
          message: `${msg.join("、")}，不可为空，或不正确`,
          type: "error",
        });
      } else this.passwordReset();
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
    /**
     * 重置密码
     */
    passwordReset() {
      $network.userUpdate(
        this.buildResetParams(),
        (res) => {
          res.code === 200 ? this.resetSuccess(res) : this.resetFail(res);
        },
        (res) => this.resetFail(res)
      );
    },
    /**
     * 构建重置密码的参数
     */
    buildResetParams() {
      return {
        id: this.$store.state.userInfo.id,
        password: this.newPassword,
      };
    },
    /**
     * 修改密码失败
     * @param res
     */
    resetFail(res) {
      this.showToast(res);
    },
    /**
     * 修改密码成功
     */
    resetSuccess() {
      ElMessage({
        message: "密码修改成功！",
        type: "success",
      });
      setTimeout(() => {
        this.$router.go(-1);
      }, 500);
    },
  },
};
</script>

<style lang="less">
.password_forget_page {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .container {
    box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    padding: 20px;
    width: 600px;
    display: flex;
    flex-direction: column;
    align-items: center;

    .title {
      width: 100%;
      font-weight: bold;
    }

    .middle {
      margin: 10px 0px;
      padding: 10px 0px;
      border-top: 0.5px solid rgba(0, 0, 0, 0.1);
      border-bottom: 0.5px solid rgba(0, 0, 0, 0.1);
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;

      .row {
        display: flex;
        align-items: center;
        margin: 20px 0px;

        .key {
          width: 120px;
          text-align: right;
          padding-right: 20px;

          .master {
            color: #ff0000;
          }
        }

        .input {
          width: 250px;
          outline: none;
          border: 0.5px solid rgba(0, 0, 0, 0.2);
          border-radius: 10px;
          height: 35px;
          text-align: center;
          line-height: 35px;

          &::placeholder {
            color: rgba(0, 0, 0, 0.5);
          }
        }

        .right_ver_code {
          width: 250px;
          display: flex;
          align-items: center;
          justify-content: space-between;

          .input {
            width: 140px;
          }

          .ver_tip {
            width: 100px;
            text-align: center;
            line-height: 35px;
            height: 35px;
            background-color: rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            cursor: pointer;
          }
        }
      }
    }

    .btn_list {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      //padding: 10px 0px;
      .btn {
        width: 100px;
        height: 35px;
        text-align: center;
        line-height: 35px;
        border-radius: 10px;
        cursor: pointer;

        &:first-child {
          background-color: rgba(0, 0, 0, 0.1);
          margin-right: 30px;
        }

        &:last-child {
          background-color: #21a4ff;
          color: #ffffff;
        }

        &:active {
          background-color: rgba(0, 0, 0, 0.1);
          color: #999999;
        }
      }
    }
  }
}
</style>
