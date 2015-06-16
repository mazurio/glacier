# Glacier [![Release](https://img.shields.io/github/release/mazurio/glacier.svg?label=maven)](https://jitpack.io/#mazurio/glacier)

Glacier is a simple, in file, object persistence layer and cache. Currently work in progress.

## Usage

In your Application.onCreate():

```java
Glacier.init(getApplicationContext());
```

And then anywhere in the application:

```java
String result = Glacier.getOrElse("cacheKey", String.class, Duration.FIVE_MINUTES, new Glacier.Callback<String>() {
    @Override
    public String onCacheNotFound() {
        return "An amazing string.";
    }
});
```

Or make it even simpler with lambda:

```java
String result = Glacier.getOrElse("key", String.class, Duration.FIVE_MINUTES, () -> "An amazing string.");
```

Storing complicated objects such as lists:

```java
ArrayList<String> stringArrayList = new ArrayList<>();

stringArrayList.add("One");
stringArrayList.add("Two");

Glacier.put("list", stringArrayList);

ArrayList<String> fromCache = new ArrayList<>();

fromCache = Glacier.get("list", fromCache.getClass());
```

Let's make it even better with RxJava:

```java
 Glacier.getObservable("cacheKey", String.class)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(s -> {
            mTextView.setText("Title: " + s); 
        });
```

## Download

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

```
compile 'com.github.mazurio.glacier:glacier:0.0.3'
```
