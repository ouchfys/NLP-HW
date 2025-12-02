package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.Order;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.CommentService;
import com.example.service.OrderService;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.util.CustomAssert;
import com.example.util.UserContext;
import com.example.vo.CommentVO;
import com.example.vo.UserVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private String key = "token_####";
    @Resource
    RoleService roleService;
    @Resource
    CommentService commentService;

    @Lazy
    @Resource
    OrderService orderService;

    @Override
    public String login(User user) {

        LambdaQueryWrapper<User> last = Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPassword, user.getPassword())
                .eq(User::getRoleId, user.getRoleId())
                .last("limit 1");
        UserVO userAndRole = getUserAndRole(last);
        CustomAssert.isNotEmpty(userAndRole, "账号或密码错误");
        HashMap<String, Object> pyload = new HashMap<>();
        pyload.put("id",userAndRole.getId());
        pyload.put("account",userAndRole.getAccount());
        pyload.put("roleId",userAndRole.getRoleId());
        pyload.put("roleName",userAndRole.getRoleName());
        String token = JWTUtil.createToken(pyload, key.getBytes());
        return token;
    }

    @Override
    public Boolean register(User user) {
        User byAccount = getByAccount(user.getAccount(), user.getRoleId());
        CustomAssert.isNull(byAccount, "该账号已存在");
        if (user.getRoleId() == 2 && ObjectUtils.isEmpty(user.getConsultPrice())){
            CustomAssert.alert("请填写律师咨询价格");
        }
        return save(user);
    }

//    @Override
//    public List<UserVO> userPage(IPage<UserVO> page, User user) {
//        List<UserVO> userVOS = baseMapper.userPage(page, user);
//        if (user.getRoleId() !=null && user.getRoleId() == 2){
//            for (UserVO userVO : userVOS) {
//                List<CommentVO> byLawId = commentService.getByLawId(userVO.getId());
//                userVO.setComments(byLawId);
//            }
//        }
//
//        return userVOS;
//    }

    @Override
    public List<UserVO> userPage(IPage<UserVO> page, User user) {
        List<UserVO> userVOS = baseMapper.userPage(page, user);

        // 获取当前用户 ID
        Integer currUserId = UserContext.getCurrUser().getId();

        // 仅在查询律师 (role_id = 2) 时应用推荐算法
        if (user.getRoleId() != null && user.getRoleId() == 2) {
            Map<Integer, Double> lawyerScores = new HashMap<>();

            for (UserVO userVO : userVOS) {
                // 获取律师的评论数据
                List<CommentVO> comments = commentService.getByLawId(userVO.getId());
                userVO.setComments(comments);

                // 计算推荐分数
                double score = calculateRecommendationScore(userVO.getId(), currUserId);
                lawyerScores.put(userVO.getId(), score);
            }

            // 按推荐分数降序排序
            userVOS.sort((a, b) -> Double.compare(lawyerScores.getOrDefault(b.getId(), 0.0),
                    lawyerScores.getOrDefault(a.getId(), 0.0)));
        }

        return userVOS;
    }


    /**
     * 计算用户对律师的推荐分数
     * @param lawyerId 律师ID
     * @param userId 当前用户ID
     * @return 推荐分数
     */
    private double calculateRecommendationScore(Integer lawyerId, Integer userId) {
        // 获取当前用户的订单
        List<Order> userOrders = orderService.getOrdersByUserId(userId);

        // 获取该律师的所有订单
        List<Order> lawyerOrders = orderService.getOrdersByLawyerId(lawyerId);

        // 计算订单相似度
        double orderSimilarity = calculateOrderSimilarity(userOrders, lawyerOrders);

        // 获取该律师的所有评论
        List<CommentVO> lawyerComments = commentService.getByLawId(lawyerId);

        // 计算评论评分的平均值
        double commentScore = lawyerComments.stream()
                .mapToInt(CommentVO::getStar)
                .average()
                .orElse(0.0);

        // 最终推荐分数 (订单相似度 60% + 评论评分 40%)
        return orderSimilarity * 0.6 + commentScore * 0.4;
    }


    /**
     * 计算用户与律师的订单相似度
     * @param userOrders 当前用户的订单
     * @param lawyerOrders 该律师的订单
     * @return 订单相似度 (0~1)
     */
    private double calculateOrderSimilarity(List<Order> userOrders, List<Order> lawyerOrders) {
        if (userOrders.isEmpty() || lawyerOrders.isEmpty()) {
            return 0.1; // 若无订单，给默认低相似度
        }

        Set<Integer> userOrderSet = userOrders.stream().map(Order::getId).collect(Collectors.toSet());
        Set<Integer> lawyerOrderSet = lawyerOrders.stream().map(Order::getId).collect(Collectors.toSet());

        // 计算交集和并集
        Set<Integer> intersection = new HashSet<>(userOrderSet);
        intersection.retainAll(lawyerOrderSet);

        Set<Integer> union = new HashSet<>(userOrderSet);
        union.addAll(lawyerOrderSet);

        return (double) intersection.size() / union.size();
    }


    private User getByAccount(String account,int role){
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account).eq(User::getRoleId, role));
    }

    public UserVO getUserAndRole(LambdaQueryWrapper<User> wrappers) {
        User one = this.getOne(wrappers);
        if (one == null){
            return null;
        }
        Role byId = roleService.getById(one.getRoleId());
        UserVO userVO = BeanUtil.copyProperties(one, UserVO.class);
        userVO.setRoleName(byId.getRoleName());
        return userVO;
    }
}
