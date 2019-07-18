package com.wanwh.api.core.result;

import com.wanwh.api.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalResultGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalResultGenerator.class);

    /**
     * normal
     * @param success
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genResult(boolean success, T data, String message) {
        GlobalResult<T> result = GlobalResult.newInstance();
        result.setSuccess(success);
        result.setData(data);
        result.setMessage(message);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generate rest result:{}", result);
        }
        return result;
    }

    /**
     * success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genSuccessResult(T data) {

        return genResult(true, data, null);
    }

    /**
     * error message
     * @param message error message
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genErrorResult(String message) {

        return genResult(false, null, message);
    }

    /**
     * error
     * @param error error enum
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genErrorResult(ErrorCode error) {

        return genErrorResult(error.getMessage());
    }

    /**
     * success no message
     * @return
     */
    public static GlobalResult genSuccessResult() {
        return genSuccessResult(null);
    }

    /**
     * success
     * @param rows
     * @param total
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genSuccessEasyUIResult(T rows, long total) {

        return genEasyUIResult(true, null, rows, total);
    }

    /**
     * normal
     * @param success
     * @param rows
     * @param total
     * @param message
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genEasyUIResult(boolean success, String message, T rows, long total) {
        GlobalResult<T> result = GlobalResult.newInstance();
        result.setSuccess(success);
        result.setMessage(message);
        result.setRows(rows);
        result.setTotal(total);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generate rest result:{}", result);
        }
        return result;
    }

    /**
     * success
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genSuccessResult(String message) {

        return genResult(true, null, message);
    }


    public static <T> GlobalResult<T> genSuccessEasyUIResultWithData(T rows, long total,T data) {
        GlobalResult<T> result = GlobalResult.newInstance();
        result.setSuccess(true);
        result.setMessage(null);
        result.setRows(rows);
        result.setTotal(total);
        result.setData(data);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generate rest result:{}", result);
        }
        return result;
    }

}
