package example.logparser;

import java.util.Date;
import now.gf.utilities.logparser.ILogLine;

/**
 * This bean is an example of a <code>ILogLine<code>.
 * 
 * @author Giorgio Ferrara
 */
public class RTLogLine implements ILogLine {
    private Date timestamp;
    private String logLevel;
    private String threadId;
    private String message;
    
    
    // --- Setter methods ---

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setLogLevel(final String logLevel) {
        this.logLevel = logLevel;
    }

    public void setThreadId(final String threadId) {
        this.threadId = threadId;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
    
    
    // --- Getter methods ---
    
    @Override
    public Date getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getLogLevel() {
        return this.logLevel;
    }

    @Override
    public String getThreadId() {
        return this.threadId;
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}
