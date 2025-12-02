package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.LawInformation;
import com.example.service.LawInformationService;
import com.example.util.Condition;
import com.example.util.Query;
import com.example.util.R;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * <p>
 * 法律信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/law-information")
public class LawInformationController {

    @Resource
    private LawInformationService lawInformationService;

   //  @ApiOperation("添加法律信息/带id修改")
    @PostMapping("/add-update")
    public R add(@RequestBody LawInformation lawInformation){
        return R.ok(lawInformationService.saveOrUpdate(lawInformation));
    }

    // @ApiOperation("删除法律信息")
    @PostMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer id){
        return R.ok(lawInformationService.removeById(id));
    }

    // @ApiOperation("分页查询法律信息/可根据title查询")
    @GetMapping("/page")
    public R page(LawInformation lawInformation, Query query){
        IPage<LawInformation> page = Condition.getPage(query);
        return R.ok(lawInformationService.page(page, Wrappers.<LawInformation>lambdaQuery()
                .like(!ObjectUtils.isEmpty(lawInformation.getTitle()),LawInformation::getTitle,lawInformation.getTitle()))
        );
    }



}
