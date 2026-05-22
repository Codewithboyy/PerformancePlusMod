# PerformancePlus

PerformancePlus is a Fabric optimization mod focused on increasing FPS, reducing lag spikes, improving chunk performance, and optimizing rendering without changing player settings or reducing visual quality aggressively.

Unlike simple FPS mods, PerformancePlus dynamically optimizes:
- Hidden underground chunk rebuilds
- Entity rendering
- Particle rendering
- Visibility updates
- Chunk scheduling

The goal is smoother gameplay with better frametimes while keeping Minecraft visually stable.

---

# Features

## Adaptive Visibility Optimization
Reduces unnecessary rendering work for areas the player cannot see.

## Underground Chunk Optimization
Hidden underground chunks rebuild less frequently to reduce lag spikes.

## Entity Frustum Culling
Far entities outside the camera view stop rendering.

## Particle Distance Culling
Very distant particles are skipped to improve FPS.

## Chunk Rebuild Optimization
Non-visible chunks are deprioritized to stabilize rendering performance.

## Shader Friendly
Does not modify:
- resolution scaling
- player FOV
- camera movement
- render scaling tricks

Designed to work better with:
- Sodium
- Iris
- Lithium
- Shader packs
- Resource packs

---

# Requirements

- Minecraft Fabric
- Fabric Language Kotlin
- Fabric API
- Java 21

---

# Supported Version

- Minecraft 1.21.1-1.21.11

---

# Installation

1. Install Fabric Loader
2. Install Fabric API
3. Place `PerformancePlus.jar` into your mods folder
4. Launch Minecraft

---

# Commands

## `/perfstats`
Shows optimization systems currently active.

## `/performanceplus`
Checks if the mod is loaded correctly.

---

# Optimization Philosophy

PerformancePlus avoids:
- fake FPS tricks
- forced resolution scaling
- changing player graphics settings automatically
- camera manipulation

Instead it focuses on:
- render pipeline optimization
- visibility culling
- chunk optimization
- reducing unnecessary updates

---

# Planned Features

- Advanced occlusion culling
- Async chunk rebuild scheduling
- Shader frametime stabilizer
- Dynamic entity update throttling
- Smart render budgeting
- GPU-aware optimization system

---

# Compatibility

Recommended with:
- Sodium
- Lithium
- FerriteCore
- Iris Shaders

---

# License

MIT License

---

# Developer

Created by Codewithboyy
