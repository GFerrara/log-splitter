package net.giorgioferrara.utilities.logparser;

import java.util.Date;

/**
 * <p>This interface describes beans that contain basic log line 
 * structure.</p>
 *
 * @author Giorgio Ferrara
 */
public interface ILogLine {
    /**
     * <p>Returns the log line timestamp.</p>
     * 
     * @return the timestamp
     */
    public Date getTimestamp();
    
    /**
     * <p>Returns the log line log level.</p>
     * 
     * @return the log level
     */
    public String getLogLevel();
    
    /**
     * <p>Returns the log line thread id.</p>
     * 
     * @return the thread id
     */
    public String getThreadId();
    
    /**
     * <p>Returns the log line message.</p>
     * 
     * @return the log message
     */
    public String getMessage();
}
