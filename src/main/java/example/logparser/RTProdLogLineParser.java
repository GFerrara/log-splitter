package example.logparser;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import now.gf.utilities.logparser.ILogLineParser;

/**
 * <p>This class is an example of <code>ILogLineParser</code>.
 * It recognizes a log line defined as: <code>timestamp loglevel [otherinfo]
 * (threadid) message</code></p>
 * 
 * @author Giorgio Ferrara
 */
public class RTProdLogLineParser extends RTLogLineParser {
    @Override
    Pattern getLogLinePattern() {
        return Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\,\\d{3})\\s+(\\w+)\\s+\\[[^\\]]+\\]\\s+\\(([^\\)]+)\\)");
    }

    @Override
    SimpleDateFormat getTimestampFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    }
    
    @Override
    int getTimestampPatternGroup() {
        return 1;
    }

    @Override
    int getLogLevelPatternGroup() {
        return 2;
    }

    @Override
    int getThreadIdPatternGroup() {
        return 3;
    }
    
    public static void main (String[] args) {
        ILogLineParser logLineParser = new RTProdLogLineParser();
        logLineParser.parse("2013-03-07 11:46:22,099 INFO  [STDOUT] (ajp-0.0.0.0-8009-2191) Hibernate: select * from table");
    }
}
