import {createStore} from "vuex";
import $utils from '../commons/utils'; 

/**
 * 项目缓存
 * @type {Store<{}>}
 */
let $store = createStore({
    /**
     * 数据池
     */
    state: {
        authorization: '',  //  token
        userTel: '',    //  电话号码
        userInfo: {},   //  用户信息
        titleList: [],   //  职称列表
        isInInquiry:false,//    是否处于问诊中
        measureRecordDetails:{},    //  测量记录详情
        inquiryRecordDetails:{},    //  问诊记录详情
        inquiryStatus: 2, //  问诊状态  1-接诊中 2-问诊结束或未发起
    },
    getters: {
        getAuthorization(state) {
            return state?.authorization || '';
        }
    },
    /**
     * 同步操作
     */
    mutations: {



        /**
         * 设置token
         * @param state
         * @param authorization
         */
        setAuthorization(state, authorization) {
            state.authorization = authorization;
        },
        /**
         * 设置用户手手机号
         * @param {{}} state
         * @param {{}} userTel
         */
        setUserTel(state, userTel) {
            state.userTel = userTel || null;
        },
        /**
         * 设置用户信息
         * @param state
         * @param data
         */
        setUserInfo(state, data) {
            state.userInfo = data || {};
        },
        /**
         * 设置职称列表
         * @param state
         * @param data
         */
        setTitleList(state, data) {
            state.titleList = data || [];
        },

        /**
         * 清楚项目所有缓存 - 谨慎使用
         * @param state
         */
        clear(state) {
            // window.localStorage.clear();
            state.userInfo = {};
            state.userTel = '';
            state.authorization = '';
            state.titleList = [];
        },
        /**
         * 将缓存数据放置到localStorage中
         * @param state
         */
        saveState(state) {
            window.localStorage.setItem('cache_temp', JSON.stringify(state));
        },
        /**
         * 从 localStorage 中恢复数据
         * @param state
         */
        updateState(state) {
            let temp = $utils.jsonParse(window.localStorage.getItem('cache_temp'));
            state.authorization = temp?.authorization || '';
            state.userTel = temp?.userTel || '';
            state.userInfo = temp?.userInfo || null;
            state.titleList = temp?.titleList || [];
            // window.localStorage.clear();
        },
    },
    /**
     * 异步操作
     */
    actions: {},
    modules: {}
});
window.onbeforeunload = res=>{
    if($store.state.isInInquiry){
        return '';
    }
}
/**
 * 页面刷新时，将内存数据保存到缓存中
 */
window.addEventListener('beforeunload', () =>  $store.commit('saveState'));
/**
 * 页面加载时，将缓存数据存到内存中
 */
window.addEventListener('load', () => $store.commit('updateState'));

export default $store;