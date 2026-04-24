# League Android Code Challenge - Version 1.0

This project implements a social feed application for the League Android coding challenge. It fetches posts and user information from a remote API and displays them in a clean, modern interface.

## 🚀 Improvements & Implementation Details (v1.0)

### 1. Architecture: MVVM + Clean Architecture (Basic)
I followed the **Model-View-ViewModel (MVVM)** pattern to ensure a clear separation of concerns, making the code maintainable and testable.
- **UI Layer**: Built entirely with **Jetpack Compose**, utilizing `StateFlow` to observe data changes.
- **Domain Layer**: Contains the business logic and domain models (`Post`) that the UI consumes.
- **Data Layer**: Handles API communication via Retrofit and data mapping from DTOs to Domain models.

### 2. Tech Stack & Libraries
- **Jetpack Compose**: For building a modern, declarative UI.
- **Material 3**: Used for UI components and theming.
- **Pull to Refresh**: Implemented using Material 3 `PullToRefreshContainer` for a better user experience.
- **Coroutines & Flow**: For asynchronous programming and reactive data streams.
- **Retrofit & Gson**: For network requests and JSON parsing.
- **Coil**: For efficient image loading and caching of user avatars.
- **JUnit & Kotlinx-Coroutines-Test**: For unit testing the business logic.

### 3. Key Features Implemented
- **Pull to Refresh**: Added a pull-to-refresh mechanism to allow users to manually update the feed.
- **Parallel Data Fetching**: Optimized performance by fetching Users and Posts in parallel using `async/await` after obtaining the `api_key`.
- **Authenticated Data Fetching**: 
    1. Performs a login request to obtain an `api_key`.
    2. Uses the key as an `x-access-token` for subsequent requests.
- **Data Mapping**: Merges data from two separate endpoints into a unified domain model (`Post`) containing username and avatar URL.
- **UI States**: Robust handling of `Loading`, `Success`, and `Error` states with a "Retry" mechanism for better resilience.
- **Toolchain Resolution**: Configured the Foojay resolver to automatically manage JDK versions, ensuring build consistency across environments.

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

---

## 📝 Assumptions & Notes
- **Login Credentials**: Used the default "hello/world" credentials for the initial authentication step.
- **Manual DI**: Used manual dependency injection via a ViewModel Factory in this version.
- **Pagination**: Pagination is not implemented in this version.
