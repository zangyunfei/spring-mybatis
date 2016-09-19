package com.zyf.springmybatis.common.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MultiformatMessage;

/**
 * Created by xinxingegeya on 15/12/24.
 */
@Plugin(name = "LogInfoPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "log", "info", "loginfo" })
public final class LogInfoPatternConverter extends LogEventPatternConverter {

    private final String[] formats;

    private final Configuration config;

    /**
     * Private constructor.
     */
    private LogInfoPatternConverter(final Configuration config,
            final String[] options) {
        super("LogInfo", "loginfo");
        this.formats = options;
        this.config = config;
    }

    /**
     * Obtains an instance of pattern converter.
     *
     * @param config
     * @param options
     * @return instance of pattern converter.
     */
    public static LogInfoPatternConverter newInstance(
            final Configuration config, final String[] options) {
        return new LogInfoPatternConverter(config, options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        final Message msg = event.getMessage();
        if (msg != null) {
            String result;
            if (msg instanceof MultiformatMessage) {
                result = ((MultiformatMessage) msg)
                    .getFormattedMessage(this.formats);
            } else {
                result = msg.getFormattedMessage();
            }

            String appendTo;
            if (result != null) {
                appendTo = this.config != null && result.contains("${") ? this.config
                    .getStrSubstitutor().replace(event, result) : result;

                if (appendTo != null) {
                    //method^|client_ip^|result^|code^|cost^|other_log
                    String[] str = StringUtils.split(appendTo, "^|");
                    if (str.length != 6) {
                        StringBuilder sb = new StringBuilder();
                        appendTo = LogInfo
                            .newBuilder()
                            .withMethodName(
                                sb.append(event.getSource().getClassName())
                                    .append('.')
                                    .append(event.getSource().getMethodName())
                                    .toString()).withMessage(appendTo)
                            .withThrowable(event.getThrown()).build()
                            .toString();
                    }
                } else {
                    appendTo = "null";
                }
            } else {
                appendTo = "null";
            }
            toAppendTo.append(appendTo);
        }
    }
}
