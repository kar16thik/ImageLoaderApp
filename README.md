Image Loader

This is the image downloading and caching library for android

It use HttpURLConnection to download images from url

To use this library use these code :

ImageLoader.with(context).load(url).into(imageView)

We have to initialise ImageLoader using with function by passing context

In load function we have have to pass image Url and in into  function we have to pass imageView

In error function we can add image resource which will display when image downloading  fail

It also had cancel method ,this will cancel all pending downloads


caching

In this library their will be 2 types of caching memory caching and disc caching

before any image download first it check available in memory cache and then check in disk cache.
if not available in cache then only download it from network

we can see is image loaded from cache or network from log under IMAGE_LOAD tag 


