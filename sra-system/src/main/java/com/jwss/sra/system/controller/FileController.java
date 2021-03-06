package com.jwss.sra.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.jwss.sra.common.model.ApiResult;
import com.jwss.sra.config.properties.FileUploadProperties;
import com.jwss.sra.framework.constant.GlobalValue;
import com.jwss.sra.framework.constant.RedisKey;
import com.jwss.sra.framework.service.IRedisService;
import com.jwss.sra.framework.util.IpUtils;
import com.jwss.sra.system.param.file.VerificationCodeParam;
import com.jwss.sra.system.util.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author jwss
 * @date 2022-1-12 14:25:41
 */
@Api(tags = "系统文件接口")
@Validated
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileUploadProperties fileUploadProperties;
    @Resource
    private IRedisService redisService;

    @ApiOperation(value = "验证码生成")
    @PostMapping("/verificationCode")
    public ApiResult<String> verificationCode(@Valid @RequestBody VerificationCodeParam param, HttpServletRequest request){
        // 生成圆圈干扰的验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        redisService.save(
                String.format(RedisKey.VERIFY_CODE, param.getCodeType(), IpUtils.getIp(request)),
                captcha.getCode(),
                300L
        );
        return ApiResult.ok(captcha.getImageBase64());
    }

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public ApiResult<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = FileUploadUtils.saveMultipartFile(multipartFile, fileUploadProperties.getLocalUrl());
        return ApiResult.ok(GlobalValue.getServerUrl() + fileUploadProperties.getBrowserUrl() + fileName);
    }

}
