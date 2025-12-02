/**
 * 构建枚举
 * @param {number | string} code
 * @param {string} msg
 */
let buildEnum = (code, msg) => {
    return {
        code, msg
    }
};

/**
 * 全局枚举对象
 */
let $enums = {
    /**
     * 用户类型
     */
    user_type_admin: buildEnum(1, '管理员'),
    user_type_doctor: buildEnum(2, '医生'),
    user_type_inhabitant: buildEnum(3, '居民'),
    /**
     * 异常枚举
     */
    unknown_parameter:buildEnum(-2,'未知参数'),
    unknown: buildEnum(-1, '未知原因'),
    instance_not_exist: buildEnum(0, '不存在的组件实例'),
    instance_remove_success: buildEnum(1, '实例成功移除'),
    get_user_info_fail: buildEnum(2, '获取用户信息失败'),
    close_modal: buildEnum(3, '成功关闭弹出框'),
    instance_exist: buildEnum(4, '已存在一个实例'),
    not_support_inquiry:buildEnum(5,'当前环境不支持音视频问诊'),
    start_inquiry_fail:buildEnum(6,'医生接诊失败'),
    stop_inquiry_fail:buildEnum(7,'医生停止问诊接口失败'),
    doctor_join_room_fail:buildEnum(8,'医生加入房间失败'),
    stop_session:buildEnum(9,'会话成功结束'),
    parameter_error:buildEnum(10,'参数错误'),
    inquiry_record_get_fail:buildEnum(11,'问诊记录获取失败'),
    measure_record_get_fail:buildEnum(12,'测量记录获取失败')

};
/**
 * 到处枚举
 */
export default $enums;