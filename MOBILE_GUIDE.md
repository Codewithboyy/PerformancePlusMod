# 📱 Mobile Development Guide for Performance Plus

This guide is specifically for developing Minecraft mods on mobile devices.

## 🚀 Quick Start on Mobile

### For Android Users (Recommended: Termux)

#### Step 1: Install Termux
1. Download [Termux](https://f-droid.org/en/packages/com.termux/) from F-Droid
2. Open Termux

#### Step 2: Set Up Development Environment
```bash
# Update packages
pkg update && pkg upgrade

# Install essential tools
pkg install openjdk-17 git wget

# Verify Java installation
java -version
```

#### Step 3: Get the Project
```bash
# Clone or download this project
cd ~
# If you have it as a zip, extract it
# Otherwise, create the project structure manually
```

#### Step 4: Build the Mod
```bash
# Navigate to project directory
cd PerformancePlusMod

# Make gradlew executable
chmod +x gradlew

# Build the mod (this may take 5-10 minutes first time)
./gradlew build
```

#### Step 5: Find Your Mod
```bash
# The compiled mod is located at:
ls build/libs/

# You should see: performanceplus-1.0.0.jar
```

### For iOS Users (iSH Shell)

#### Step 1: Install iSH
1. Download iSH from the App Store
2. Open iSH

#### Step 2: Set Up Environment
```bash
# Update package manager
apk update

# Install Java (may take a while)
apk add openjdk17

# Install essential tools
apk add git wget
```

#### Step 3-5: Follow same steps as Android above

## 🌐 Alternative: Cloud-Based Development (All Platforms)

### Option 1: GitHub Codespaces (Free for Students)
1. Create a GitHub account
2. Upload this project to a repository
3. Click "Code" → "Codespaces" → "Create codespace"
4. In the terminal, run: `./gradlew build`

### Option 2: Gitpod (Free Tier Available)
1. Create Gitpod account
2. Upload project to GitHub/GitLab
3. Prefix repo URL with `gitpod.io/#`
4. Build in the terminal

### Option 3: Replit (Easiest for Beginners)
1. Go to [Replit.com](https://replit.com)
2. Create new Repl → Select "Java"
3. Upload all project files
4. In Shell, run: `./gradlew build`

## 📝 Editing Code on Mobile

### Recommended Mobile Code Editors

#### Android:
1. **Acode** (Free, feature-rich)
   - Syntax highlighting
   - File management
   - Terminal integration

2. **Spck Editor** (JavaScript/Java support)
   - Built-in git
   - Live preview

3. **Termux** with **Micro** or **Nano**
   ```bash
   pkg install micro
   micro src/main/java/com/performanceplus/PerformancePlus.java
   ```

#### iOS:
1. **Textastic** (Paid, professional)
2. **Buffer Editor** (Free)
3. **Koder** (Good for Java)

## 🔧 Common Mobile Development Challenges

### Problem: Build Takes Forever
**Solution**: First build always takes longest (downloads dependencies)
- Subsequent builds are much faster
- Use `./gradlew build --no-daemon` on mobile to save memory

### Problem: Out of Memory
**Solution**: 
```bash
# Edit gradle.properties and add:
org.gradle.jvmargs=-Xmx1024m
```

### Problem: Can't Test the Mod
**Solution**: 
- Build the .jar on mobile
- Transfer it to your PC/laptop
- Test in Minecraft there
- Or use cloud gaming services

## 📤 Transferring Your Mod

### From Mobile to PC:

#### Method 1: Cloud Storage
1. Upload `build/libs/performanceplus-1.0.0.jar` to Google Drive/Dropbox
2. Download on PC
3. Place in `.minecraft/mods/` folder

#### Method 2: USB Cable
1. Connect phone to PC
2. Navigate to Termux/iSH files
3. Copy the .jar file
4. Place in `.minecraft/mods/` folder

#### Method 3: Email
1. Send the .jar file to yourself
2. Download on PC
3. Place in mods folder

## 💡 Tips for Mobile Mod Development

### 1. Work in Small Sessions
- Mobile keyboards can be tiring
- Use Bluetooth keyboard for longer coding sessions

### 2. Version Control
```bash
# Initialize git in your project
git init
git add .
git commit -m "Initial commit"

# Push to GitHub for backup
git remote add origin YOUR_REPO_URL
git push -u origin main
```

### 3. Use Code Snippets
- Create snippets for common patterns
- Save frequently used code blocks

### 4. Test Incrementally
- Build after small changes
- Easier to find bugs

### 5. Learning Resources
- [Fabric Wiki](https://fabricmc.net/wiki/)
- [Minecraft Modding Discord](https://discord.gg/minecraft-modding)
- YouTube tutorials (search "Fabric mod tutorial")

## 🎯 Next Steps

### Beginner Projects:
1. ✅ You've built your first performance mod!
2. Try adding a new command
3. Create a config file
4. Add custom particles

### Intermediate:
1. Create custom blocks
2. Add new items
3. Implement recipes
4. Create GUIs

### Advanced:
1. Custom world generation
2. New mob AI
3. Complex game mechanics
4. Multiplayer features

## 📚 Useful Commands

```bash
# Clean build (if something breaks)
./gradlew clean

# Build without running tests (faster)
./gradlew build -x test

# Show all gradle tasks
./gradlew tasks

# Update dependencies
./gradlew --refresh-dependencies
```

## 🆘 Getting Help

- **Fabric Discord**: Great community for questions
- **Stack Overflow**: Tag questions with [fabric-mod]
- **GitHub Issues**: Report bugs in this project
- **Reddit**: r/fabricmc

## 🎓 Learning Path

1. **Week 1**: Understand the basics (you are here!)
2. **Week 2**: Modify existing features
3. **Week 3**: Add simple new features
4. **Week 4**: Create your own mod from scratch

---

**Remember**: Every expert was once a beginner. Mobile development is challenging, but totally possible. Take breaks, ask questions, and have fun! 🎮

Happy mobile modding! 📱✨
