let $api = {
    userLogin: '/user/login',  //用户登录
    userInfo: '/user/info',  //获取用户信息
    userUpdate: '/user/update',  //更新用户信息
    userRemove: '/user/remove',  //删除用户信息
    imageUpload: '/upload/file', //  上传图片

    lawInfoList: '/law-information/page', //  分页查询法律信息
    lawInfoAddUpdate: '/law-information/add-update', //  添加法律信息-带id修改 
    lawInfoDelete: '/law-information/remove/', //  删除法律信息 

    userPage: '/user/page', //  用户信息分页列表 
    userRegister: '/user/register', //  律师添加-用户注册  

    orderPage: '/order/page', //  订单分页查询
    orderUpdate: '/order/add-update', //  订单创建-更新
    messagePage: '/message/page', //  分页获取订单消息
    messageSend: '/message/send-msg', //  发送消息
    messageReceive: '/message/receive-msg/', //  接收消息

    commentList: '/comment/page',
    commentUpdate: '/comment/update',
    deleteComment: '/comment/delete',

};

export default $api;