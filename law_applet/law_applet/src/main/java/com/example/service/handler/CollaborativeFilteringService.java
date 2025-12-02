package com.example.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollaborativeFilteringService {

    @Autowired
    private RatingService ratingService;

    // 获取推荐的律师ID列表
    public List<Integer> getRecommendedLawyers(Integer targetUserId, long pageSize, long pageNum) {
        // 获取用户-律师评分矩阵
        Map<Integer, Map<Integer, Integer>> ratingMatrix = ratingService.getUserLawyerRatingMatrix();

        // 如果没有评分数据，直接返回空列表
        if (ratingMatrix == null || ratingMatrix.isEmpty()) {
            return Collections.emptyList();
        }

        // 获取所有律师ID
        List<Integer> allLawyerIds = ratingMatrix.values().stream()
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .collect(Collectors.toList());

        // 如果没有律师数据，直接返回空列表
        if (allLawyerIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 计算每个律师的预测评分
        Map<Integer, Double> lawyerScores = new HashMap<>();
        for (Integer lawyerId : allLawyerIds) {
            double predictedRating = predictRating(targetUserId, lawyerId, ratingMatrix);
            lawyerScores.put(lawyerId, predictedRating);
        }

        // 按评分排序并分页
        return lawyerScores.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // 按评分降序
                .skip((pageNum - 1) * pageSize) // 修正括号，并确保 long 计算正确
                .limit(pageSize) // 限制每页大小
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // 预测用户对律师的评分
    private double predictRating(Integer targetUserId, Integer targetLawyerId, Map<Integer, Map<Integer, Integer>> ratingMatrix) {
        double sumSimilarity = 0.0;
        double weightedSum = 0.0;

        Map<Integer, Integer> targetUserRatings = ratingMatrix.getOrDefault(targetUserId, new HashMap<>());

        for (Integer userId : ratingMatrix.keySet()) {
            if (userId.equals(targetUserId)) {
                continue; // 跳过目标用户
            }

            Map<Integer, Integer> userRatings = ratingMatrix.get(userId);
            if (userRatings == null || !userRatings.containsKey(targetLawyerId)) {
                continue; // 跳过未评价目标律师的用户
            }

            double similarity = cosineSimilarity(targetUserRatings, userRatings);
            sumSimilarity += similarity;
            weightedSum += similarity * userRatings.get(targetLawyerId);
        }

        if (sumSimilarity == 0.0) {
            return 0.0; // 如果没有相似用户，返回0
        }

        return weightedSum / sumSimilarity;
    }

    // 计算余弦相似度
    private double cosineSimilarity(Map<Integer, Integer> user1Ratings, Map<Integer, Integer> user2Ratings) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Integer lawyerId : user1Ratings.keySet()) {
            if (user2Ratings.containsKey(lawyerId)) {
                dotProduct += user1Ratings.get(lawyerId) * user2Ratings.get(lawyerId);
            }
            norm1 += Math.pow(user1Ratings.get(lawyerId), 2);
        }

        for (Integer lawyerId : user2Ratings.keySet()) {
            norm2 += Math.pow(user2Ratings.get(lawyerId), 2);
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}