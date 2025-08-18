# CleanArchitecturePlayground
## Overview
CleanArchitecturePlayground is an Android application demonstrating the implementation of Clean Architecture principles in mobile development.<br>
The app's core structure—comprising Presentation, Domain, and Data layers—is largely forked from my earlier 2023 repository, AndroidPlaygroundDemo.
The primary updates include migrating the original XML-based UI to Jetpack Compose and adopting the latest modern Android app development practices. 
This project showcases the advantages of migrating older libraries or architectures in a straightforward and effective manner, making it an excellent example for such transitions.

## Project Structure
The app follows Clean Architecture principles, dividing the codebase into distinct layers:

### App layer
- Built with Jetpack Compose for modern, declarative UI, replacing XML layouts.

### Presentation Layer  
- Utilizes Jetpack components like StateFlow for reactive UI updates.  
- Rendering data and handling user interactions.
- Change livedata to StateFlow

### Domain Layer  
- The core of the app, containing business logic and use cases.  

### Data Layer  
- Manages data sources like Room for local persistence or APIs (e.g., via Retrofit). 

## Features
Modular Architecture: Demonstrates separation of concerns with Clean Architecture.  
Kotlin-Based: Written in Kotlin, leveraging modern Android development practices.  
Dependency Injection: Uses a dependency injection framework (e.g., Hilt or Dagger) to manage dependencies across layers.  
Unit Testing: Includes examples of unit tests for use cases and ViewModels to ensure code reliability.  
Sample Use Cases: Implements basic use cases (e.g., data retrieval, user interactions) to showcase the architecture.

### Dependencies
The project uses the following libraries (indicative, based on common Clean Architecture setups for Android):

### Jetpack Libraries:  
- Hilt ViewModel and StateFlow for UI state management.  
- Room for local database persistence.  
- Compose navigation for in-app navigation.

### Dependency Injection:  
- Hilt for managing dependencies.

### Networking (if applicable):  
- Retrofit or OkHttp for API calls.
