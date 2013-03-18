package example.logparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import now.gf.utilities.logparser.ILogLine;
import now.gf.utilities.logparser.ILogLineParser;

/**
 * <p>This class is an example of <code>ILogLineParser</code>. The
 * class has been conceived to be a base class that provides a <code>parse</code>
 * method facility that contains the main logic for converting a log line to
 * a <code>ILogLine</code> class.</p>
 * 
 * <p>Children classes have to define their own log line pattern as well as 
 * positions for key log entities (timestamp, thread id and so on)</p>
 * 
 * @author Giorgio Ferrara
 */
abstract class RTLogLineParser implements ILogLineParser {
    private final Pattern LOG_LINE_PATTERN;
    private final SimpleDateFormat TIMESTAMP_FORMATTER;
    private final int TIMESTAMP_PATTERN_GROUP;
    private final int LOG_LEVEL_PATTERN_GROUP;
    private final int THREAD_ID_PATTERN_GROUP;

    public RTLogLineParser() {
        this.LOG_LINE_PATTERN = getLogLinePattern();
        this.TIMESTAMP_FORMATTER = getTimestampFormatter();
        this.TIMESTAMP_PATTERN_GROUP = getTimestampPatternGroup();
        this.LOG_LEVEL_PATTERN_GROUP = getLogLevelPatternGroup();
        this.THREAD_ID_PATTERN_GROUP = getThreadIdPatternGroup();
    }

    abstract Pattern getLogLinePattern();
    
    abstract SimpleDateFormat getTimestampFormatter();
    
    abstract int getTimestampPatternGroup();
    
    abstract int getLogLevelPatternGroup();
    
    abstract int getThreadIdPatternGroup();
    
    public ILogLine parse(final String line) {
       if (line == null) return null;
       Matcher matcher = LOG_LINE_PATTERN.matcher(line);
       if (!matcher.find()) return null;
       RTLogLine rtLogLine = new RTLogLine();
       try {rtLogLine.setTimestamp(TIMESTAMP_FORMATTER.parse(matcher.group(TIMESTAMP_PATTERN_GROUP)));} catch (ParseException ex) {}
       rtLogLine.setLogLevel(matcher.group(LOG_LEVEL_PATTERN_GROUP));
       String threadRef = matcher.group(THREAD_ID_PATTERN_GROUP);
       rtLogLine.setThreadId(threadRef);
       rtLogLine.setMessage(line.substring(matcher.end()).trim());
       return rtLogLine;
   }
}
