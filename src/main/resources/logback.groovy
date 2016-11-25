import ch.qos.logback.classic.filter.LevelFilter
import static ch.qos.logback.core.spi.FilterReply.DENY
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
def bySecond = timestamp("yyyyMMdd'T'HHmmss")

appender("FILE", FileAppender) {
  file = "logs/log-${bySecond}.txt"
  filter(LevelFilter) {
        level = ERROR
        onMatch = ACCEPT
      	onMismatch = DENY
  }
  append = true
  encoder(PatternLayoutEncoder) {
    pattern = "%level %logger - %msg%n"
  }
}
appender("CONSOLE", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(LevelFilter) {
        level = INFO
        onMatch = ACCEPT
      	onMismatch = DENY
  }
}

root(INFO, ["CONSOLE", "FILE"])