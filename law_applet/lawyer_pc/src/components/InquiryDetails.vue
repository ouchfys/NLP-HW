<template>
  <div class="inquiry_details" v-show="isShow" @click.stop="isShow = !isShow">
    <div class="container">
      <div class="row">
        <div class="key">流水号</div>
        <div class="value">{{ inquiryInfo.id || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">状态</div>
        <div class="value">{{ getInquiryStatus(inquiryInfo.status)}}</div>
      </div>
      <div class="row">
        <div class="key">医院名称</div>
        <div class="value">{{ inquiryInfo.hospName || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">科室名称</div>
        <div class="value">{{ inquiryInfo.departName || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">医生姓名</div>
        <div class="value">{{ inquiryInfo.doctorName || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">医生ID</div>
        <div class="value">{{ inquiryInfo.doctorUserId || '--'}}</div>
      </div>
      <div class="row">
        <div class="key"> 咨询时间</div>
        <div class="value">{{ inquiryInfo.inquiryTime || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">问诊类型</div>
        <div class="value">{{ inquiryInfo.inquiryType || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">问诊结论</div>
        <div class="value">{{ inquiryInfo.inquiryConc || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">居民身份证</div>
        <div class="value">{{ inquiryInfo.residentIdCard || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">居民姓名</div>
        <div class="value">{{ inquiryInfo.residentName || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">居民电话信息</div>
        <div class="value">{{ inquiryInfo.residentTel || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">居民ID</div>
        <div class="value">{{ inquiryInfo.residentUserId || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">居民注册手机号</div>
        <div class="value">{{ inquiryInfo.residentUserTel || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">开始时间</div>
        <div class="value">{{ inquiryInfo.startTime || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">创建时间</div>
        <div class="value">{{ inquiryInfo.createTime || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">创建时间</div>
        <div class="value">{{ inquiryInfo.createTime || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">结束时间</div>
        <div class="value">{{ inquiryInfo.endTime || '--'}}</div>
      </div>

      <div class="row">
        <div class="key">咨询时长</div>
        <div class="value">{{ inquiryInfo.duration || '--'}}</div>
      </div>
      <div class="row">
        <div class="key">更新时间</div>
        <div class="value">{{ inquiryInfo.updateTime || '--' }}</div>
      </div>
    </div>
  </div>
</template>

<script>

import $utils from "@/commons/utils";
import $enums from "@/commons/enums";

export default {
  name: 'InquiryDetails',
  data() {
    return {
      isShow: false,
      inquiryInfo: {},
      success: null,
      fail: null
    }
  },
  methods: {
    /**
     * 显示组件
     * @param res
     * - inquiryInfo
     */
    show(res) {
      console.log('res ---------------->', res)
      if ($utils.isEmptyObject(res?.inquiryInfo)) {
        $utils.callback(res.fail, $enums.unknown_parameter);
      } else {
        this.initPageData(res);
      }
    },
    /**
     * 隐藏组件
     */
    hide() {
      this.isShow = false;
    },
    /**
     * 初始化页面数据
     * @param res
     */
    initPageData(res) {
      this.inquiryInfo = res?.inquiryInfo || {};
      this.success = res?.success || null;
      this.fail = res?.fail || null;
      this.isShow = true;
    },
    /**
     * 获取问诊状态
     * @param {number | string} status 
     */
    getInquiryStatus(status){
      return $utils.getInquiryStatus(status);
    },
  }
}
</script>

<style lang="less" scoped>
.inquiry_details{
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: 1fr;
  gap: 30px;
  justify-items: center;
  align-items: center;
  background-color: rgba(0,0,0,.4);
  position: fixed;
  top: 0px;
  left: 0px;
  z-index: 1;
  .container{
    width: 80vw;
    height: 80vh;
    background-color: #ffffff;
    border-radius: 10px;
    display: grid;
    grid-template-columns: repeat(2,1fr);
    gap: 30px;
    padding: 30px;
    .row{
      width: 100%;
      display: grid;
      grid-template-columns: 100px auto;
      .key{
        text-align: center;
        color: #999999;
      }
      .value{
        text-align: center;
        border-bottom: 1px dashed rgba(0,0,0,0.1);
      }
    }
  }
}
</style>