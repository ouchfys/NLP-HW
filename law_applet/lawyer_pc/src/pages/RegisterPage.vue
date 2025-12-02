<template>
  <div class="register_page">
    <div class="register_box">
      <div class="title">医生注册</div>
      <div class="input_container">
        <div class="box_1">
          <div class="row  ">
            <div class="key">身份证<span class="master">*</span></div>
            <input class="input" type="text" maxlength="18" placeholder="请输入18位身份证" v-model="idCard">
          </div>
          <div class="row">
            <div class="key">姓名<span class="master">*</span></div>
            <input class="input" type="text" placeholder="请输入姓名" v-model="username">
          </div>
          <div class="row" @click.stop="hospitalChoice">
            <div class="key">医院<span class="master">*</span></div>
            <div class="select" :class="{select_not:hospitalIndex===null}">{{ getHospitalName || '请选择医院' }}</div>
          </div>
          <div class="row" @click.stop="departmentChoose">
            <div class="key">科室<span class="master">*</span></div>
            <div class="select" :class="{select_not:depIndex===null}">{{ getDepName || '请选择科室' }}</div>
          </div>
          <div class="row" @click.stop="titleChoose">
            <div class="key">职称<span class="master">*</span></div>
            <div class="select" :class="{select_not:titleIndex===null}">{{ getTitleName || '请选择职称' }}</div>
          </div>
          <div class="row">
            <div class="key">医师资格证</div>
            <input class="input" type="text" maxlength="18" placeholder="请输入医师资格证书编号" v-model="numberOne">
          </div>
          <div class="row">
            <div class="key">医师执业证</div>
            <input class="input" type="text" maxlength="18" placeholder="请输入医师执业证书编号" v-model="numberTwo">
          </div>
        </div>
        <div class="box_2">
          <div class="row image_box">
            <div class="key">头像<span class="master">*</span></div>
            <div class="right" @click.stop="chooseUserAvatar()">
              <img class="avatar" v-if="avatar" :src="avatar" alt="avatar"/>
              <div class="no_image" v-else> +</div>
            </div>
          </div>
          <div class="row row_remark" @click.stop="inputUserRemark()">
            <div class="key">简介</div>
            <div class="select" :class="{select_not:remark === null}">{{ remark || '请填写个人简介' }}</div>
          </div>
          <div class="row" @click.stop="inputUserProficient()">
            <div class="key">擅长</div>
            <div class="select" :class="{select_not:proficient  == null}">{{ proficient || '请填写' }}</div>
          </div>
          <div class="row">
            <div class="key">手机号<span class="master">*</span></div>
            <input class="input" type="tel" maxlength="11" placeholder="请输入11位手机号" v-model="tel">
          </div>
          <div class="row">
            <div class="key">验证码<span class="master">*</span></div>
            <div class="right">
              <input class="input" type="text" maxlength="6" placeholder="请输入验证码" v-model="code">
              <div class="ver_code" @click.stop="checkSendTel()">{{ time || '获取验证码' }}</div>
            </div>
          </div>
<!--          <div class="row">-->
<!--            <div class="key">密码</div>-->
<!--            <input class="input" type="password" maxlength="11" placeholder="请输入至少6位字符" v-model="password">-->
<!--          </div>-->
        </div>
      </div>
      <div class="btn_list">
        <div class="btn back" @click.stop="pageBack()">返回</div>
        <div class="btn register" @click.stop="registerCheck()">注册</div>
      </div>
    </div>
  </div>
</template>

<script>
import $network from '../commons/network';
import $utils from '../commons/utils';

export default {
  name: 'RegisterView',

  /**
   * 页面数据池
   */
  data() {
    return {
      avatar: null,
      avatarUrl: null,
      idCard: null,  //  身份证
      username: null, //  用户名
      hospitalList: [],  //  医院列表
      hospitalIndex: null, //  医院已选下标
      titleList: [], //  职称列表
      titleIndex: null, //  职称选择下标
      depList: [],
      depIndex: null,
      numberOne: null, //  医师资格证书编号
      numberTwo: null, //  医师执业证书编号
      remark: null,  //  简介
      proficient: null,  //  擅长
      // password: null,  //  密码
      tel: null,
      code: null,
      time: null,
      isSendVerCode: false
    }
  },
  /**
   * 组件挂载时
   */
  mounted() {
    this.initPageData();
  },
  computed: {
    /**
     * 获取医院名称
     * @returns {*|string}
     */
    getHospitalName() {
      try {
        return this.hospitalList[this.hospitalIndex].hospName || null;
      } catch (e) {
        return null;
      }
    },
    /**
     * 获取部门名称
     * @returns {null|*}
     */
    getDepName() {
      try {
        return this.depList[this.depIndex].departName || null;
      } catch (e) {
        return null;
      }
    },
    /**
     * 获取职称名称
     * @returns {null | *}
     */
    getTitleName() {
      try {
        return this.titleList[this.titleIndex].titlesName || null;
      } catch (e) {
        return null;
      }
    },
  },
  /**
   * 页面方法
   */
  methods: {
    /**
     * 初始化页面
     */
    initPageData() {
      this.getHospitalList();
      this.getDepartmentList();
      this.getTitleList();
    },
    /**
     * 获取职称列表
     */
    getTitleList() {
      $network.getTitleList({}, res => {
        res.code === 200 ? this.getTitleListSuccess(res) : this.getTitleListFail(res.msg);
      }, res => this.getTitleListFail(res));
    },
    /**
     * 获取职称列表成功
     * @param res
     */
    getTitleListSuccess(res) {
      this.titleList = res.data;
    },
    /**
     * 获取职称列表失败
     * @param errMsg
     */
    getTitleListFail(res) {
      console.error(res)
      $utils.showToast(res);
    },
    /**
     * 获取医院列表
     */
    getHospitalList() {
      $network.getHospitalList({}, res => res.code === 200 ? (this.hospitalList = res.data) : this.getHospitaFail(res.msg), res => this.getHospitaFail(res.msg || res.errMsg));
    },
    /**
     * 获取医院列表失败
     * @param msg
     */
    getHospitaFail(msg) {
      console.error(msg)
    },
    /**
     * 获取科室列表
     */
    getDepartmentList() {
      $network.getDepartmentList({}, res => res.code === 200 ? (this.depList = res.data) : this.getDepFail(res.msg), res => this.getDepFail(res.msg || res.errMsg))
    },
    /**
     * 获取科室列表失败
     * @param msg
     */
    getDepFail(msg) {
      console.error(msg);
    },
    /**
     * 选择用户头像
     */
    chooseUserAvatar() {
      $utils.chooseImage({
        success:res=> this.avatar = res.base64,
        fail:res=> console.error('fail -->', res)
      })
    },
    /**
     * 选择医院
     * @param res
     */
    hospitalChoice(res) {
      this.$hospitalChoice.open({
        hospitalList: this.hospitalList,
        success: res => res.type === 'confirm' ? this.parseHospitalChoice(res) : this.$hospitalChoice.close()
      })
    },
    /**
     * 处理医院选择
     * @param res
     */
    parseHospitalChoice(res) {
      this.hospitalIndex = res.data._index;
      this.$hospitalChoice.close();
    },
    /**
     * 科室选择
     */
    departmentChoose() {
      this.$departmentChoose.open({
        depList: this.depList,
        success: res => res.type === 'confirm' ? this.parseDepChoice(res) : this.$departmentChoose.close()
      })
    },
    /**
     * 处理科室选择
     * @param res
     */
    parseDepChoice(res) {
      this.depIndex = res.data._index;
      this.$departmentChoose.close();
    },
    /**
     * 职称选择
     */
    titleChoose() {
      this.$titleChoose.open({
        titleList: this.titleList,
        success: res => res.type === 'confirm' ? this.parseTitleChoose(res) : this.$titleChoose.close()
      })
    },
    /**
     * 处理选择职称选择结果
     * @param res
     */
    parseTitleChoose(res) {
      this.titleIndex = res.data._index;
      this.$titleChoose.close();
    },
    /**
     * 输入用户简介
     */
    inputUserRemark() {
      this.$richText.show({
        title: '简介',
        content: this.remark,
        success: res => res.type === 'confirm' && this.parseUserRemark(res)
      })
    },
    /**
     * 处理用户简介
     * @param res
     */
    parseUserRemark(res) {
      console.log(res)
      this.remark = res.data;
    },

    /**
     * 输入用户擅长
     */
    inputUserProficient() {
      this.$richText.show({
        title: '擅长',
        content: this.proficient,
        success: res => res.type === 'confirm' && this.parseUserProficient(res)
      })
    },
    /**
     * 处理用户简介
     * @param res
     */
    parseUserProficient(res) {
      this.proficient = res.data;
    },
    /**
     * 手机号校验
     */
    checkSendTel() {
      console.log('tel -->', this.tel)
      $utils.validatePhoneNumber(this.tel) ? this.sendVerCode() : $utils.showToast('请输入正确的中国地区手机号');
    },
    /**
     * 发送验证码
     */
    sendVerCode() {
      if (this.isSendVerCode) {
        $utils.showToast('验证码，已发送，请注意查收');
      } else {
        $network.sendVerCode(this.buildCodeParams(), res => res.code === 200 ? this.sendSuccess(res) : this.sendFail(res), res => this.sendFail(res));
      }
    },
    /**
     * 构建验证码发送参数
     * @returns {{phone, type: number}}
     */
    buildCodeParams() {
      return {
        phone: this.tel,
        type: 1
      }
    },
    /**
     * 验证码发送失败
     * @param res
     */
    sendFail(res) {
      console.log('res -->', res)
      $utils.showToast(JSON.stringify(res))
    },
    /**
     * 验证码发送成功
     * @param res
     */
    sendSuccess(res) {
      this.isSendVerCode = true;
      this.updateVerCodeTimer();
    },
    /**
     * 验证码倒计时
     */
    updateVerCodeTimer() {
      let num = 60;
      this.timeId = setInterval(() => {
        this.time = num;
        num -= 1;
        num === 0 && this.initVerCodeDom();
      }, 1000)
    },
    /**
     * 初始化验证码按钮文案
     */
    initVerCodeDom() {
      clearInterval(this.timeId);
      this.time = null;
      this.isSendVerCode = false;
    },
    /**
     * 注册检测
     */
    registerCheck() {
      let msg = [];
      if (this.code === null) msg.push('【验证码】');
      if (this.tel === null && !$utils.validatePhoneNumber(this.tel)) msg.push('【手机号】');
      if (this.depIndex === null) msg.push('【科室】');
      if (this.hospitalIndex === null) msg.push('【医院】');
      if (this.username === null) msg.push('【姓名】');
      if (this.idCard === null && !$utils.validateIDCard(this.idCard)) msg.push('【身份证】');
      if (this.avatar === null) msg.push('【头像】');
      msg.length > 0 ? $utils.showToast(`${msg.join('、')}不可为空,或不正确`) : this.uploadUserAvatar();
    },
    /**
     * 上传用户头像
     */
    uploadUserAvatar() {
      $utils.showLoading('头像上传中');
      $network.uploadImage(this.avatar, res => {
        res.code === 200 ? this.uploadAvatarSuccess(res.data) : this.uploadAvatarFail(res.msg);
      }, res => this.uploadAvatarFail('图片上传失败'));
    },
    /**
     * 图片上传失败
     * @param msg
     */
    uploadAvatarFail(msg) {
      $utils.hideLoading();
      $utils.showToast(msg);
    },
    /**
     * 用户头像上传成功
     */
    uploadAvatarSuccess(url) {
      this.avatarUrl = url;
      this.userRegister();
    },
    /**
     * 用户注册
     */
    userRegister() {
      $network.register(this.buildRegisterParams(), res => {
        res.code === 200 ? this.registerSuccess() : this.registerFail(res.msg);
      }, res => this.registerFail('注册失败'))
    },
    /**
     * 用户注册失败
     * @param msg
     */
    registerFail(msg){
      console.error(msg);
      $utils.showToast(msg);
    },
    /**
     * 跳转到注册成功提示页面
     */
    registerSuccess(){
      this.$router.push('/register/success');
    },
    /**
     * 构建用户注册参数
     * @returns {{}}
     */
    buildRegisterParams() {
      return {
        ability: 1,
        avatar: this.avatarUrl,
        certNum: this.numberOne,
        departId: this.depList[this.depIndex].id,
        goodDisease: this.proficient,
        hospId: this.hospitalList[this.hospitalIndex].id,
        idCard: this.idCard,
        introDesc: this.remark,
        license: this.numberTwo,
        // password: $utils.md5Encrypt(this.password),
        smCode: this.code,
        titleId: this.titleList[this.titleIndex].id,
        userName: this.username,
        userTel: this.tel
      }
    },
    /**
     * 页面返回
     */
    pageBack(){
      $utils.back();
    },
  }
}
</script>

<style lang="less">
.register_page {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .register_box {
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    //width: 600px;
    padding: 30px;

    .title {
      font-weight: bold;
      width: 100%;
      border-bottom: 0.5px solid rgba(0, 0, 0, 0.1);
      padding-bottom: 10px;
    }

    .input_container {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      column-gap: 30px;
    }

    .row {
      margin-top: 30px;
      display: flex;
      align-items: flex-start;

      .key {
        width: 158px;
        height: 35px;
        line-height: 35px;
        text-align: right;
        color: rgba(0, 0, 0, 0.5);
        padding: 0px 10px;

        .master {
          color: #ff0000;
        }
      }

      .input {
        width: 300px;
        height: 35px;
        outline: none;
        border: 1px solid rgba(0, 0, 0, 0.2);
        text-align: center;
        border-radius: 10px;

        &::placeholder {
          color: rgba(0, 0, 0, 0.5);
        }

        &:hover {
          color: #21a4ff;
          border: 0.5px solid #21a4ff;


          &::placeholder {
            color: #21a4ff;
          }
        }
      }

      .select {
        width: 300px;
        height: 35px;
        outline: none;
        border: 1px solid rgba(0, 0, 0, 0.2);
        text-align: center;
        border-radius: 10px;
        line-height: 35px;
        cursor: pointer;
        overflow: hidden;

        &:hover {
          color: #21a4ff;
          border: 0.5px solid #21a4ff;
        }
      }

      .select_not {
        color: rgba(0, 0, 0, 0.5);
      }

      .right {
        width: 300px;
        height: 35px;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .no_image {
          width: 100px;
          height: 100px;
          text-align: center;
          line-height: 100px;
          border-radius: 10px;
          border: 0.5px solid rgba(0, 0, 0, 0.1);
          cursor: pointer;
          color: rgba(0, 0, 0, 0.4);

          &:hover {
            color: #21a4ff;
            border: 0.5px solid #21a4ff;
          }
        }

        .avatar {
          width: 100px;
          height: 100px;
          border-radius: 10px;
          border: 0.5px solid rgba(0, 0, 0, 0.1);
          cursor: pointer;
        }

        .input {
          width: 175px;
        }

        .ver_code {
          background-color: rgba(0, 0, 0, 0.1);
          height: 35px;
          line-height: 35px;
          width: 105px;
          border-radius: 10px;
          text-align: center;
          cursor: pointer;
        }
      }
    }

    .row_remark {
      margin-top: 60px;
    }

    .image_box {
      margin: 60px 0px 30px 0px;
    }

    .btn_list {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      padding-top: 20px;
      border-top: 0.5px solid rgba(0, 0, 0, 0.1);
      margin-top: 30px;

      .btn {
        height: 35px;
        line-height: 35px;
        background-color: rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        text-align: center;
        cursor: pointer;

        &:active {
          background-color: rgba(0, 0, 0, 0.1);
          color: rgba(0, 0, 0, 0.5);
        }
      }

      .back {
        width: 100px;
        margin-right: 30px;
      }

      .register {
        background-color: #21a4ff;
        color: #ffffff;
        width: 200px;
      }
    }

  }
}
</style>