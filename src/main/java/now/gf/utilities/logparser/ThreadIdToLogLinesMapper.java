package now.gf.utilities.logparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class maps thread ids to log lines.</p>
 * 
 * @author Giorgio Ferrara
 */
class ThreadIdToLogLinesMapper {
    private Map<String, List<Integer>> threadToLinesMap;
    
    public ThreadIdToLogLinesMapper() {
        this.threadToLinesMap = new HashMap<>();
    }
    
    public synchronized void addLineForThread(final String threadId, final Integer lineNo) {
        if (threadToLinesMap.containsKey(threadId)) {
            threadToLinesMap.get(threadId).add(lineNo);
        } else {
            final ArrayList<Integer> lines = new ArrayList<>(); 
            lines.add(lineNo);
            threadToLinesMap.put(threadId, lines);
        }
    }
    
    public synchronized Map<String, List<Integer>> getThreadToLinesMap() {
        return Collections.unmodifiableMap(threadToLinesMap);
    }
}
