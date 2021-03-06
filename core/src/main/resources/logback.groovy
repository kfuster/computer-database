import ch.qos.logback.classic.filter.LevelFilter

import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY

def bySecond = timestamp("yyyyMMdd'T'HHmmss")

appender("FILE", FileAppender) {
  file = "logs/log-${bySecond}.log"
  filter(LevelFilter) {
        level = DEBUG
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

logger("org.hibernate.type", ALL)
logger("org.hibernate", DEBUG)
logger("org.springframework", ALL)
logger("com.zaxxer.hikari", ALL)
root(INFO, ["CONSOLE", "FILE"])