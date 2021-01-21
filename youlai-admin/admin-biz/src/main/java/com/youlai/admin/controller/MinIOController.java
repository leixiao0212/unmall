package com.youlai.admin.controller;


import cn.hutool.core.util.IdUtil;
import com.youlai.admin.service.impl.MinIOService;
import com.youlai.common.core.result.Result;
import com.youlai.common.web.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/files")
@Slf4j
@AllArgsConstructor
public class MinIOController {

    private MinIOService minIOService;

    @PostMapping
    @ApiOperation(value = "文件上传", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "bucket_name", value = "桶名称", paramType = "query", dataType = "string")
    })
    public Result<String> upload(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "bucket_name", required = false, defaultValue = "default") String bucketName
    ) {
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String objectName = IdUtil.simpleUUID() + "." + suffix;
            String path = minIOService.putObject(bucketName, objectName, file.getInputStream());
            return Result.success(path);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @DeleteMapping
    @ApiOperation(value = "文件删除", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "文件路径", required = true, paramType = "query"),
    })
    public Result removeFile(@RequestParam String path) {
        try {
            int lastIndex = path.lastIndexOf("/");
            String bucketName = path.substring(path.lastIndexOf("/", lastIndex - 1) + 1, lastIndex);
            String objectName = path.substring(lastIndex + 1);
            minIOService.removeObject(bucketName, objectName);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(e.getLocalizedMessage());
        }
    }

}
