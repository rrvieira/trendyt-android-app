# Trendyt

Trendyt is a sample movie listing android app. The goal of this project is to showcase a fully working app implemented in a modern architecture - MVI.

A few quick project highlights:
- MVI Architecture
- Coroutines
- [Jetpack Compose](https://developer.android.com/jetpack/compose) UI with [Material Design 3](https://m3.material.io/) components
	- Dynamic light/dark theming support
	- Two screens:
		- Home (popular movies list)
		- Movie details
- Real world data provided by [TMDB](https://www.themoviedb.org/) (API documentation available [here](https://developers.themoviedb.org/3/getting-started/introduction))
	- Using [Retrofit 2](https://square.github.io/retrofit/) as HTTP Client
- Dependency injection
	- Supported by [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) library
- Unit and UI Tests
	- using mock data

### Try Trendyt

To try out Trendyt, after cloning this repository and opening it in Android Studio, you need to set your personal [TMDB](https://www.themoviedb.org/) API Key.

In your `local.properties` project file add:
```
tmdb.api.key=<your personal api key>
```

Notes:
- [Android Studio Chipmunk](https://developer.android.com/studio) is required
- You can register for your own personal TMDB api key [here](https://developers.themoviedb.org/3/getting-started/introduction)

### Demo

#### General

[trendyt-demo.webm](https://user-images.githubusercontent.com/3785821/188210161-e2dcb2a9-7a37-4f20-bc08-a98bf2e9fc7b.webm)

#### Dynamic Theme

[trendyt-dynamic-themes.webm](https://user-images.githubusercontent.com/3785821/188213172-2b5b62db-5222-400e-b46c-d82553bcefcd.webm)
