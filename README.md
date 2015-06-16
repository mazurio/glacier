# Glacier

Glacier is a simple, in file, object persistence layer and cache. Currently work in progress.

## Usage

In your Application.onCreate().

```java
Glacier.init(getApplicationContext());
```

And then anywhere in the application

```java
String result = Glacier.getOrElse("cacheKey", String.class, Duration.FIVE_MINUTES, new Glacier.Callback<String>() {
    @Override
    public String onCacheNotFound() {
        return "An amazing string.";
    }
});
```

Or make it even simpler with lambda

```java
String result = Glacier.getOrElse("key", String.class, Duration.FIVE_MINUTES, () -> "An amazing string.");
```

Storing lists

```java
ArrayList<String> stringArrayList = new ArrayList<>();

stringArrayList.add("One");
stringArrayList.add("Two");

Glacier.put("list", stringArrayList);

ArrayList<String> fromCache = new ArrayList<>();

fromCache = Glacier.get("list", fromCache.getClass());
```

## Roadmap
1. Synchronous and Asynchronous get and getOrElse.
2. Networking examples (Retrofit).
3. Example application that uses Glacier.
4. RxJava integration.

## Download

Will be available on Maven Central later on, currently work in progress.
