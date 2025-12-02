import $config from "@/commons/config";
import $api from "@/commons/api";
import $utils from '@/commons/utils'

/**
 * 网络请求接口
 */
let $network = {
    userLogin(data, success, fail) {
        $network.post_java($api.userLogin, data, success, fail);
    },

    userInfo(data, success, fail) {
        $network.post_java($api.userInfo, data, success, fail);
    },

    userUpdate(data, success, fail) {
        $network.post_java($api.userUpdate, data, success, fail);
    },

    uploadImage(file, success, fail) {
        const formData = new FormData();
        formData.append('file', file);
        let headers = this.header_java_formData();
        $network.post_java($api.imageUpload, formData, success, fail, headers);
    },

    lawInfoList(current, size, success, fail) {
        $network.get_java(`${$api.lawInfoList}?current=${current}&size=${size}`, success, fail);
    },

    commentPage(current, size, success, fail) {
        $network.get_java(`${$api.commentList}?current=${current}&size=${size}`, success, fail);
    },

    commentUpdate(data, success, fail){
        $network.post_java($api.commentUpdate,data, success,fail)
    },
    deleteComment(data, success, fail){
        $network.post_java($api.deleteComment,data, success,fail)
    },

    lawInfoAddUpdate(data, success, fail) {
        $network.post_java($api.lawInfoAddUpdate, data, success, fail);
    },

    lawInfoDelete(data, success, fail) {
        $network.post_java(`${$api.lawInfoDelete}${data}`,null, success, fail);
    },

    userPage(current, size, role, success, fail) {
        $network.get_java(`${$api.userPage}?current=${current}&size=${size}&roleId=${role}`, success, fail);
    },

    userRegister(data, success, fail) {
        $network.post_java($api.userRegister, data, success, fail);
    },

    userRemove(data, success, fail) {
        $network.post_java(`${$api.userRemove}?ids=${data}`, null, success, fail);
    },

    orderPage(current, size, success, fail) {
        $network.get_java(`${$api.orderPage}?current=${current}&size=${size}`, success, fail);
    },

    messagePage(data, success, fail) {
        $network.post_java_2($api.messagePage, data, success, fail);
    },

    messageSend(data, success, fail) {
        $network.post_java_3($api.messageSend, data, success, fail);
    },

    messageReceive(data, success, fail) {
        $network.post_java_3(`${$api.messageReceive}${data}`, null, success, fail);
    },

    orderUpdate(data, success, fail) {
        $network.post_java($api.orderUpdate, data, success, fail);
    },

    /**
     * 网络请求 - get - Java
     * @param {string} uri
     * @param {Function} success
     * @param {Function} fail
     * @param {string} header
     */
    get_java(uri, success = null, fail = null, header = '') {
        $utils.showLoading('loading……');
        fetch(`${$config.JAVA_SERVICE}${uri}`, {
            method: 'GET',
            headers: header || $network.header_java(),
        }).then(res => res.json()).then(res => {
            $utils.hideLoading();
            $network.callback(success, res);
        }).catch(res => {
            $utils.hideLoading();
            $network.callback(fail, res);
        });
    },
    /**
     * 网络请求 - post - Java
     * @param uri
     * @param data
     * @param success
     * @param fail
     * @param headers
     */
    post_java(uri, data, success, fail, headers) {
        $utils.showLoading('loading……');
        fetch(`${$config.JAVA_SERVICE}${uri}`, {
            method: 'POST',
            headers: headers || $network.header_java(),
            // body: data instanceof FormData ? this.buildFormData(data) : JSON.stringify(data || {})
            body: data instanceof FormData ? data : JSON.stringify(data || {})
        }).then(res => res.json()).then(res => {
            $utils.hideLoading();
            $network.callback(success, res);
        }).catch(res => {
            $utils.hideLoading();
            $network.callback(fail, res)
        });
    },
    post_java_2(uri, data, success, fail, headers) {
        $utils.showLoading('loading……');
        fetch(`${$config.JAVA_SERVICE}${uri}`, {
            method: 'POST',
            headers: headers || $network.header_java_2(),
            body: data,
        }).then(res => res.json()).then(res => {
            $utils.hideLoading();
            $network.callback(success, res);
        }).catch(res => {
            $utils.hideLoading();
            $network.callback(fail, res)
        });
    },
    post_java_3(uri, data, success, fail, headers) {
        fetch(`${$config.JAVA_SERVICE}${uri}`, {
            method: 'POST',
            headers: headers || $network.header_java(),
            // body: data instanceof FormData ? this.buildFormData(data) : JSON.stringify(data || {})
            body: data instanceof FormData ? data : JSON.stringify(data || {})
        }).then(res => res.json()).then(res => {
            $network.callback(success, res);
        }).catch(res => {
            $network.callback(fail, res)
        });
    },
    /**
     * 构建form data参数
     * @param formData
     * @returns {string}
     */
    buildFormData(formData) {
        let result = [];
        for (const [key, value] of formData.entries()) {
            result.push(encodeURIComponent(key) + '=' + encodeURIComponent(value));
        }
        return result.join('&');
    },
    /**
     * header - java
     * @returns {{Authorization: string, "Content-Type": string}}
     */
    header_java() {
        return {
            'Content-Type': 'application/json',
            'Authorization': $utils.getAuthorization()
        }
    },
    header_java_2() {
        return {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': $utils.getAuthorization()
        }
    },
    header_java_formData() {
        return {
            'Authorization': $utils.getAuthorization()
        }
    },
    /**
     * 函数回调
     * @param _callback
     * @param res
     */
    callback(_callback, res) {
        typeof _callback == 'function' && _callback(res);
    }


}

export default $network;