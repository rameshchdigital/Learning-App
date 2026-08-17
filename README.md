# 🌟 Little English Buddy - Android App

**Learn English. Play. Speak. Grow!** 🧸🔤🔢🎨

An interactive, cheerful, and engaging English learning app for children and toddlers built with **Kotlin** and **Jetpack Compose** (Material 3).

---

## 📱 Final APK Details

| Attribute | Details |
| :--- | :--- |
| **App Name** | **Little English Buddy** |
| **Final APK File Name** | **`LittleEnglishBuddy-v1.0.apk`** |
| **Package / Application ID** | `com.aistudio.babyworld.toddler` |
| **Target Android Version** | Android 7.0+ (API Level 24 to 36) |
| **Version** | `1.0` (VersionCode: `1`) |

---

## 📥 How to Download the APK

### 1. From GitHub Releases
1. Navigate to the **[Releases](../../releases)** tab of this GitHub repository.
2. Open the latest release (e.g. `v1.0-build-X`).
3. Under **Assets**, click **`LittleEnglishBuddy-v1.0.apk`** to download directly to your Android device or PC.
4. On your Android device, tap the downloaded `.apk` and grant permission to install unknown apps if prompted.

### 2. From GitHub Actions Artifacts
1. Go to the **Actions** tab.
2. Select the latest workflow run on the `main` branch.
3. Scroll to the **Artifacts** section and download **`LittleEnglishBuddy-v1.0-apk`**.

---

## ✨ Features

- 🔤 **Alphabet & Phonics**: Interactive letter cards (A–Z) with phonics sounds, example words, and visual vocabulary.
- 🔢 **Numbers & Counting**: Numbers 1–20, counting patterns, number bonds, and audio counting drills.
- 🎨 **Colors & Mixing**: Color swatches, secondary colors, rainbow spectrum, and interactive color mixing laboratory.
- 📐 **Shapes & Geometry**: Circle, square, triangle, star, and common everyday objects.
- 🐾 **Animal Friends**: Farm animals, jungle safaris, ocean wonders, and authentic animal sounds.
- 🗣️ **Interactive Speaking & Ear Training**: Spoken pronunciations with Android Text-To-Speech and auditory listening challenges.
- ⭐ **Star Reward System**: Encouraging badges, confetti, and celebratory audio cues.
- 🛡️ **Kid-Safe & Offline-Ready**: Child-friendly interface with large touch targets and intuitive navigation.

---

## 🛠️ How to Build Locally

### Prerequisites
- Android Studio Ladybug (or newer) / JDK 17+
- Android SDK Platform 36

### Build Commands
```bash
# Build Debug APK
gradle :app:assembleDebug

# The APK will be generated at:
# app/build/outputs/apk/debug/app-debug.apk
```
