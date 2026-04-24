# League Android Code Challenge

This project implements a social feed application for the League Android coding challenge. It fetches posts and user information from a remote API and displays them in a clean, modern interface.

## 🚀 Improvements & Implementation Details

### 1. Architecture: MVVM + Clean Architecture
I followed the **Model-View-ViewModel (MVVM)** pattern to ensure a clear separation of concerns, making the code maintainable and testable.
- **UI Layer**: Built entirely with **Jetpack Compose**, utilizing `StateFlow` to observe data changes.
- **Domain Layer**: Contains the business logic and domain models (`Post`) that the UI consumes.
- **Data Layer**: Handles API communication via Retrofit and data mapping from DTOs to Domain models.

### 2. Tech Stack & Libraries
- **Jetpack Compose**: For building a modern, declarative UI.
- **Material 3**: For UI components and theming.
- **Coroutines & Flow**: For asynchronous programming and reactive data streams.
- **Retrofit & Gson**: For network requests and JSON parsing.
- **Coil**: For efficient image loading and caching of user avatars.
- **JUnit & Kotlinx-Coroutines-Test**: For unit testing the business logic.

### 3. Key Features Implemented
- **Authenticated Data Fetching**: 
    1. Performs a login request to obtain an `api_key`.
    2. Uses the key as an `x-access-token` to fetch Users and Posts in parallel using `async/await`.
- **Data Mapping**: Merges data from two different endpoints (Users and Posts) into a single list of `Post` objects containing the username and avatar URL.
- **UI States**: Robust handling of `Loading`, `Success`, and `Error` states, including a "Retry" mechanism for network failures.
- **Toolchain Resolution**: Configured the Foojay resolver to automatically manage JDK versions for the build environment.

### 4. Unit Testing
I included unit tests for the `FeedViewModel` to demonstrate state management testing:
- Verifies initial `Loading` state.
- Verifies `Success` state when data is fetched correctly.
- Verifies `Error` state when the repository throws an exception.
- Uses a `FakeRepository` for predictable and fast test execution.

---

## 🛠 Build Instructions
1. Open the project in the latest version of **Android Studio**.
2. The project uses **Gradle Toolchains**, so it will automatically detect or download the required JDK.
3. Sync the project with Gradle files.
4. Run the `:kotlin_app` module on an emulator or physical device (API 26+).

## 🧪 Running Tests
To run the unit tests, execute the following command in the terminal:
```bash
./gradlew :kotlin_app:testDebugUnitTest
```
Or right-click the `FeedViewModelTest` file in Android Studio and select **Run**.

---

## 📝 Assumptions & Notes
- **Login Credentials**: Used the default "hello/world" credentials for the initial authentication step as per common challenge requirements.
- **Manual DI**: For a project of this scale, I used manual dependency injection (passing the repository to the ViewModel via a Factory) to keep the project lightweight without the overhead of Hilt or Koin.
- **Error Handling**: Basic error handling is implemented to show a message and a retry button if the API call fails or the login is invalid.
