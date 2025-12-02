<template>
  <div class="chat-container">
    <div class="chatTitle">
      <el-page-header @back="goBack" content="咨询页面"> </el-page-header>
      <el-button type="primary" @click="handleComplete()">完成</el-button>
    </div>
    <!-- 聊天消息列表 -->
    <div class="chat-messages" ref="messagesContainer">
      <div v-for="(message, index) in messages" :key="index" class="message">
        <div v-if="message.role == 2" class="user-message">
          <div class="chatMsgContent">
            <div class="chatUsername chatTime">{{ message.insertTime }}</div>
            <div class="chatContent contentMe">{{ message.message }}</div>
          </div>
          <el-avatar
            class="meAvatar"
            :size="50"
            src="https://goflychat.oss-cn-hangzhou.aliyuncs.com/static/upload/avator/2022June/32a988a3c2f8700119fa1f5da1b6a4bd.png"></el-avatar>
        </div>
        <div v-else class="other-message">
          <el-avatar
            class="chatAvatar"
            :size="50"
            src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-avatar>
          <div class="chatMsgContent">
            <div class="chatUsername">{{ message.insertTime }}</div>
            <div class="chatContent">{{ message.message }}</div>
          </div>
        </div>
      </div>
      <div style="height: 80px"></div>
    </div>
    <!-- 输入框及发送按钮 -->
    <div class="input-container">
      <input v-model="newMessage" type="text" placeholder="请输入消息..." />
      <button @click="sendMessage">发送</button>
    </div>
  </div>
</template>

<script>
import $network from "@/commons/network";
import { ElMessageBox } from "element-plus";
import { ElMessage } from "element-plus";

let setValTimer = null;
export default {
  name: "OrderChat",
  data() {
    return {
      messages: [],
      newMessage: "",
      orderDetails: {},
    };
  },

  created() {
    this.orderDetails = this.$route.query;
    this.initPageData();
  },

  methods: {
    initPageData() {
      let params = {
        current: 1,
        size: 40,
        orderNo: this.orderDetails.orderNo,
      };
      $network.messagePage(
        this.objectToFormData(params),
        (res) => {
          if (res.code == 200) {
            let chatItem = {
              message: "哈喽，快来聊天吧!",
              role: 3,
            };
            res.data.records.reverse();
            if (res.data.records.length === 0)
              res.data.records.unshift(chatItem);
            this.messages = res.data.records;
            this.loopGetMessage();
          } else this.showToast(res.msg || "拉取消息列表异常");
          setTimeout(() => {
            this.scrollToBottom()
          },500)
          this.$refs.messagesContainer.scrollTop =
            this.$refs.messagesContainer.scrollHeight;
        },
        (res) => this.showToast(res || "拉取消息列表出错")
      );
    },

    objectToFormData(obj) {
      const formData = new URLSearchParams();
      for (const key in obj) {
        formData.append(key, obj[key]);
      }
      return formData;
    },

    sendMessage() {
      if (this.newMessage.trim() === "") return; // 防止发送空消息
      this.messageSave();
    },

    messageSave() {
      let params = {
        message: this.newMessage,
        receiveUserId: this.orderDetails.userId,
        orderNo: this.orderDetails.orderNo,
        role: 2,
        insertTime: this.getCurTime(),
      };
      $network.messageSend(
        params,
        (res) => {
          if (res.code == 200) {
            this.messages.push(params);
            this.$refs.messagesContainer.scrollTop =
              this.$refs.messagesContainer.scrollHeight;
            this.newMessage = "";
          } else this.showToast(res.msg || "发送消息异常");
        },
        (res) => this.showToast(res || "发送消息出错")
      );
    },

    loopGetMessage() {
      setValTimer = setInterval(() => {
        this.receiveMessage();
      }, 3000);
    },

    receiveMessage() {
      $network.messageReceive(
        this.orderDetails.orderNo,
        (res) => {
          if (res.code == 200) {
            if (res.data.length > 0) {
              let array1 = [...this.messages, ...res.data];
              console.log(array1);
              this.messages = array1;
              console.log(this.messages);
              this.$refs.messagesContainer.scrollTop =
                this.$refs.messagesContainer.scrollHeight;
            }
          } else this.showToast(res.msg || "拉取消息列表异常");
        },
        (res) => this.showToast(res || "拉取消息列表出错")
      );
    },

    goBack() {
      clearInterval(setValTimer);
      this.$router.push("/legal/advice/order");
    },

    handleComplete() {
      ElMessageBox.confirm("您确定要结束用户本次的咨询吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then((res) => {
          this.orderUpdate();
        })
        .catch((res) => {});
    },

    orderUpdate() {
      let params = {
        id: this.orderDetails.id,
        status: 3,
      };
      $network.orderUpdate(
        params,
        (res) => {
          if (res.code == 200) {
            clearInterval(setValTimer);
            this.$router.push("/legal/advice/order");
            ElMessage({
              message: "咨询已结束！",
              type: "success",
            });
          } else this.showToast(res.msg || "结束咨询异常");
        },
        (res) => this.showToast(res || "结束咨询出错")
      );
    },

    scrollToBottom() {
      // 获取消息列表的 DOM 元素
      const messageList = this.$refs.messagesContainer;
      
      // 使用 setTimeout 来确保在 DOM 更新后执行滚动操作
      this.$nextTick(() => {
        // 滚动到底部
        messageList.scrollTop = messageList.scrollHeight;
      });
    },

    /**
     * 获取当前时间
     */
    getCurTime() {
      var date = new Date();
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      var d = date.getDate();
      d = d < 10 ? "0" + d : d;
      var h = date.getHours();
      h = h < 10 ? "0" + h : h;
      var minute = date.getMinutes();
      minute = minute < 10 ? "0" + minute : minute;
      var second = date.getSeconds();
      second = second < 10 ? "0" + second : second;
      return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
    },

    showToast(msg) {
      this.$toast.show({
        msg: typeof msg === "string" ? msg : JSON.stringify(msg),
      });
    },
  },
};
</script>

<style scoped>
/* 样式可以根据实际需要进行调整 */
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f1f5f8;
}

.chatTitle {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  height: 45px;
  border-radius: 10px;
  padding: 0 10px;
}

.chat-messages {
  height: 75vh;
  overflow: auto; /* 当内容溢出容器时显示滚动条 */
  overflow-y: auto;
  padding-top: 80px;
}

.message {
  margin-bottom: 10px;
  width: 100%;
}

.user-message {
  width: 100%;
  display: flex;
  align-items: flex-end;
  justify-content: end;
  margin-bottom: 20px;
}

.other-message {
  display: flex;
  align-items: flex-start;
}

.chatAvatar {
  margin-right: 15px;
  flex-shrink: 0;
  margin-top: 5px;
}

.meAvatar {
  margin-left: 15px;
  flex-shrink: 0;
  margin-top: 5px;
}

.chatUsername {
  font-size: 12px;
  white-space: nowrap;
  color: #999;
  margin-bottom: 2px;
}

.chatTime {
  text-align: right;
}

.chatContent {
  border-radius: 10px 10px 10px 0px;
  padding: 10px;
  background-color: rgb(255, 255, 255);
  box-shadow: 0 5px 30px rgb(50 50 93 / 8%), 0 1px 3px rgb(0 0 0 / 5%);
  font-size: 14px;
  word-break: break-all;
}

.contentMe {
  border-radius: 10px 10px 0px 10px;
  background-color: #70f5f5;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.input-container {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

button {
  padding: 8px 15px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
