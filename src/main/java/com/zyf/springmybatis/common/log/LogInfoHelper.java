package com.zyf.springmybatis.common.log;

/**
 * @author zangyunfei
 */
public class LogInfoHelper {

    private static final String SELF = LogInfoHelper.class.getName();
    private static final int DEFAULT_MILLIS = 0;

    /**
     * 找到通过LogInfoHelper打印日志的方法
     *
     * @return
     */
    private static String extractMethodName() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        boolean found = false;
        for (StackTraceElement element : stack) {
            final String className = element.getClassName();
            if (found) {
                if (!LogInfoHelper.SELF.equals(className)) {
                    return className + '.' + element.getMethodName();
                }
            } else {
                found = LogInfoHelper.SELF.equals(className);
            }
        }
        return "-";
    }

    public static void info(String msg) {
        LogInfo.newBuilder().withCode(LogCode.INFO_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void infoWithMail(String msg) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();

    }

    public static void infoWithMsg(String msg) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();

    }

    public static void info(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.INFO_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void infoWithMail(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void infoWithMsg(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    /**
     * 使用时formatter中大括号对数与可变参数的个数必须相同，否则报错
     *
     * @param formatter
     * @param args
     */
    public static void infoWithFormatter(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.INFO_NO_ACTION.getCode())
            .withMsgFormatter(formatter, args).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void infoWithMail(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MAIL.getCode())
            .withMsgFormatter(formatter, args).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void infoWithMsg(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.INFO_WITH_MSG.getCode())
            .withMsgFormatter(formatter, args).withSuccess('Y')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().info();
    }

    public static void warn(String msg) {
        LogInfo.newBuilder().withCode(LogCode.WARN_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMail(String msg) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMsg(String msg) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warn(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.WARN_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMail(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMsg(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    /**
     * 使用时formatter中大括号对数与可变参数的个数必须相同，否则报错
     *
     * @param formatter
     * @param args
     */
    public static void warnWithFormatter(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.WARN_NO_ACTION.getCode())
            .withMsgFormatter(formatter, args).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMail(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MAIL.getCode())
            .withMsgFormatter(formatter, args).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void warnWithMsg(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.WARN_WITH_MSG.getCode())
            .withMsgFormatter(formatter, args).withSuccess('-')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().warn();
    }

    public static void error(String msg) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('N').withMillis(0)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMail(String msg) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('N').withMillis(0)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMsg(String msg) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('N').withMillis(0)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void error(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_NO_ACTION.getCode())
            .withMessage(msg).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMail(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MAIL.getCode())
            .withMessage(msg).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMsg(String msg, Throwable t) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MSG.getCode())
            .withMessage(msg).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS).withThrowable(t)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    /**
     * 使用时formatter中大括号对数与可变参数的个数必须相同，否则报错
     * 
     * @param formatter
     * @param args
     */
    public static void errorWithFormatter(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_NO_ACTION.getCode())
            .withMsgFormatter(formatter, args).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMail(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MAIL.getCode())
            .withMsgFormatter(formatter, args).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithMsg(String formatter, Object... args) {
        LogInfo.newBuilder().withCode(LogCode.ERROR_WITH_MSG.getCode())
            .withMsgFormatter(formatter, args).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }

    public static void errorWithCode(LogCode code, String formatter,
            Object... args) {
        LogInfo.newBuilder().withCode(code.getCode())
            .withMsgFormatter(formatter, args).withSuccess('N')
            .withMillis(LogInfoHelper.DEFAULT_MILLIS)
            .withMethodName(LogInfoHelper.extractMethodName()).build().error();
    }
}
