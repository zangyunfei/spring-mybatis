package com.zyf.springmybatis.common.log;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.zyf.springmybatis.common.NetUtils;

/**
 * level ^| time ^| project ^| method ^| client_ip ^| result ^| code ^| cost ^|
 * other_log
 * Created by liyanxin on 15/12/7.
 */
public class LogInfo implements Serializable { // NOPMD

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9082944904431751514L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInfo.class);
    private static final String SELF = LogInfo.class.getName();
    private static final char UNKNOWN_MESSAGE = '-';
    /**
     * client_ip^|method^|Y/N^|code^|cost^|
     */
    protected static final String LOG_TEMPLATE = "{}^|{}^|{}^|{}^|{}^|";
    protected static final String LOG_MESSAGE_TEMPLATE = "{}";

    /**
     * 日志的数据部分
     */
    private Class<?> loggerClazz;
    private char success;
    private int code;
    private long millis;
    private String formatter;
    private Object[] args;
    private String methodName;
    private Throwable throwable;
    private String message;

    public static LogInfoBuilder newBuilder() {
        return new LogInfo.LogInfoBuilder();
    }

    public LogInfo() {
    }

    public LogInfo(LogInfoBuilder builder) {
        this.loggerClazz = builder.loggerClazz;
        this.success = builder.success;
        this.code = builder.code;
        this.millis = builder.millis;
        this.formatter = builder.formatter;
        this.args = builder.args;
        this.methodName = builder.methodName;
        this.throwable = builder.throwable;
        this.message = builder.message;
    }

    public Class<?> getLoggerClazz() {
        return this.loggerClazz;
    }

    public char getSuccess() {
        return this.success;
    }

    public int getCode() {
        return this.code;
    }

    public long getMillis() {
        return this.millis;
    }

    public String getFormatter() {
        return this.formatter;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    /**
     * 严重错误（1/6）
     * 非常严重的错误，导致系统中止。
     */
    public void error() {
        this.log(LogLevel.ERROR);
    }

    /**
     * 警告（2/6）
     */
    public void warn() {
        this.log(LogLevel.WARN);
    }

    /**
     * 一般信息（3/6）
     * 运行时产生的有意义的事件。
     */
    public void info() {
        this.log(LogLevel.INFO);
    }

    /**
     * 调试信息（4/6）
     * 系统流程中的细节信息，一般正常运行时不被打印。
     */
    public void debug() {
        this.log(LogLevel.DEBUG);
    }

    /**
     * 更细节的调试信息（5/6）
     * 比debug更加细节的信息，一般正常运行时不被打印。
     */
    public void trace() {
        this.log(LogLevel.TRACE);
    }

    /**
     * 输出日志
     *
     * @param level
     */
    private void log(LogLevel level) {
        String format = this.extractLogFormat();
        Object[] arrays = this.extractLogArgs(this);
        Logger usedLogger;
        if (this.getLoggerClazz() == null) {
            usedLogger = LogInfo.LOGGER;
        } else {
            usedLogger = LoggerFactory.getLogger(this.getLoggerClazz());
        }

        switch (level) {
            case ERROR:
                usedLogger.error(format, arrays);
                break;
            case WARN:
                usedLogger.warn(format, arrays);
                break;
            case INFO:
                usedLogger.info(format, arrays);
                break;
            case DEBUG:
                usedLogger.debug(format, arrays);
                break;
            case TRACE:
                usedLogger.trace(format, arrays);
                break;
            default:
                throw new RuntimeException("the log level not support");
        }
    }

    /**
     * 提取日志模版
     *
     * @return
     */
    private String extractLogFormat() {
        String exceptionFormatter = "throw exception =>{}";
        StringBuilder temple = new StringBuilder();
        if (StringUtils.isNotBlank(this.formatter) && this.args != null
            && this.args.length != 0) {
            if (this.getThrowable() != null) {
                if (this.formatter.endsWith(",")) {
                    return temple.append(LogInfo.LOG_TEMPLATE)
                        .append(this.formatter).append(exceptionFormatter)
                        .toString();
                } else {
                    return temple.append(LogInfo.LOG_TEMPLATE)
                        .append(this.formatter).append(',')
                        .append(exceptionFormatter).toString();
                }
            } else {
                return temple.append(LogInfo.LOG_TEMPLATE)
                    .append(this.formatter).toString();
            }
        } else if (StringUtils.isNotBlank(this.message)) {
            if (this.getThrowable() != null) {
                return temple.append(LogInfo.LOG_TEMPLATE)
                    .append(LogInfo.LOG_MESSAGE_TEMPLATE).append(',')
                    .append(exceptionFormatter).toString();
            } else {
                return temple.append(LogInfo.LOG_TEMPLATE)
                    .append(LogInfo.LOG_MESSAGE_TEMPLATE).toString();
            }
        } else {
            throw new RuntimeException("log message is blank");
        }
    }

    /**
     * @param logInfo
     * @return
     */
    private Object[] extractLogArgs(LogInfo logInfo) {
        List<Object> argList = new ArrayList<>();
        if (StringUtils.isBlank(this.getMethodName())) {
            StackTraceElement[] stack = new Throwable().getStackTrace();
            boolean found = false;
            for (StackTraceElement element : stack) {
                final String className = element.getClassName();
                if (found) {
                    if (!LogInfo.SELF.equals(className)) {
                        this.methodName = className + '.'
                            + element.getMethodName();
                        break;
                    }
                } else {
                    found = LogInfo.SELF.equals(className);
                }
            }
        }
        argList.add(logInfo.getMethodName());

        String ipAddress;
        try {
            ipAddress = NetUtils.getLocalHostLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            ipAddress = String.valueOf(LogInfo.UNKNOWN_MESSAGE);
        }
        argList.add(ipAddress);

        if (logInfo.getSuccess() == '\0') {
            argList.add(LogInfo.UNKNOWN_MESSAGE);
        } else {
            argList.add(logInfo.getSuccess());
        }
        if (logInfo.getCode() == 0) {
            argList.add(LogInfo.UNKNOWN_MESSAGE);
        } else {
            argList.add(logInfo.getCode());
        }
        if (logInfo.getMillis() == 0L) {
            argList.add(LogInfo.UNKNOWN_MESSAGE);
        } else {
            argList.add(logInfo.getMillis());
        }
        if (StringUtils.isNotBlank(logInfo.getFormatter())
            && logInfo.getArgs() != null && logInfo.getArgs().length != 0) {
            Collections.addAll(argList, logInfo.getArgs());
        } else if (StringUtils.isNotBlank(logInfo.getMessage())) {
            argList.add(logInfo.getMessage());
        } else {
            throw new RuntimeException("log parameter is null");
        }
        if (logInfo.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);) {
                pw.println();
                logInfo.getThrowable().printStackTrace(pw);
            } catch (Exception e) {
                e.printStackTrace();
            }
            argList.add(sw.toString());
        }

        return argList.toArray();
    }

    /**
     * 打印日志的message部分
     *
     * @return
     */
    @Override
    public String toString() {
        String format = this.extractLogFormat();
        Object[] arrays = this.extractLogArgs(this);
        return MessageFormatter.arrayFormat(format, arrays).getMessage();
    }

    public static class LogInfoBuilder {
        private Class<?> loggerClazz;
        private char success;
        private int code;
        private long millis;
        private String message;
        private String formatter;
        private Object[] args;
        private String methodName;
        private Throwable throwable;

        private LogInfoBuilder() {
        }

        public LogInfoBuilder withLoggerClazz(Class<?> _loggerClazz) {
            this.loggerClazz = _loggerClazz;
            return this.self();
        }

        public LogInfoBuilder withSuccess(char _success) {
            this.success = _success;
            return this.self();
        }

        public LogInfoBuilder withCode(int _code) {
            this.code = _code;
            return this.self();
        }

        public LogInfoBuilder withMillis(long _millis) {
            this.millis = _millis;
            return this.self();
        }

        /**
         * 调用该方法设置日志的名字
         *
         * @param _methodName
         * @return
         */
        public LogInfoBuilder withMethodName(String _methodName) {
            this.methodName = _methodName;
            return this.self();
        }

        public LogInfoBuilder withMessage(String _message) {
            this.message = _message;
            return this.self();
        }

        public LogInfoBuilder withMsgFormatter(String format, Object... _args) {
            // format 如果为空,不对该部分信息输出
            if (StringUtils.isNotBlank(format) && _args != null
                && _args.length != 0) {

                int count = format.length()
                    - format.replaceAll("\\{\\}", "").length();
                if (count / 2 == _args.length) {
                    this.formatter = format;
                    this.args = _args;
                }
            }
            return this.self();
        }

        public LogInfoBuilder withThrowable(Throwable _throwable) {
            this.throwable = _throwable;
            return this.self();
        }

        public LogInfo build() {
            return new LogInfo(this);
        }

        private LogInfoBuilder self() {
            return this;
        }
    }

    /**
     * 该类内部使用的日志级别
     */
    private enum LogLevel {
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE
    }
}
