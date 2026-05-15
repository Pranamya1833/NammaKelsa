# Namma Kelsa 🔧
### *Find. Hire. Work.*

> *Namma Kelsa* means "Our Work" in Kannada

A dignity-first Android labor marketplace app that connects 
daily wage workers with homeowners in semi-urban Bengaluru — 
eliminating middlemen and giving workers a professional 
digital identity for the first time.

---

## Problem Statement

Skilled daily wage workers like painters, plumbers and 
electricians have no digital presence. They rely entirely 
on word-of-mouth and middlemen who take commissions and 
control access to jobs. Homeowners struggle to find 
reliable workers quickly when they need them.

**Namma Kelsa solves this** by creating a direct digital 
bridge between workers and homeowners — no middleman, 
no commission, just direct hire.

---

## Features

- Worker profile creation with skill, rate, location and phone
- Phone number based profile lookup and editing
- Real-time availability toggle — Available Today ON/OFF
- Customer search with skill filter chips
- Available workers shown at the top of the list
- One-tap Call button — direct hire, zero middleman
- AI-powered multilingual search in Kannada, Hindi, English
- Animated splash screen
- Clean blue and white professional UI

---

## Screenshots

### Splash Screen
![Splash Screen](screenshots/Splash%20screen.png)

### Worker Registration
![Worker Registration](screenshots/worker%20registration.png)

### AI Powered Search Result
![AI Powered Search 2](screenshots/ai%20powered%20search%202.png)

### Availability Toggle
![Availability Toggle](screenshots/Availability%20toggle.png)

### One Tap Call
![One Tap Call](screenshots/one%20tap%20call.png)

### One Tap Call Dialer
![One Tap Call 2](screenshots/one%20tap%20call%202.png)

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Android SDK | Mobile app framework |
| Firebase Firestore | Real-time cloud database |
| Mistral AI API | Multilingual skill detection |
| OkHttp3 | HTTP client for API calls |
| Kotlin Coroutines | Async operations |
| XML Layouts | UI design |
| Android Studio | Development environment |

---

## Project Structure

**Kotlin Source Files**
- `SplashActivity.kt` — Animated splash screen
- `MainActivity.kt` — Home screen, Worker/Customer selection
- `WorkerActivity.kt` — Worker profile create and edit
- `CustomerActivity.kt` — Customer search and AI feature
- `WorkerAdapter.kt` — RecyclerView adapter for worker cards
- `Worker.kt` — Worker data class

**Resource Files**
- `res/layout/` — XML UI layouts for all screens
- `res/drawable/` — Icons and background drawables
- `res/values/` — Colors, strings, themes and arrays
---

## Setup Instructions

### Prerequisites
- Android Studio
- Android SDK API 24+
- Firebase account
- Mistral AI API key (free at console.mistral.ai)

### Installation

**1. Clone the repository**
git clone https://github.com/Pranamya1833/NammaKelsa.git

**2. Open in Android Studio**

**3. Configure Firebase**
- Create a Firebase project at console.firebase.google.com
- Add Android app with package: com.example.nammakelsa
- Download google-services.json and place in app/ folder
- Enable Firestore Database in test mode
- Region: asia-south1

**4. Add Mistral API Key**
- Get free API key at console.mistral.ai
- Open CustomerActivity.kt
- Replace: `your-mistral-api-key-here`
- With your actual Mistral API key

**5. Run the app**
- Connect Android device or start emulator (Pixel 6 recommended)
- Click Run in Android Studio

---

## AI Integration

Namma Kelsa integrates **Mistral AI API** for multilingual 
skill detection. Customers can describe their problem in 
any Indian language:

- Kannada: ನನ್ನ ಮನೆಯ ನಲ್ಲಿ ಸೋರುತ್ತಿದೆ → Plumber
- Hindi: दीवार पेंट करनी है → Painter  
- English: My garden needs cleaning → Gardener

The AI automatically detects the required skill and filters 
the worker list instantly.

---

## Success Criteria Met

| Criteria | Implementation | Status |
|---|---|---|
| Availability switch updates search instantly | Firestore with available workers sorted first | ✅ Complete |
| Call button works with phone dialer | Android Intent ACTION_DIAL | ✅ Complete |
| UI simple enough for any worker | Phone number lookup, large buttons | ✅ Complete |

---

## Future Improvements

- GPS based distance filtering for nearby workers
- Worker rating and review system
- Push notifications for job requests
- In-app chat between worker and customer
- Worker photo gallery for past work showcase
- Multi-language UI throughout the app

---

## MindMatrix VTU Internship Program
**Project 53 — Self-Employment Category**

Built as part of the MindMatrix VTU Internship Program, 
Namma Kelsa addresses the real-world problem of digital 
exclusion faced by daily wage workers in semi-urban India.