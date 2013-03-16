package now.gf.utilities.logparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * <p>This class performs log files splitting, i.e, given a log file
 * that file will be scanned line by line and each line will be parsed
 * to grab some basic information. Between these, the thread id.</p>
 * 
 * <p>Normally a log file traces activity performed by many threads that
 * act simultaneously, therefore it's difficult to follow each thread activity 
 * by simply reading the log straight on.</p>
 * 
 * <p>Here this class comes in help, by splitting the original log file into 
 * many log files each one containing one single thread activity.</p>
 * 
 * <p>This class doesn't impose any predefined structure to log file: 
 * simply said, it can be anything because the responsibility to parse the 
 * log line is in charge to another class (described by the interface 
 * <code>ILogLineParser</code>) that can be configured externally and that
 * can change depending on each log structure</p>
 * 
 * <p>The way the real log-line-parser class is tied together with this splitter
 * is by mean of the 
 * <code>META-INF/services/now.gf.utilities.logparser.ILogLineParser</code>
 * file: here the concrete implementation of the <code>ILogLineParser</code>
 * to use has to be defined.</p>
 * 
 * @author Giorgio Ferrara
 */
public class LogSplitter {
    private static final String NEW_LINE = System.getProperty("line.separator");
    
    /**
     * <p>This method takes a log file and split it into several different
     * smaller log files, each one containing lines pertaining to one
     * only thread.</p>
     * 
     * <p>These new files will be located under a new directory, that will
     * be created just under the directory that contains the original log file
     * and named according to this rule: <code>&lt;logfilename&gt;-splitted</code>.
     * So, for instance, if log file name is <code>"server.log"</code>, the new directory name
     * will be <code>server.log-splitted</code>.</p>
     * 
     * <p>The name assigned to each new file has the following structure: 
     * <code>&lt;thread-id&gt;.log</code></p>
     * 
     * @param log The log file to be split
     */
    public void split(final File log) {
        if (log == null || !log.isFile()) {System.err.println("Invalid log file"); return;}
        ThreadIdToLogLinesMapper threadIdToLogLinesMapper = new ThreadIdToLogLinesMapper();
        System.out.println("Working...");
        // 1. Analyze log file
        {
            ILogLineParser logLineParser = getLogLineParser();
            if (logLineParser == null) {System.err.println("Undefined or misconfigured log line parser"); return;}
            try (BufferedReader input = new BufferedReader(new FileReader(log))) {
                String line, lastThreadId = null; 
                for (int lineNo=1; (line=input.readLine())!=null; lineNo++) {
                    ILogLine logLine = logLineParser.parse(line);
                    if (logLine != null) {
                        lastThreadId = logLine.getThreadId();
                        threadIdToLogLinesMapper.addLineForThread(lastThreadId, lineNo);
                    } else if (lastThreadId != null) {
                        threadIdToLogLinesMapper.addLineForThread(lastThreadId, lineNo);
                    }
                }
            } catch (IOException e) {e.printStackTrace();}
        }
        // 2. Split it
        {
            File splittedLogDir = new File(log.getParent() + File.separator + log.getName() + "-splitted");
            if (splittedLogDir.exists()) {splittedLogDir.delete();} else {splittedLogDir.mkdir();}
            Map<String, List<Integer>> threadToLinesMap = threadIdToLogLinesMapper.getThreadToLinesMap();
            for (String threadId : threadToLinesMap.keySet()) {
                File splittedLogFile = new File(splittedLogDir ,threadId + ".log");
                try (
                    BufferedReader input = new BufferedReader(new FileReader(log));
                    FileWriter output = new FileWriter(splittedLogFile);
                ) {
                    int lineNo=1; for (int threadLineNo : threadToLinesMap.get(threadId)) {
                        for (; lineNo<threadLineNo; input.readLine(), lineNo++);
                        output.write(input.readLine() + NEW_LINE); lineNo++;
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        System.out.println("Done");
    }
    
    private ILogLineParser getLogLineParser() {
        try {
            return ServiceLoader.load(ILogLineParser.class).iterator().next();
        } catch (Throwable e) {
            return null;
        }
    }
    
    /**
     * <p>Program entry point.</p>
     * 
     * @param args fully qualified log file
     */
    public static void main (String[] args) {
        LogSplitter logSplitter = new LogSplitter();
        if (args.length < 1) {System.err.println("Please provide a log file"); return;}
        logSplitter.split(new File(args[0]));
    }
}