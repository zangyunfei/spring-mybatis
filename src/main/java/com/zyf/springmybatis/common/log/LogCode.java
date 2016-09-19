package com.zyf.springmybatis.common.log;

/**
 * 定义是否发短信或邮件的code
 *
 * @author liyanxin
 *         错误码5000~6000为雨峰需要关注的错误
 */

public enum LogCode {
    INFO_NO_ACTION(1000),
    INFO_WITH_MAIL(1001),
    INFO_WITH_MSG(1002),
    WARN_NO_ACTION(2000),
    WARN_WITH_MAIL(2001),
    WARN_WITH_MSG(2002),
    ERROR_NO_ACTION(3000),
    ERROR_WITH_MAIL(3001),

    ERROR_WITH_MAIL_SEND_CANAL_MGMT(5001),
    //青岛银行发送服务费给canal系统出现异常发送此邮件给产品（雨峰）

    ERROR_WITH_MSG(3002);

    private int code;

    private LogCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getCodeStr() {
        return Integer.toString(this.code);
    }
}