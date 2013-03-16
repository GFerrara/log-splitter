package example.logparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.giorgioferrara.utilities.logparser.ILogLine;
import net.giorgioferrara.utilities.logparser.ILogLineParser;

/**
 * This class is an example of <code>ILogLineParser</code>.
 * 
 * @author Giorgio Ferrara
 */
public class RTLogLineParser implements ILogLineParser{
    private static final Pattern LOG_LINE_PATTERN = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\,\\d{3})\\s+(\\w+)\\s+\\[[^\\]]+\\]\\s+\\(([^\\)]+)\\)");
    private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    @Override
    public ILogLine parse(final String line) {
        if (line == null) return null;
        Matcher matcher = LOG_LINE_PATTERN.matcher(line);
        if (!matcher.find()) return null;
        RTLogLine rtLogLine = new RTLogLine();
        try {rtLogLine.setTimestamp(TIMESTAMP_FORMATTER.parse(matcher.group(1)));} catch (ParseException ex) {}
        rtLogLine.setLogLevel(matcher.group(2));
        String threadRef = matcher.group(3);
        rtLogLine.setThreadId(threadRef);
        rtLogLine.setMessage(line.substring(matcher.end()).trim());
        return rtLogLine;
    }
    
    public static void main (String[] args) {
        ILogLineParser logLineParser = new RTLogLineParser();
        logLineParser.parse("2013-03-07 11:46:22,099 INFO  [STDOUT] (ajp-0.0.0.0-8009-2191) Hibernate: select * from table");
    }
}
