package com.jwss.sra.framework.exceptions;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.jwss.sra.common.enums.ApiResultEnum;
import com.jwss.sra.common.model.ApiResult;
import com.jwss.sra.common.model.BusinessException;
import org.sagacity.sqltoy.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常
 *
 * @author jwss
 * @date 2022-2-7 02:52:00
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常捕获
     *
     * @param e 捕获的异常
     * @return 封装的返回对象
     **/
    @ExceptionHandler(Exception.class)
    public ApiResult<String> handlerException(Exception e) {
        logger.error("GlobalExceptionHandler error :" + e.getMessage());
        logger.error(e.getClass().getName());
        if (e instanceof DataAccessException) {
            return ApiResult.error(ApiResultEnum.ERROR.getDesc(), ApiResultEnum.ERROR.getCode());
        } else if (e instanceof NotLoginException) {
            return ApiResult.error(ApiResultEnum.NOT_LOGIN.getDesc(), ApiResultEnum.NOT_LOGIN.getCode());
        } else if (e instanceof NotPermissionException) {
            return ApiResult.error(ApiResultEnum.NOT_PERMISSION.getDesc(), ApiResultEnum.NOT_PERMISSION.getCode());
        } else if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return ApiResult.error(businessException.getErrorMsg(), businessException.getErrorCode());
        } else if (e instanceof NotRoleException) {
            return ApiResult.error(ApiResultEnum.NOT_PERMISSION.getDesc(), ApiResultEnum.NOT_PERMISSION.getCode());
        }
        return ApiResult.error();
    }

}
