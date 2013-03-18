log-splitter
============
log-splitter is a Java 1.7 utility that allows you to split a log file with several threads traces into several files each one containing one thread trace.
This is especially useful when you need to isolate one thread work and to follow it from start to end.

How it works? Very easily.

* Firstly you need to get a log file. (Quite obvious, isn't it? After all we want to split a log file...)
* Ok. Then you need to describe the log line structure. For instance, if your log file lines appear as the following:
  `2013-03-07 11:46:22,099 INFO  [STDOUT] (ajp-0.0.0.0-8009-2191) Hibernate: select * from table`
  you can describe it as `timestamp` `log level` `[other info]` `(thread id)` `message`
* Now build a class that implements the `ILogParser` interface. That class will have the responsibility to parse the log line described before
* Finally link the class that implements the `ILogParser` interface to the log splitter. This is accomplished by editing the file `META-INF\services\now.gf.utilities.logparser.ILogLineParser`: open it and simply write your parser class.
* And now... break up your log file! How? Just run the Java class `LogSplitter` passing the full log file filename (i.e. with path) as a parameter.

The project already comes with an example. Take a look at the `example.logparser` package for more details.

Hope this help.
