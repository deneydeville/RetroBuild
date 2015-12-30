# RetroBuild

Retro style of a full build & export system for jars having other jars as dependencies. 
What Retrobuild does is:

1. Compiles sources

2. Expands 3rd party jars

3. Generates result jar from classes coming both from sources and external jars

Thus we have all the control for build and export process. 
* Fully debugable
* 100% Java
* No IDE depedendance

## How to run

1. Download/clone repository
2. Go to [RetroBuild.java](https://github.com/borisf/RetroBuild/blob/master/src/com/apisolutions/retrobuild/RetroBuild.java)
3. Change to your parameters
```java
  public static final String SOURCES_FOLDER =
            "...";

    public static final String RESULT_FOLDER =
            "...";

    public static final String THIRD_PARTY_JARS =
            "...";

    public static final String MAIN_CLASS_IN_JAR =
            "...";

    public static final String JAR_NAME =
            "...";
```
