package example.logparser;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import now.gf.utilities.logparser.ILogLineParser;

/**
 * <p>This class is an example of <code>ILogLineParser</code>.
 * It recognizes a log line defined as: <code>[timestamp] threadid loglevel 
 * message</code></p>
 * 
 * @author Giorgio Ferrara
 */
public class RTCollLogLineParser extends RTLogLineParser {
    @Override
    Pattern getLogLinePattern() {
        return Pattern.compile("^\\[(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\,\\d{3})\\]\\s+([^\\s]+)\\s+(\\w+)\\s+-\\s+");
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
        return 3;
    }

    @Override
    int getThreadIdPatternGroup() {
        return 2;
    }
    
    public static void main (String[] args) {
        ILogLineParser logLineParser = new RTCollLogLineParser();
        logLineParser.parse("[2013-03-15 16:18:32,396] ajp-0.0.0.0-8109-7 DEBUG - data");
    }
}
