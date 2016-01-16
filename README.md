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
2. Go to [RetroBuild.java](https://github.com/borisf/RetroBuild/blob/master/src/com/apisolutions/retrobuild/RetroBuild.java), this will be our starting point

```java
  public static void main(String[] args) throws Exception {
        RetroBuild.with(new ClassySharkBuildConfig()).build();
    }
```


3. Create your [BuildConfig](https://github.com/borisf/RetroBuild/blob/master/src/com/apisolutions/retrobuild/builds/BuildConfig.java) by implementing the methods below, and add to Retrobuild object from the previous step
```java
    public abstract String getSourcesFolder();

    public abstract String getResultFolder();

    public abstract String getThirdPartyJarsFolder();

    public abstract String getMainClassInJar();

    public abstract String getJarName();
```

