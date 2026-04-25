# Unit Testing Strategy - Version 1.0 (Legacy Monolith)

This document outlines the unit testing approach for the original version 1.0 of the application. Unlike the modularized V3.1, this version uses a simpler monolithic structure with a single `Repository` and helper functions.

## 🛠 Testing Stack
- **JUnit 4**: The core testing framework.
- **Mockito-Kotlin**: For mocking the Retrofit `Api` interface and other dependencies.
- **Kotlinx-Coroutines-Test**: For testing suspending functions within the Repository and ViewModel using `runTest` and `UnconfinedTestDispatcher`.
- **MockedStatic**: Specifically used to mock `android.util.Base64` during repository login tests.

---

## 📊 Coverage Areas

### 1. Presentation Layer (`life.league.challenge.kotlin.ui`)
- **`FeedViewModelTest`**: 
    - Verifies the state transition from `Loading` to `Success` when data is fetched.
    - Validates error state propagation when the repository fails.
    - Uses a `FakeRepository` to isolate ViewModel logic from network concerns.

### 2. Data Layer (`life.league.challenge.kotlin.api`)
- **`RepositoryTest`**:
    - Tests the sequence of calls: `login()` -> `getUsers()` + `getPosts()` -> `mapToDomain()`.
    - Mocks the `android.util.Base64` class to allow unit testing of the Basic Auth encoding logic in a non-Android environment.
    - Ensures that a missing API key during login results in a thrown exception.

### 3. Logic & Helpers (`life.league.challenge.kotlin.model`)
- **`MappingTest`**:
    - Deeply validates the `mapToDomain` logic.
    - Ensures "orphan" posts (posts without a corresponding user) are filtered out correctly.
    - Verifies that the joining logic correctly associates user avatars and usernames with their respective posts.

---

## 🚀 How to Run Tests

Execute the following command in the terminal:

```bash
./gradlew :kotlin_app:testDebugUnitTest
```

---

## 🚫 Excluded from Unit Testing

The following areas are intentionally excluded from unit testing for the reasons specified:

### 1. Data Transfer Objects (DTOs)
- **Classes**: `UserDto`, `PostDto`, `Account`.
- **Reason**: These are simple `data class` containers with no logic. Testing them only verifies the Kotlin compiler and adds no value to project stability.

### 2. Retrofit API Interface (`Api.kt`)
- **Reason**: The `Api` interface is a standard Retrofit definition. Its behavior is verified through the `RepositoryTest` by mocking the interface and ensuring the repository consumes its outputs correctly.

### 3. Service Configuration (`Service.kt`)
- **Reason**: `Service.kt` is a singleton object used for Retrofit initialization. It contains static configuration (URLs and Converter Factories). Testing this would require verifying that Retrofit is correctly configured, which is an infrastructure concern better handled by integration or end-to-end tests.

### 4. UI Components (Composables)
- **Classes**: `FeedScreen`, `PostItem`.
- **Reason**: Jetpack Compose UI components are excluded from unit tests as they require `createComposeRule` and are better suited for Instrumented Tests (in the `androidTest` folder) or Screenshot testing.
