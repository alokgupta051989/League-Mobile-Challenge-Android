# Unit Testing Strategy

This document outlines the unit testing approach, coverage, and tools used in the project.

## 🛠 Testing Stack
- **JUnit 4**: The core testing framework.
- **Mockito & Mockito-Kotlin**: For mocking dependencies and verifying interactions.
- **Kotlinx-Coroutines-Test**: For testing suspending functions and handling `CoroutineDispatcher` in tests.
- **MockedStatic**: Used to mock static Android platform methods like `android.util.Log` and `android.util.Base64` in local unit tests.

---

## 📊 Coverage Areas

The test suite is designed to cover the critical paths of the application, aiming for high reliability across all architectural layers.

### 1. Presentation Layer (`FeedViewModelTest`)
- **Initial State**: Verifies the UI starts in a `Loading` state.
- **Success Flow**: Ensures that when data is fetched successfully, the state transitions to `Success` with the correct data.
- **Error Flow**: Verifies that exceptions are caught and mapped to an `Error` state with a user-friendly message.
- **Refresh Logic**: Validates that the `isRefreshing` state is managed correctly during pull-to-refresh actions.

### 2. Domain Layer (`GetPostsUseCaseTest`)
- **Orchestration**: Ensures the UseCase correctly calls the repository with the expected parameters and returns the results.

### 3. Data Layer (`RepositoryTest`)
- **Network Flow**: Tests the sequential calls to `login`, `getUsers`, and `getPosts`.
- **Validation**: Verifies that missing API keys or failed logins trigger the appropriate exceptions.
- **Static Dependencies**: Handles `Base64` encoding and `Log` statements through static mocking.

### 4. Logic & Mappers (`MappingTest`, `DatabaseMappingTest`)
- **Data Transformation**: Verifies the complex "join" logic between Posts and Users.
- **Filtering**: Ensures that posts without a valid author are filtered out.
- **Integrity**: Validates that fields (id, username, title, etc.) are correctly mapped between API DTOs, Domain models, and Database entities.

---

## 🚀 How to Run Tests

### Via Terminal
Run the following command in the project root:
```bash
./gradlew :kotlin_app:testDebugUnitTest
```

### Via Android Studio
1. Right-click the `src/test/java` folder.
2. Select **"Run 'Tests in 'life.league...'"**.
3. View the results in the **Run** tool window.

---

## 💡 Key Testing Decisions

### Handling Android Platform Dependencies
Unit tests run on the JVM, which doesn't include Android platform code. To avoid `Method ... not mocked` errors:
1. We used `unitTests.returnDefaultValues = true` in `build.gradle` for basic safety.
2. For critical logic (like `Base64` or `Log`), we used `Mockito.mockStatic` to provide controlled behavior during tests.

### Coroutines Handling
We use `UnconfinedTestDispatcher` in ViewModels to ensure that state changes triggered within `viewModelScope.launch` happen immediately within the test thread, making assertions reliable without manual delays.
