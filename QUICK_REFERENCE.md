# Performance Plus - Quick Reference Card

## 📋 Essential Commands

### Building
```bash
./gradlew build              # Build the mod
./gradlew clean build        # Clean and build
./gradlew build -x test      # Build without tests (faster)
```

### In-Game
```
/perfstats                   # Show performance statistics (requires OP)
```

## 📁 Key Files to Modify

| File | Purpose | Common Changes |
|------|---------|----------------|
| `PerformancePlus.java` | Main mod initialization | Add new features |
| `MemoryOptimizer.java` | Memory management | Adjust GC intervals |
| `ChunkOptimizer.java` | Chunk optimization | Change monitoring thresholds |
| `FPSOptimizer.java` | FPS tracking | Adjust FPS warning levels |
| `PerformanceCommand.java` | Commands | Add new commands |
| `fabric.mod.json` | Mod metadata | Update version, description |
| `gradle.properties` | Build settings | Change Minecraft version |

## 🎯 Common Customizations

### Change Memory Check Interval
**File**: `MemoryOptimizer.java` (line 10)
```java
private static final int GC_INTERVAL = 6000; // 6000 ticks = 5 minutes
```

### Change Memory Threshold
**File**: `MemoryOptimizer.java` (line 34)
```java
if (memoryUsagePercent > 75) { // Trigger GC at 75% usage
```

### Change Low FPS Warning
**File**: `FPSOptimizer.java` (line 34)
```java
if (averageFPS < 30) { // Warn when FPS drops below 30
```

### Change Chunk Log Frequency
**File**: `ChunkOptimizer.java` (line 23)
```java
if (totalChunksLoaded % 1000 == 0) { // Log every 1000 chunks
```

## 🔧 Adding New Features

### 1. Create New Optimizer
```java
// Create: src/main/java/com/performanceplus/YourOptimizer.java
package com.performanceplus;

public class YourOptimizer {
    public static void init() {
        // Your optimization code
    }
}
```

### 2. Register in Main Class
```java
// In PerformancePlus.java, onInitialize() method:
YourOptimizer.init();
```

### 3. Add to Command (Optional)
```java
// In PerformanceCommand.java, executeStats() method:
source.sendFeedback(() -> Text.literal("§e" + YourOptimizer.getStats()), false);
```

## 📊 Performance Metrics Explained

### Memory Stats
- **Used**: Currently allocated memory
- **Max**: Maximum available memory
- **Free**: Available memory

### Chunk Stats
- **Loaded**: Total chunks loaded since server start
- **Unloaded**: Total chunks unloaded
- **Active**: Currently loaded chunks (Loaded - Unloaded)

### FPS Stats
- **Average FPS**: Rolling average over last 100 frames

## 🐛 Troubleshooting Quick Fixes

| Problem | Solution |
|---------|----------|
| Build fails | `./gradlew clean` then rebuild |
| Mod doesn't load | Check Fabric API is installed |
| Commands don't work | Ensure you have OP permissions |
| No performance improvement | Check logs for errors |
| OutOfMemoryError during build | Add to gradle.properties: `org.gradle.jvmargs=-Xmx2048m` |

## 📱 Mobile Development Shortcuts

### Termux (Android)
```bash
pkg install openjdk-17        # Install Java
pkg install micro             # Install text editor
micro filename.java           # Edit file
./gradlew build              # Build mod
```

### File Transfer
```bash
# Copy built jar to accessible location
cp build/libs/performanceplus-1.0.0.jar ~/storage/downloads/
```

## 🎓 Code Patterns

### Adding Event Listener
```java
ServerTickEvents.END_SERVER_TICK.register(server -> {
    // Your code here
});
```

### Adding Client Event
```java
ClientTickEvents.END_CLIENT_TICK.register(client -> {
    // Your code here
});
```

### Logging
```java
PerformancePlus.LOGGER.info("Info message");
PerformancePlus.LOGGER.warn("Warning message");
PerformancePlus.LOGGER.error("Error message");
```

### Sending Feedback to Player
```java
source.sendFeedback(() -> Text.literal("§aSuccess!"), false);
source.sendFeedback(() -> Text.literal("§eWarning!"), false);
source.sendFeedback(() -> Text.literal("§cError!"), false);
```

## 🌈 Color Codes for Messages
```
§0 = Black       §8 = Dark Gray
§1 = Dark Blue   §9 = Blue
§2 = Dark Green  §a = Green
§3 = Dark Aqua   §b = Aqua
§4 = Dark Red    §c = Red
§5 = Dark Purple §d = Light Purple
§6 = Gold        §e = Yellow
§7 = Gray        §f = White
```

## 🚀 Version Update Checklist

1. ✅ Update version in `gradle.properties`
2. ✅ Update changelog in `README.md`
3. ✅ Test all features
4. ✅ Build: `./gradlew clean build`
5. ✅ Verify jar exists in `build/libs/`
6. ✅ Tag release in git (if using)

## 📚 Useful Resources

- **Fabric Wiki**: https://fabricmc.net/wiki/
- **Fabric API Docs**: https://maven.fabricmc.net/docs/
- **Yarn Mappings**: https://fabricmc.net/wiki/tutorial:mappings
- **Fabric Discord**: https://discord.gg/v6v4pMv

---

**Pro Tip**: Keep this file open while coding for quick reference! 📌
