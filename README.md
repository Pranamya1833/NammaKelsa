# Namma Kelsa 🔧

> *Namma Kelsa* means "Our Work" in Kannada

A dignity-first Android labor marketplace app that connects daily wage workers with homeowners in semi-urban Bengaluru — eliminating middlemen and giving workers a digital identity.

## Problem Statement
Skilled daily wage workers like painters, plumbers and electricians have no digital presence. They rely on word-of-mouth and middlemen. Homeowners struggle to find reliable workers quickly. Namma Kelsa bridges this gap directly.

## Features
- Worker profile creation with skill, rate, location and phone
- Phone number based profile lookup and editing
- Real-time availability toggle (Available Today ON/OFF)
- Customer search with skill filter chips
- Available workers shown first in list
- One-tap Call button — direct hire, no middleman
- AI-powered multilingual search (Kannada, Hindi, English)
- Animated splash screen
- Blue and white professional UI
## Screenshots

### Splash Screen
![Splash Screen](screenshots/Splash%20screen.png)

### Worker Registration
![Worker Registration](screenshots/worker%20registration.png)

### AI Powered Search
![AI Powered Search](screenshots/ai%20powered%20search.png)

### AI Powered Search Result
![AI Powered Search 2](screenshots/ai%20powered%20search%202.png)

### Availability Toggle
![Availability Toggle](screenshots/Availability%20toggle.png)

### One Tap Call
![One Tap Call](screenshots/one%20tap%20call.png)

### One Tap Call Dialer
![One Tap Call 2](screenshots/one%20tap%20call%202.png)

## Tech Stack
Kotlin, Android SDK, Firebase Firestore, Mistral AI API, OkHttp3, Kotlin Coroutines, XML Layouts, Android Studio

## Project Structure
app/src/main/java/com/example/nammakelsa/
├── MainActivity.kt
├── WorkerActivity.kt
├── CustomerActivity.kt
├── WorkerAdapter.kt
├── Worker.kt
└── SplashActivity.kt
## Setup Instructions

### Prerequisites
- Android Studio
- Android SDK API 24+
- Firebase account
- Mistral AI API key (free at console.mistral.ai)

### Installation
1. Clone the repository
git clone https://github.com/Pranamya1833/NammaKelsa.git
2. Open in Android Studio

3. Add Firebase configuration
   - Create Firebase project at console.firebase.google.com
   - Add Android app with package: com.example.nammakelsa
   - Download google-services.json → place in app/ folder
   - Enable Firestore Database in test mode

4. Add Mistral API key
   - Get free key at console.mistral.ai
   - In CustomerActivity.kt replace:
   - your-mistral-api-key-here with your actual key

5. Run the app
   - Connect Android device or start emulator
   - Click Run in Android Studio

## Success Criteria
1. Availability switch updates search results instantly
2. Call button works with phone dialer
3. UI simple enough for any worker to manage their profile

## AI Integration
Mistral AI API detects which skill is needed from customer 
description in Kannada, Hindi or English automatically.

## MindMatrix VTU Internship Program
Project 53 — Self-Employment Category

## Future Improvements
- GPS based distance filtering for nearby workers
- Worker rating and review system
- Push notifications for job requests
- In-app chat between worker and customer
- Worker photo gallery for past work showcase
- Multi-language UI support