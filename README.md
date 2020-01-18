# Kotlin Coroutines

Some examples of how [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) can be used on Android in an app built using MVVM architecture.
The examples that illustrate error handling randomly throw an Exception that gets caught and displayed by a `Snackbar`.

## Branches

* [RxJava](https://github.com/jshvarts/Coroutines101): Load Github repos using Retrofit
* [Coroutines using `CoroutineScope`](https://github.com/jshvarts/Coroutines101/tree/coroutine-scope): Load Github repos using `CoroutineScope` and Retrofit
* [Coroutines using `viewModelScope`](https://github.com/jshvarts/Coroutines101/tree/viewModelScope): Load Github repos using `viewModelScope` and Retrofit
* [Coroutines](https://github.com/jshvarts/Coroutines101/tree/images-sync): Load two images over the network synchronously
* [Coroutines using `async`/`await`](https://github.com/jshvarts/Coroutines101/tree/images-async): Load two images over the network **asynchronously**
* [Coroutines using `CoroutineExceptionHandler`](https://github.com/jshvarts/Coroutines101/tree/images-sync-handle-exceptions): Load two images over the network synchronously and handle exceptions using custom `CoroutineExceptionHandler`
* [Coroutines using `try`/`catch`](https://github.com/jshvarts/Coroutines101/tree/images-sync-try-catch): Load two images synchronously over the network and handle exceptions using `try`/`catch`
* [Coroutines using `async` and `try`/`catch`](https://github.com/jshvarts/Coroutines101/tree/images-async-try-catch): Load two images **asynchronously** over the network and handle exceptions using `try`/`catch` if any of the images fail to load.
First image to fail will cancel its parent job which will cancel the rest of its children (in this case, the other image loading).

## Contributions

If you have any comments or would like to contribute additional examples, feel free to open an issue or submit a pull request.

 