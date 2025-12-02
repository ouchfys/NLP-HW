<template>
  <!-- 居民咨询记录和测量记录列表 -->
  <div class="inhabitant_details" v-show="isShow">
    <div class="container">
      <div class="header_top">
        <div class="left"></div>
        <div class="middle">{{ userInfo.userName }}</div>
        <div class="right">
          <div class="btn" @click.stop="isShow = !isShow">x</div>
        </div>
      </div>
      <div class="info_box">
        <!--问诊记录-->
        <div class="record_list" style="margin-top: -30px;">
          <div class="row t_head">
            <div class="td" v-for="(item, index) in ['序号', '问诊医生', '咨询时间', '咨询时长', '问诊结论']" :style="item === '问诊结论' ? 'grid-column-end:span 2' : ''">
              {{ item }}
            </div>
          </div>
          <div class="record_list" v-if="inquiryRecord.records.length > 0">
            <div class="row" v-for="(item, index) in inquiryRecord.records">
              <div class="td">{{ getOrderNum(index, inquiryRecord.pageNum, inquiryRecord.pageSize) }}</div>
              <div class="td">{{ item.doctorName }}</div>
              <div class="td">{{ item.inquiryTime }}</div>
              <div class="td">{{ item.duration }}</div>
              <div class="td" style="grid-column-end:span 2;">{{ item.inquiryConc }}</div>
            </div>
            <div class="page_info">
              <div class="box">
                <div class="tips">每页显示</div>
                <input class="input" type="number" :value="inquiryRecord.pageSize" @focusout="pageSizeChange_inquiry">
                <div class="tips">条</div>
              </div>
              <div class="box">
                <div class="tips">跳转到</div>
                <input class="input" type="number" :value="inquiryRecord.pageNum" @focusout="pageNumChange_inquiry">
                <div class="tips">页</div>
                <div class="btn" @click.stop="prePageInquiry()">上一页</div>
                <div class="btn" @click.stop="nextPageInquiry()">下一页</div>
              </div>
            </div>
          </div>
        </div>
        <!--测量记录-->
        <div class="record_list">
          <div class="row t_head">
            <div class="td" v-for="(item, index) in ['序号', '用户账号', '时间', '指标', '数值', '家庭成员']">
              {{ item }}
            </div>
          </div>
          <div class="record_list" v-if="measureRecord.records.length > 0">
            <div class="row" v-for="(item, index) in measureRecord.records">
              <div class="td">{{ getOrderNum(index, measureRecord.pageNum, measureRecord.pageSize) }}</div>
              <div class="td">{{ item.userAccount }}</div>
              <div class="td">{{ item.measureTime }}</div>
              <div class="td">{{ buildMeasureKeyValue(item)['key'] || '--' }}</div>
              <div class="td">{{ buildMeasureKeyValue(item)['value'] || '--'  }}</div>
              <div class="td">{{ item.archiveMember || '--' }}</div>
            </div>
            <div class="page_info">
              <div class="box">
                <div class="tips">每页显示</div>
                <input class="input" type="number" :value="measureRecord.pageSize" @focusout="pageSizeChange_measure">
                <div class="tips">条</div>
              </div>
              <div class="box">
                <div class="tips">跳转到</div>
                <input class="input" type="number" :value="measureRecord.pageNum" @focusout="pageNumChange_measure">
                <div class="tips">页</div>
                <div class="btn" @click.stop="prePageMeasure()">上一页</div>
                <div class="btn" @click.stop="nextPageMeasure()">下一页</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $network from "@/commons/network";
import $utils from "@/commons/utils";
import $enums from "@/commons/enums";

export default {
  name: 'InhabitantDetails',
  /**
   * 组件数据池
   * @returns {{userInfo: {}, isShow: boolean}}
   */
  data() {
    return {
      isShow: false,
      isShowCloseBtn: true,
      userId: null,
      userInfo: {},
      measureRecord: {
        records: [],
        pageNum: 1,
        pageSize: 5,
        total: 0,
        pages: 0,
      },
      inquiryRecord: {
        records: [],
        pageNum: 1,
        pageSize: 5,
        total: 0,
        pages: 0,
      }
    }
  },
  /**
   * 方法合集
   */
  methods: {
    /**
     * 显示居民西信息
     * @param options
     * - userId - number | string - 居民ID
     */
    show(options) {
      if (options.userId) {
        this.initPageData(options);
      } else {
        let reuslt = $enums.parameter_error;
        reuslt.msg = `${reuslt.msg} - ${'userId 不能为空'}`;
        $utils.callback(options.fail, reuslt);
      }
    },
    /**
     * 隐藏
     */
    hide() {
      this.isShow = false;
    },
    /**
     * 初始化页面数据
     * @param res
     */
    initPageData(res) {
      this.userId = res.userId;
      this.isShowCloseBtn = res.isShowCloseBtn;
      this.success = res.success || null;
      this.fail = res.fail || null;
      this.getUserInfo(res.userId);
      this.getInquiryRecordList(1);
      this.getMesureRecordList(1);
    },
    /**
     * 获取用户归档数据
     * @param pageNum
     */
    getMesureRecordList(pageNum) {
      $network.getArchivistData({
        current: pageNum,
        size: this.measureRecord.pageSize,
        userId: this.userId
      }, res => {
        res.code === 200 ? this.parseMeasureRecord(res.data) : this.getMeasreRecordFail(res.msg);
      }, res => this.getMeasreRecordFail(res.errMsg));
    },
    /**
     * 获取测量数据失败
     * @param msg
     */
    getMeasreRecordFail(msg) {
      let result = $enums.measure_record_get_fail;
      result.msg = `${result.msg} - ${msg}`;
      $utils.callback(this.fail, result);
    },
    /**
     * 处理测量数据列表
     * @param res
     */
    parseMeasureRecord(res) {
      this.measureRecord.total = res.total;
      this.measureRecord.pages = res.pages;
      this.measureRecord.pageNum = res.current;
      this.measureRecord.records = res.records;
    },
    /**
     * 获取问诊列表
     * @param pageNum
     */
    getInquiryRecordList(pageNum) {
      $network.getReocrdInquiry({
        userId: this.userId,
        current: pageNum,
        size: this.inquiryRecord.pageSize
      }, res => {
        res.code === 200 ? this.parseInquiryRecord(res.data) : this.getInquiryRecordFail(res.msg);
      }, res => this.getInquiryRecordFail(res.errMsg));
    },
    /**
     * 问诊记录获取失败
     * @param {string} msg
     */
    getInquiryRecordFail(msg) {
      let result = $enums.inquiry_record_get_fail;
      result.msg = `${result.msg} - ${msg}`;
      $utils.callback(this.fail, result)
    },
    /**
     * 这里问诊记录
     */
    parseInquiryRecord(res) {
      this.inquiryRecord.pages = res.pages;
      this.inquiryRecord.total = res.total;
      this.inquiryRecord.pageNum = res.current;
      this.inquiryRecord.records = res.records; 
    },
    /**
     * 构建显示值
     * @param {{}} item 
     */
    buildMeasureKeyValue(item) {   
      return $utils.getMeasureKeyValue(item);
    }, 
    /**
     * 获取更多数据
     * @param type
     */
    getMoreRecord(type) {
      switch (type) {
        case 'inquiryRecord':
          this.getInquiryRecordList(this.inquiryRecord.pageNum + 1);
          break;
        case 'measureRecord':
          this.getMesureRecordList(this.measureRecord.pageNum + 1); 
          break;
      }
    },
    /**
     * 获取列表序号
     * @param index
     * @param pageNum
     * @param pageSize
     * @return {*}
     */
    getOrderNum(index, pageNum, pageSize) {
      return $utils.calculateIndexNum(index, pageNum, pageSize)
    },
    /**
     * 分页大小改变 - 问诊记录
     * @param res
     */
    pageSizeChange_inquiry(res) {
      let pageSize = parseInt(res.target.value);
      if (pageSize < 1) return;
      this.inquiryRecord.pageSize = pageSize;
      this.getInquiryRecordList(1);
    },
    /**
     * 分页大小改变 - 测量记录
     * @param res
     */
    pageSizeChange_measure(res) {
      let pageSize = parseInt(res.target.value);
      if (pageSize < 1) return;
      this.measureRecord.pageSize = pageSize;
      this.getMesureRecordList(1);
    },
    /**
     * 页码改变 - 问诊记录
     * @param res
     */
    pageNumChange_inquiry(res) {
      let pageNum = parseInt(res.target.value);
      if (pageNum < 1 || pageNum > this.inquiryRecord.pages) return;
      this.getInquiryRecordList(pageNum);
    },

    /**
     * 页码改变 - 测量记录
     * @param res
     */
    pageNumChange_measure(res) {
      let pageNum = parseInt(res.target.value);
      if (pageNum < 1 || pageNum > this.measureRecord.pages) return;
      this.getMesureRecordList(pageNum);
    },
    /**
     * 上一页 - 问诊记录
     */
    prePageInquiry() {
      this.inquiryRecord.pageNum > 1 ? this.getInquiryRecordList(this.inquiryRecord.pageNum - 1) : $utils.showToast('已是第一页');
    },

    /**
     * 上一页 - 测量记录
     */
    prePageMeasure() {
      this.measureRecord.pageNum > 1 ? this.getMesureRecordList(this.inquiryRecord.pageNum - 1) : $utils.showToast('已是第一页');
    },
    /**
     * 下一页 - 问诊记录
     */
    nextPageInquiry() {
      this.inquiryRecord.pageNum < this.inquiryRecord.pages ? this.getInquiryRecordList(this.inquiryRecord.pageNum + 1) : $utils.showToast('已加载全部')
    },

    /**
     * 下一页 - 问诊记录
     */
    nextPageMeasure() {
      this.measureRecord.pageNum < this.measureRecord.pages ? this.getMesureRecordList(this.measureRecord.pageNum + 1) : $utils.showToast('已加载全部')
    },
    /**
     * 获取用户信息
     */
    getUserInfo(userId) {
      $network.getUserInfoById(this.buildParamsUser(userId), res => {
        res.code === 200 ? this.parseUserInfo(res.data) : this.getInfoFail(res.msg);
      }, res => this.getInfoFail(res))
    },
    /**
     * 构建用户信息请求参数
     * @param {number | string} userId
     */
    buildParamsUser(userId) {
      return {
        userId
      }
    },
    /**
     * 获取用户信息失败
     * @param msg
     */
    getInfoFail(msg) {
      let res = $enums.get_user_info_fail;
      res.msg = msg;
      $utils.callback(this.fail, res);
    },
    /**
     * 处理用户信息
     * @param userInfo
     */
    parseUserInfo(userInfo) {
      this.userInfo = userInfo;
      this.isShow = true;
    },
    /**
     * 关闭模态框面板
     */
    closeUserInfo() {
      this.isShow = false;
      $utils.callback(this.success, $enums.close_modal)
    },
  }
}
</script>

<style lang="less" scoped>
.inhabitant_details {
  position: fixed;
  top: 0px;
  left: 0px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, .4);
  width: 100vw;
  height: 100vh;

  .container {
    width: 90%;
    height: 90%;
    overflow: hidden;
    overflow-y: auto;
    border-radius: 10px;
    background-color: #ffffff;
    position: relative;

    //  头部导航
    .header_top {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 30px;

      .middle {
        font-weight: bold;
        letter-spacing: 2px;
      }

      .right {
        .btn {
          width: 26px;
          height: 26px;
          background-color: rgba(0, 0, 0, .1);
          text-align: center;
          line-height: 26px;
          border-radius: 50%;
          color: #999999;
          cursor: pointer;

          &:hover {
            background-color: #21a4ff;
            color: #ffffff;
          }
        }
      }
    }

    .info_box {
      -moz-column-gap: 30px;
      column-gap: 30px;
      border: 1px solid rgba(0, 0, 0, 0.1);
      border-left: none;
      border-right: none;
      width: 100%;
      padding: 20px 30px;
      overflow: hidden;
      overflow-y: auto;


      .record_list {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;

        .title {
          width: 100%;
        }

        .row {
          width: 100%;
          display: grid;
          grid-template-columns: 50px repeat(5, 1fr);
          padding: 10px 0px;

          .td {
            width: 100%;
            text-align: center;
          }

          &:last-child {
            border-bottom: none;
          }
        }

        .t_head {
          width: 100%;
        }

        .page_info {
          width: 100%;
          padding: 15px;
          display: flex;
          align-items: center;
          justify-content: space-between;

          .box {
            display: flex;
            align-items: center;

            .tips {
              color: #999999;
            }

            .input {
              outline: none;
              width: 50px;
              height: 35px;
              text-align: center;
              border-radius: 10px;
              border: 1px solid rgba(0, 0, 0, .1);
              margin: 0px 10px;


              &::-webkit-inner-spin-button,
              &::-webkit-outer-spin-button {
                -webkit-appearance: none;
              }


              &:focus {

                border: 1px solid #21a4ff;
              }

              &::placeholder {
                color: #999999;
              }
            }

            .btn {
              width: 100px;
              height: 35px;
              text-align: center;
              line-height: 35px;
              background-color: rgba(0, 0, 0, 0.1);
              border-radius: 10px;
              cursor: pointer;
              margin-left: 30px;

              &:hover {
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

        .t_head {
          font-weight: bold;
          margin-top: 30px;
          background-color: #21a4ff;
          color: #ffffff;
        }
      }
    }

    .btn_list {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-around;
      position: absolute;
      bottom: 0px;
      background-color: rgba(0, 0, 0, .1);

      .btn {
        margin: 10px 0px;
        width: 100px;
        height: 35px;
        text-align: center;
        line-height: 35px;
        border-radius: 10px;
        color: #21a4ff;
        cursor: pointer;

        &:active {
          background-color: rgba(0, 0, 0, 0.1);
          color: #999999;
        }
      }
    }
  }


}
</style>