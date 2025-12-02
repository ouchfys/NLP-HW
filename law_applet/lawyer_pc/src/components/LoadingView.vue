<template>
  <div class="loading_c" v-show="isShow">
    <div class="loader"></div>
    <span class="span">{{ content }}</span>
  </div>
</template>

<script>

export default {
  name: 'LoadingView',
  /**
   * 页面数据
   */
  data(){
    return {
      isShow:false,
      content:'',
      success:null,
      fail:null
    }
  },
  /**
   * 方法合集
   */
  methods:{
    /**
     * 显示loading
     * @param params
     */
    show(params){
      if(typeof params === 'string'){
        this.content = params;
      }else{
        this.content = params.content || 'Loading';
        this.success = params.success || null;
        this.fail = params.fail || null;
      }
      this.isShow = true;
    },
    /**
     * 隐藏loading
     * @param params
     */
    hide(params = {}){
      this.success = params.success || null;
      this.fail = params.fail || null;
      this.isShow = false;
    }
  }
}
</script>

<style>
.loading_c {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  left: 0px;
  background: rgba(0,0,0,0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;

  .loader {
    position: absolute;
    top: 40%;
    border-top: 0.3em solid rgba(0, 0, 0, 0.1);
    border-right: 0.3em solid rgba(0, 0, 0, 0.1);
    border-bottom: 0.3em solid rgba(0, 0, 0, 0.1);
    border-left: 0.3em solid #21a4ff;
    -moz-transform: translateZ(0);
    -webkit-transform: translateZ(0);
    transform: translateY(40%);
    -moz-animation: loader 600ms infinite linear;
    -webkit-animation: loader 600ms infinite linear;
    animation: loader 600ms infinite linear;
    -moz-transition: all 600ms ease;
    -o-transition: all 600ms ease;
    -webkit-transition: all 600ms ease;
    transition: all 600ms ease;
    z-index: 9999;
  }

  .loader,
  .loader:after {
    border-radius: 50%;
    width: 2em;
    height: 2em;
  }

  .span {
    position: absolute;
    top: 45%;
    margin-left: -7px;
    font-weight: 400;
    color: #ffffff;
  }
}


@-webkit-keyframes loader {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
    border-left: 0.3em solid #21a4ff;
  }
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
    border-left: 0.3em solid #21a4ff;
  }
}

@-moz-keyframes loader {
  0% {
    -moz-transform: rotate(0deg);
    transform: rotate(0deg);
    border-left: 0.3em solid #21a4ff;
  }
  100% {
    -moz-transform: rotate(360deg);
    transform: rotate(360deg);
    border-left: 0.3em solid #21a4ff;
  }
}

@keyframes loader {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
    border-left: 0.3em solid #21a4ff;
  }
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
    border-left: 0.3em solid #21a4ff;
  }
}
</style>