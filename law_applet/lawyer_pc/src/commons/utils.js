import CryptoJS from 'crypto-js';
import $store from "@/store/index";
import $config from "@/commons/config";


/**
 * 通用工具类
 */
let $utils = {
    /**
     * 应用
     */
    app: null,
    /**
     * 初始化
     * @param app
     */
    init(app) {
        this.app = app;
    },
    /**
     * 页面跳转
     * @param number
     */
    go(number) {
        if ($utils.isNumber(number)) return;
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$router.go(number);
    },
    /**
     * 页面返回
     */
    back() {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$router.back();
    },
    /**
     * 路由跳转
     * @param {string} path
     */
    push(path) {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$router.push(path);
    },

    /**
     * 计算列表序号
     * @param index
     * @param pageNum
     * @param pageSize
     * @return {*}
     */
    calculateIndexNum(index, pageNum, pageSize) {
        return (pageNum - 1) * pageSize + index + 1;
    },
    /**
     * 获取问诊状态
     * 状态:1 - 问诊中,2 - 已结束,3 - 未开始,4 - 未呼通
     * @param {number | string} status 
     */
    getInquiryStatus(status) {
        switch (`${status}`) {
            case '1':
                return '问诊中';
            case '2':
                return '已结束';
            case '3':
                return '未开始';
            case '4':
                return '未呼通';
            default:
                return '未知'
        }
    },
    /**
     * 判断是否为数字
     * @param number
     * @return {boolean}
     */
    isNumber(number) {
        return typeof number === 'number';
    },
    /**
     * 重启应用
     */
    reloadApp() {
        $utils.app.config.globalProperties.$store.commit('clear', {});
        $utils.hideLoading();
        $utils.hideToast();
        window.history.replaceState(null, null, window.location.pathname);
        window.location.reload();
    },
    /**
     * 显示提示框
     * @param msg
     */
    showToast(msg) {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$toast.show({ msg: typeof msg === 'string' ? msg : JSON.stringify(msg) });
    },
    /**
     * 隐藏提示框
     */
    hideToast() {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$toast.hide();
    },
    /**
     * 显示Loading
     * @param {string} content
     */
    showLoading(content) {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$loading.show({ content });
    },
    /**
     * 隐藏模态框
     */
    hideLoading() {
        if (!$utils.app) return;
        $utils.app.config.globalProperties.$loading?.hide();
    },
    /**
     * 构建图片地址
     * @param {string} avatar
     * @return {*|string}
     */
    buildImageUrl(avatar = '') {
        if (!avatar) avatar = '';
        if (avatar.startsWith('http')) return avatar;
        if (avatar.startsWith('/imgs')) return `${$config.FS_SERVICE}${avatar}`
        return '/assets/logo.png';
    },
    /**
     * 获取用户信息
     * @param success
     */
    getUserInfo(success) {
        let userInfo = $store.state.userInfo || this.jsonParse(window.localStorage.getItem('cache_temp')).userTel || {};
        if (!this.isEmptyObject(userInfo)) this.callback(success, userInfo);
        else setTimeout(() => this.getUserInfo(success), 500);
    },
    /**
     * 获取当前医生ID
     * @param success
     */
    getUserId(success) {
        let userId = $store.state.userInfo?.userId || null;
        if (userId) this.callback(success, userId);
        else setTimeout(() => this.getUserId(success), 500);
    },
    /**
     * 判断是否是空对象
     * @param obj
     * @returns {boolean}
     */
    isEmptyObject(obj) {
        return Object.keys(obj).length === 0 && obj.constructor === Object;
    },
    /**
     * 获取用户手机号
     * @returns {*|string|string}
     */
    getUserTel() {
        try {
            return $store.state.userTel || $store.state.userInfo.userTel || this.jsonParse(window.localStorage.getItem('cache_temp')).userTel || '';
        } catch (e) {
            return '';
        }
    },
    /**
     * 获取Authorization
     * @returns {*|string|string}
     */
    getAuthorization() {
        try {
            let authorization = $store.state?.authorization || '';
            let cache_temp = this.jsonParse(window.localStorage.getItem('cache_temp'));
            if (cache_temp) authorization = cache_temp?.authorization;
            return authorization || '';
        } catch (e) {
            return '';
        }
    },
    /**
     * 获取Authorization - V1
     * @param success
     */
    getAuthorizationV1(success) {
        let authorization = this.getAuthorization();
        if (authorization.length > 0) this.callback(success, authorization);
        else setTimeout(() => this.getAuthorizationV1(success), 500);
    },
    /**
     * json 字符串解析
     * @param jsonStr
     * @returns {{}|string}
     */
    jsonParse(jsonStr) {
        try {
            return JSON.parse(jsonStr)
        } catch (e) {
            console.error('JSON字符串解析失败 -->', e);
            return {};
        }
    },
    /**
     * 身份证验证
     * @param idCard
     * @returns {boolean}
     */
    validateIDCard(idCard) {
        // 身份证号码正则表达式
        const regex = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
        // 判断身份证号码是否符合规则
        if (!regex.test(idCard)) return false;
        // 计算身份证号码校验码
        let sum = 0;
        let weight = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
        let checkCodes = ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
        for (let i = 0; i < 17; i++) {
            sum += parseInt(idCard.charAt(i)) * weight[i];
        }
        let remainder = sum % 11;
        let checkCodeIndex = checkCodes[remainder];
        // 判断校验码是否正确
        if (checkCodeIndex !== idCard.toUpperCase().charAt(17)) return false;

        return true;
    },

    /**
     * 校验手机号是否是中国地区正确的手机号
     * @param phoneNumber
     * @returns {boolean}
     */
    validatePhoneNumber(phoneNumber = '') {
        return /^1[3-9]\d{9}$/.test(phoneNumber);
    },
    /**
     * 选择图片
     * @param params
     * - success - Function - 成功回调
     * - fail - Function - 失败回调
     */
    chooseImage(params) {
        let inputEl = window.document.createElement('input');
        inputEl.type = 'file';
        inputEl.accept = 'image/*';
        inputEl.onchange = res => this.parseFileChange(res.target.files[0], params.success, params.fail);
        inputEl.onerror = res => this.callback(params.fail, res)
        inputEl.click();
    },
    /**
     * 处理文件改变是返回数据
     * @param file
     * @param success
     * @param fail
     */
    parseFileChange(file, success, fail) {
        if (file) {
            this.readFileAsBase64(file, base64 => {
                this.callback(success, { file, base64 });
            }, () => {
                this.callback(fail, 'fail:file to base64');
            })
        } else {
            this.callback(fail, 'fail:no file change');
        }
    },
    /**
     * 文件转base64
     * @param file
     * @param success
     * @param fail
     */
    readFileAsBase64(file, success, fail) {
        const reader = new FileReader();
        reader.onloadend = () => this.callback(success, reader.result);
        reader.onerror = error => this.callback(fail, error);
        reader.readAsDataURL(file);
    },
    /**
     * 回调函数
     * @param _callback
     * @param data
     */
    callback(_callback, data) {
        typeof _callback === 'function' && _callback(data);
    },
    /**
     * 构建显示值
     * @param {{}} item 
     */
    getMeasureKeyValue(item) {
        if (item.basalMetabolicRate) return $utils.buildMeasureItem('基础代谢率', item.basalMetabolicRate);
        else if (item.bloodGlucose) return $utils.buildMeasureItem('血糖', item.bloodGlucose);
        else if (item.bloodOxygenConcentration) return $utils.buildMeasureItem('血氧浓度', item.bloodOxygenConcentration);
        else if (item.bloodPressure) return $utils.buildMeasureItem('血压', item.bloodPressure);
        else if (item.bloodketone) return $utils.buildMeasureItem('血酮', item.bloodketone);
        else if (item.bmi) return $utils.buildMeasureItem('BMI', item.bmi);
        else if (item.bone) return $utils.buildMeasureItem('骨骼量', item.bone);
        else if (item.boneMass) return $utils.buildMeasureItem('骨量', item.boneMass);
        else if (item.cholesterol) return $utils.buildMeasureItem('胆固醇', item.cholesterol);
        else if (item.diastolicPressure && item.systolicPressure) return $utils.buildMeasureItem('舒张压（低压） / 收缩压(高压)', `${item.diastolicPressure} / ${item.systolicPressure}`);
        else if (item.fatFreeWeight) return $utils.buildMeasureItem('去脂体重', item.fatFreeWeight);
        else if (item.fatMass) return $utils.buildMeasureItem('脂肪量', item.fatMass);
        else if (item.fatRate) return $utils.buildMeasureItem('脂肪率', item.fatRate);
        else if (item.heartRate) return $utils.buildMeasureItem('心率', item.heartRate);
        else if (item.moistureContent) return $utils.buildMeasureItem('水分率', item.moistureContent);
        else if (item.muscleMass) return $utils.buildMeasureItem('肌肉量', item.muscleMass);
        else if (item.muscleRate) return $utils.buildMeasureItem('肌肉率', item.muscleRate);
        else if (item.physicalAge) return $utils.buildMeasureItem('身体年龄', item.physicalAge);
        else if (item.protein) return $utils.buildMeasureItem('蛋白质', item.protein);
        else if (item.resistance) return $utils.buildMeasureItem('阻抗值', item.resistance);
        else if (item.skeletalMuscle) return $utils.buildMeasureItem('骨骼肌', item.skeletalMuscle);
        else if (item.sleep) return $utils.buildMeasureItem('睡眠', item.sleep);
        else if (item.steps) return $utils.buildMeasureItem('步数', item.steps);
        else if (item.temperature) return $utils.buildMeasureItem('体温', item.temperature);
        else if (item.triglyceride) return $utils.buildMeasureItem('甘油三酯', item.triglyceride);
        else if (item.uricacid) return $utils.buildMeasureItem('尿酸', item.uricacid);
        else if (item.visceralFatIndex) return $utils.buildMeasureItem('内脏脂肪指数', item.visceralFatIndex);
        else return {};
    },
    /**
     * 构建测量数据item 
     * @param key
     * @param value 
     */
    buildMeasureItem(key, value) {
        return { key, value }
    },

}

export default $utils;
