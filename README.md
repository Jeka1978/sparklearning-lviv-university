## Homework for [Big Data architecture course](https://cms.ucu.edu.ua/mod/assign/view.php?id=18124) in UCU

### Architecture of football package 

- `configs` package:
  - Two spark configurations depend on profile
  - `MainConfig`: loading property sources, scan components, describing spark beans
  - `UserConfig`: loading and transform data from property sources
- `formatters` package: adding additional information to original data
- `printers` package: logging data to console
- `savers` package: saving data to files
- `valdators` package: adding validation issue codes to Action
- `DataProcessorImpl` class - work with spark RDD collections
- `ActionInfo` class - data object for store one action

### How to run

0. Install [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and
[Maven](https://maven.apache.org/install.html)
1. Clone this repository
2. Open as Maven project in IntelliJ Idea, wait until loading all dependecies.
3. Run `src/main/java/com/lviv/football/Main.java` class
4. You will see unsatisfied bean exception. To fix it - set [environment variable](http://stackoverflow.com/questions/13748784/setting-up-and-using-environmental-variables-in-intellij-idea) to `spring.profiles.active=DEV`
5. All results in console and file