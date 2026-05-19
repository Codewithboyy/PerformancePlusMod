# 🚀 SIMPLE MOBILE SETUP (No Gradle Wrapper Needed)

If you're getting "gradlew not found" errors, use this alternative method!

## Method 1: Install Gradle Directly (RECOMMENDED FOR MOBILE)

### On Termux (Android):
```bash
# Install Gradle
pkg install gradle

# Navigate to your project
cd /storage/emulated/0/Download/PerformancePlusMod

# Build directly with gradle command
gradle build
```

### On iSH (iOS):
```bash
# Install Gradle
apk add gradle

# Navigate to project
cd ~/PerformancePlusMod

# Build
gradle build
```

## Method 2: Generate Gradle Wrapper Files

If you want to use `./gradlew`, you need to generate the wrapper files first:

```bash
# First install gradle (see Method 1)
pkg install gradle  # or: apk add gradle

# Go to project directory
cd PerformancePlusMod

# Generate wrapper files
gradle wrapper

# Now you can use gradlew
chmod +x gradlew
./gradlew build
```

## Method 3: Cloud Build (Easiest!)

### Using GitHub (Free):
1. Create account at https://github.com
2. Click "New Repository"
3. Upload your PerformancePlusMod folder
4. Go to "Actions" tab
5. Click "Set up a workflow yourself"
6. Paste this:

```yaml
name: Build Mod
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Grant execute permission
        run: chmod +x gradlew
      - name: Build with Gradle
        run: ./gradlew build
      - name: Upload artifact
        uses: actions/upload-artifact@v3
        with:
          name: mod-jar
          path: build/libs/*.jar
```

7. Commit and push
8. Download built jar from Actions tab!

## Troubleshooting

### "gradle: command not found"
```bash
# Make sure you installed it:
pkg install gradle    # Termux
# or
apk add gradle        # iSH
```

### "Permission denied"
```bash
chmod +x gradlew
```

### "JAVA_HOME not set"
```bash
# Set Java home
export JAVA_HOME=/data/data/com.termux/files/usr/lib/jvm/java-17-openjdk
```

### Out of Memory
```bash
# Add to gradle.properties:
echo "org.gradle.jvmargs=-Xmx1024m" >> gradle.properties
```

## Quick Commands Cheat Sheet

```bash
# Using installed gradle (EASIEST)
gradle build

# Using gradlew (if wrapper exists)
./gradlew build

# Clean before building
gradle clean build

# Build without tests (faster)
gradle build -x test

# Check gradle version
gradle --version
```

## What Each Method Produces

All methods produce: `build/libs/performanceplus-1.0.0.jar`

This is your mod file! Copy it to `.minecraft/mods/` on your PC.

## Still Having Issues?

### Quick Fix - Build on PC Instead:
1. Transfer the PerformancePlusMod folder to your PC via:
   - USB cable
   - Google Drive / Dropbox
   - Email
2. On PC, open terminal in the folder
3. Run: `./gradlew build` (Mac/Linux) or `gradlew.bat build` (Windows)
4. Copy the .jar from `build/libs/` to your mods folder

### Use Online IDE:
- **Replit.com** - Upload files, click "Run"
- **Gitpod.io** - Connect GitHub repo, auto-builds
- **GitHub Codespaces** - Best for serious development

---

**Remember**: Building on mobile is possible but challenging. Don't get discouraged! The cloud/PC options work great too. 💪
