# Unit Testing Strategy - Version 2.0 (Hilt & Clean Architecture)

This document outlines the unit testing approach for Version 2.0. This version introduces Hilt for Dependency Injection and follows Clean Architecture principles, requiring more isolated testing strategies.

## 🛠 Testing Stack
- **JUnit 4**: The core testing framework.
- **Mockito & Mockito-Kotlin**: For mocking dependencies and verifying interactions.
- **Kotlinx-Coroutines-Test**: For testing suspending functions and handling `CoroutineDispatcher` in tests.
- **MockedStatic**: Used to mock static Android platform methods like `android.util.Log` and `android.util.Base64`.

---

## 📊 Coverage Areas

### 1. Presentation Layer (`FeedViewModelTest`)
- **State Management**: Verifies transitions between `Loading`, `Success`, and `Error` states.
- **UseCase Interaction**: Ensures the `GetPostsUseCase` is called correctly.
- **Reactive Updates**: Validates that state changes are emitted correctly via `StateFlow`.

### 2. Domain Layer (`GetPostsUseCaseTest`)
- **Business Logic**: Verifies that the UseCase correctly orchestrates the call to the repository with hardcoded/default credentials.

### 3. Data Layer (`RepositoryTest`)
- **Network Flow**: Tests the sequence of `login` -> `getUsers` -> `getPosts`.
- **Error Propagation**: Ensures network or authentication failures are converted to appropriate exceptions.
- **Log Validation**: Verified repository logging behavior using `MockedStatic`.

### 4. Persistence & Logic (`DatabaseMappingTest`, `MappingTest`)
- **Data Integrity**: Ensures accurate conversion between `UserDto`/`PostDto`, `PostEntity` (DB), and `Post` (Domain).
- **Relational Logic**: Verifies that posts and users are correctly joined based on `userId`.

---

## 🚀 How to Run Tests

```bash
./gradlew :kotlin_app:testDebugUnitTest
```

---

## 🚫 Excluded from Unit Testing

The following areas are intentionally excluded from unit testing for the reasons specified:

### 1. Simple Data Models / Entities
- **Classes**: `Account`, `Post`, `PostDto`, `UserDto`, `PostEntity`.
- **Reason**: These are standard Kotlin `data class`es used for data transport and storage. They contain no logic. Testing them only verifies the compiler and adds no value.

### 2. Hilt Dependency Injection Modules (`di` package)
- **Reason**: Hilt modules are configuration files. Testing them would involve verifying that a specific class is provided by a specific function, which is better handled by Hilt's own compile-time checks or through integration tests.

### 3. Room Database & DAOs (`db` package)
- **Classes**: `AppDatabase`, `PostDao`.
- **Reason**: Testing DAOs requires an Android environment (SQLite). These should be tested using **Instrumented Tests** in the `androidTest` folder (using `In-Memory` database) rather than JVM unit tests to ensure correct SQL execution and Room behavior.

### 4. Retrofit API Definitions (`api` package)
- **Classes**: `Api`.
- **Reason**: The interface only defines metadata for Retrofit. The actual network communication and parsing logic are infrastructure concerns. Mocking the interface in `RepositoryTest` is sufficient to verify the app's handling of network data.

### 5. UI Components & Application Class
- **Classes**: `LeagueApplication`, `FeedScreen`, `PostItem`.
- **Reason**: These are Android Framework or Compose components. They require an emulator/device context and are better suited for Instrumented/UI tests or Screenshot tests.
