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

## Download

TODO
