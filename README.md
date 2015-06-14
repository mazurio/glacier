# Glacier

Glacier is a simple, in file, object persistence layer and cache. Currently work in progress.

## Usage

In your Application.onCreate().

```
Glacier.init(getApplicationContext());
```

And then anywhere in the application

```
String result = Glacier.getOrElse("cacheKey", String.class, Duration.FIVE_MINUTES, new Glacier.Callback<String>() {
    @Override
    public String onCacheNotFound() {
        return "An amazing string.";
    }
});
```

Or make it even simpler with lambda

```
String result = Glacier.getOrElse("key", String.class, Duration.FIVE_MINUTES, () -> "An amazing string.");
```

Storing lists

```
ArrayList<String> stringArrayList = new ArrayList<>();

stringArrayList.add("One");
stringArrayList.add("Two");

Glacier.put("list", stringArrayList);

ArrayList<String> fromCache = new ArrayList<>();

fromCache = Glacier.get("list", fromCache.getClass());
```

## Download

TODO
