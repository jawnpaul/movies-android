[![Android Build](https://github.com/jawnpaul/movies-android/actions/workflows/android_build.yml/badge.svg)](https://github.com/jawnpaul/movies-android/actions/workflows/android_build.yml)

# movies-android
Repository for an android application that shows a list of movies and their details


The application consumes data from the [TMDB API](https://www.themoviedb.org/)


## Table of Contents

- [Architecture](#architecture)
- [Libraries](#libraries)
- [Process](#process)
- [Testing](#testing)
- [Extras](#extras)
- [Demo](#demo)

## Architecture

The application follows clean architecture because of the benefits it brings to software which includes scalability, maintainability and testability. It enforces separation of concerns and dependency inversion, where higher and lower level layers all depend on abstractions. In the project, the layers are separated into different gradle modules namely.

- Domain
- Data
- Cache
- Remote

These modules are Kotlin modules except the cache module. The reason being that the low level layers need to be independent of the Android framework. One of the key points of clean architecture is that low level layers should be platform agnostic. As a result, the domain and data layers can be plugged into a kotlin multiplatform project for example, and it will run just fine because we don't depend on the android framework. The cache and remote layers are implementation details that can be provided in any form (Firebase, GraphQl server, REST, SharedPreference, File system, ROOM, SQLDelight, etc) as long as it conforms to the business rules / contracts defined in the data layer which in turn also conforms to contracts defined in domain.

The project has an app module that essentially serves as the presentation layer. Right now, it currently has the movies feature that holds the UI code and presents data to the users.

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to setup. Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming, along with a fine suite of operators that make it a robust solution.

#### Domain

The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app. All other layers have their own representation of these domain models, and Mapper classes (or adapters) are used to transform the domain models to each layer's domain model representation. Usecases which represent a single unit of business logic are also defined in the domain layer, and are consumed by the presentation layer. Writing mappers and models can take a lot of effort and result in boilerplate, but they make the codebase much more maintainable and robust by separating concerns.

The UseCases use a ```BaseUseCase``` interface that defines the parameters its taking in and
output and also handles running the UseCases in a background thread leveraging Kotlin Coroutines.

#### Data

The Data layer implements the contract for providing data defined in the domain layer, and it in turn provides a contract that will be used to fetch data from the cache and remote data source. We have two data sources - ```Remote``` and ```Cache```. Remote relies on Retrofit library to fetch data from the [TMDB REST API](https://www.themoviedb.org/), while the cache layer uses Room library to persist the recent movie search and discoveries. The remote layer contains an OkHttp Interceptor that modifies api requests and add the ```api_key``` to the request as required by the [TMDB REST API](https://www.themoviedb.org/).


#### Presentation

I used the MVVM pattern for the presentation layer. The Model essentially exposes the various states the view can be in. The ViewModel handles the UI logic and provides data via Android architectural component LiveData to the view. The ViewModel talks to the domain layer with the individual use cases. The reason for using the ```Jetpack Viewmodel``` is that it survives configuration changes, and thus ensures that the view state is persisted across screen rotation.

## Libraries

Libraries used in the application are:

- [Jetpack](https://developer.android.com/jetpack)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
  and act as a channel between use cases and UI.
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - support library that allows binding of UI components in layouts to data sources, binds character details and search results to UI.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Provides an observable data holder class.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [Shimmer](https://facebook.github.io/shimmer-android/) - Shimmer provides an easy way to add a shimmer effect to views in the application.
- [Moshi](https://github.com/square/moshi) - JSON Parser, used to parse requests on the data layer for Entities and understands Kotlin non-nullable
and default parameters.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming in order
to obtain data from the network as well as the database.
- [Coil](https://coil-kt.github.io/coil/) - This was used for loading images in the application.
- [JUnit](https://junit.org/junit4/) - This was used for unit testing the repository, the use cases and the ViewModels.
- [Mockk](https://mockk.io/) This is a mocking library for Kotlin. I used it to provide test doubles during testing.
- [Truth](https://truth.dev/) - Assertions Library, provides readability as far as assertions are concerned.
- [Hilt](https://github.com/InsertKoinIO/koin) - Dependency injection plays a central role in the architectural pattern used.
For this reason I have chosen Hilt which is built on top of the battle tested DI framework - Dagger 2.
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the tmdb api with the retrofit client.

## Process

In general, any particular flow can be said to follow the steps below:
- The view sends an action to the ViewModel
- The ViewModel reaches out to the UseCase/Interactor
- The UseCase via an abstraction layer reaches out to the repository
- The repository decides where to get the data from and returns (mapped to domain representation) either a success or a failure via a Sealed Either class.
- The UseCase gets the returned value and hand it over to the ViewModel
- The ViewModel maps the returned value to the presentation object.
- Finally, the ViewModel creates a view to model the state of the view and hand it over the view leveraging DataBinding.

## Testing

Testing is done with Junit4 testing framework, and with Google Truth for making assertions. Each layer has its own tests. The remote layer makes use of Mockwebserver to test the api requests and verify that mock Json responses provided in the test resource folder are returned. The cache layer includes tests for the Room data access object (DAO), ensuring that data is saved and retrieved as expected. The presentation layer is extensively unit-tested to ensure that the viewmodel renders the correct view states.


## Extras
The project uses ktlint to enforce proper code style. Github actions handles continous integration, and runs ktlint and unit tests.

## Installation

Minimum Api Level: 21

compileSdkVersion: 31

Build System: [Gradle](https://gradle.org/)

1. Get a free TMDB API credentials at [https://www.themoviedb.org/documentation/api](https://www.themoviedb.org/documentation/api)
3. Clone the repo
   ```sh
   git clone https://github.com/jawnpaul/movies-android.git
   ```
5. Enter your keys in `local.properties`
   ```sh
   apiKey={Insert your API Key}
    ```

## Demo

Find below screenshots of the application

|<img src="https://user-images.githubusercontent.com/29982834/194877368-99b495be-941f-4337-b5a4-9817c71e22ad.png" width=200/>|<img src="https://user-images.githubusercontent.com/29982834/194877575-020013f4-4010-422a-95b4-caaf54e6dfc0.png" width=200/>|
|:----:|:----:|

|<img src="https://user-images.githubusercontent.com/29982834/194877739-85f0cdf9-c6c3-4139-b01c-cdd60dc66f47.png" width=200/>|<img src="https://user-images.githubusercontent.com/29982834/194877760-8b3dac8c-a6a8-4bcc-aed0-dfac8a3eccf5.png" width=200/>|
|:----:|:----:|


## License

MIT

**Free Software, Hell Yeah!**
