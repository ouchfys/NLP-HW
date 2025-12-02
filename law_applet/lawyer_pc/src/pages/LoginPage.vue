<template>
  <div class="register_page">
    <div v-if="isMobileDev" style="font-size: 20px">不支持移动端使用</div>
    <div v-if="!isMobileDev" class="login_box">
      <div class="title">
        <span class="hospital" style="font-size: 18px">律师咨询服务平台</span>
      </div>
      <input class="input tel" type="tel" placeholder="手机号" v-model="tel" />
      <input
        class="input password"
        type="password"
        placeholder="密码"
        v-model="password" />
      <div class="code-box">
        <el-input
          class="input-code"
          placeholder="验证码"
          v-model="code"
          maxlength="4"
          clearable
          prop="security">
        </el-input>
        <div class="code-img" @click="refreshCode">
          <security :identifyCode="identifyCode"></security>
        </div>
      </div>
      <el-select
        v-model="role"
        placeholder="请选择角色类别"
        size="large"
        class="role_class">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <div></div>
      <div class="btn_login" @click.stop="userLoginBefor()">登录</div>
      <div class="help_tip">
        <!-- <span class="tip" @click.stop="jumpToPasswordForget()">忘记密码</span> -->
        <!-- <span class="tip" @click.stop="jumpToRegisterPage()">注册账户</span> -->
      </div>
    </div>
  </div>
</template>

<script>
import $network from "@/commons/network";
import $utils from "@/commons/utils";
import security from "@/components/security.vue";
import { ElMessage } from "element-plus";

export default {
  name: "LoginView",
  components: {
    security,
  },
  data() {
    return {
      options: [
        {
          value: 1,
          label: "管理员",
        },
        {
          value: 2,
          label: "律师",
        },
      ],
      tel: "",
      password: "",
      role: null,
      isMobileDev: false,
      code: "",
      // 随机抽取四位数
      identifyCodes: "1234567890abcdef",
      identifyCode: "",
    };
  },
  /**
   * 组件加载时
   */
  created() {
    this.isMobileDev = this.isMobile();
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    // 切换验证码
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    // 生成随机验证码
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
          this.identifyCodes[
            Math.floor(Math.random() * (this.identifyCodes.length - 0) + 0)
          ];
      }
    },
    isMobile() {
      let flag = navigator.userAgent.match(
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      );
      return flag;
    },
    /**
     * 检查登录参数
     */
    userLoginBefor() {
      if (!this.tel) {
        ElMessage({
            message: "手机号不能为空",
            type: "warning",
          });
        return
      }
      if (!this.password) {
        ElMessage({
            message: "密码不能为空",
            type: "warning",
          });
        return
      }
      if (!this.code) {
        ElMessage({
            message: "验证码不能为空",
            type: "warning",
          });
        return
      }
      if (this.identifyCode != this.code) {
        ElMessage({
            message: "验证码不正确，请重新输入",
            type: "warning",
          });
        return
      }
      if (!this.role) {
        ElMessage({
            message: "请选择角色",
            type: "warning",
          });
        return
      }
      this.userLogin()
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
     * 用户登录
     */
    userLogin() {
      $network.userLogin(
        this.buildLoginParams(),
        (res) => {
          res.code === 200 ? this.loginSuccess(res) : this.showToast(res.msg);
        },
        (res) => this.showToast(res)
      );
    },
    /**
     * 构建用户登录参数
     * @returns {{password: *, userTel: string}}
     */
    buildLoginParams() {
      return {
        account: this.tel,
        password: this.password,
        roleId: this.role
      };
    },
    /**
     * 登录成功
     * @param res
     */
    loginSuccess(res) {
      this.$store.commit("setAuthorization", res.data);
      this.getUserInfo();
    },
    /**
     * 获取用户信息
     */
    getUserInfo() {
      $network.userInfo(
        null,
        (res) => {
          res.code === 200 ? this.getUserSuccess(res.data) : this.getUserFail(res.msg);
        },
        (res) => this.getUserFail(res)
      );
    },
    /**
     * 获取用户信息失败
     * @param res
     */
    getUserFail(res) {
      this.showToast(res);
      this.$store.commit("setUserTel", null);
      window.localStorage.clear();
      window.history.replaceState(null, null);
      this.$router.push("/login");
    },
    /**
     * 获取用户信息成功
     * @param userInfo
     */
    getUserSuccess(userInfo) {
      // 设置本地存储
      localStorage.setItem('role', userInfo.roleId);
      this.$store.commit("setUserInfo", userInfo);
      this.jumpToHomePage();
    },
    /**
     * 跳转到首页
     */
    jumpToHomePage() {
      this.$store.commit("setUserTel", this.tel);
      this.$router.push(`/home`);
    },
    /**
     * 跳转到注册页面
     */
    jumpToRegisterPage() {
      this.$store.commit("setUserTel", this.tel);
      this.$router.push(`/register`);
    },
    /**
     * 跳转到忘记密码页面
     */
    jumpToPasswordForget() {
      this.$store.commit("setUserTel", this.tel);
      this.$router.push(`/password/change`);
    },
  },
};
</script>

<style lang="less">
.register_page {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-image: url("./../assets/background.jpeg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;

  .login_box {
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
    width: 500px;
    height: 450px;
    padding: 20px;
    background-color: #fff;

    .title {
      font-weight: bold;
      font-size: 30px;
    }

    .input {
      width: 264px;
      height: 40px;
      outline: none;
      border: 1px solid rgba(0, 0, 0, 0.2);
      border-radius: 10px;
      text-align: center;
      font-weight: bold;
      margin-top: 30px;

      &::placeholder {
        letter-spacing: 0px;
        font-weight: normal;
      }
    }
    .code-box {
      display: flex;
      height: 40px;
      width: 264px;
      margin-top: 30px;
      justify-content: space-between;
      flex-wrap: nowrap;

      .input-code {
        width: 150px;
        height: 38px;
        outline: none;
        text-align: center;
        font-weight: bold;
        margin-right: 10px;
      }
      .code-img {
        margin-top: 1px;
      }
    }

    .role_class {
      width: 264px;
      height: 50px;
      line-height: 50px;
      margin-top: 20px;
      text-align: center;
    }

    .btn_login {
      width: 264px;
      height: 40px;
      text-align: center;
      line-height: 40px;
      background-color: #21a4ff;
      color: #ffffff;
      border-radius: 10px;
      margin-top: 30px;
      cursor: pointer;

      &:active {
        background-color: rgba(0, 0, 0, 0.2);
      }
    }

    .help_tip {
      margin-top: 30px;

      .tip {
        margin: 0px 10px;
        cursor: pointer;

        &:hover {
          color: #21a4ff;
          text-decoration: underline;
        }
      }
    }
  }
}
</style>
