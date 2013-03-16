package net.giorgioferrara.utilities.logparser;

/**
 * <p>This interface describes classes that can parse a log line
 * phrase.</p>
 * 
 * @author Giorgio Ferrara
 */
public interface ILogLineParser {
    /**
     * <p>Given a log line, converts it into a structured <code>ILogLine
     * </code> class, or <code>null</code> if log line doesn't respect
     * the expected structure.</p>
     * 
     * @param line log line to parse
     * @return a <code>ILogLine</code> class or <code>null</code> for 
     * unrecognized log lines structures
     */
    public ILogLine parse(final String line);
}