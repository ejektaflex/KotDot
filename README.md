Godot-Kotlin
========
Kotlin language bindings for the [Godot Engine](https://godotengine.org/)'s [GDNative API](https://github.com/GodotNativeTools/godot_headers).

**WARNING:** These bindings are currently still under development and are not in a usable state.


Checklist:

 - [x] Simple shared/dynamic library creation
 - [ ] Read API file and use KotlinPoet to translate API JSON data into Kotlin classes
 - [ ] Read Core classes file API JSON and translate into Kotlin classes
 - [ ] Automatic conversion of Kotlin files into GDNS files on build

# Setup

1.Clone the repository
```bash
git clone https://github.com/ejektaflex/godot-kotlin
```
2.Build libraries
 ```bash
./gradlew build
 ```
 That will generate a shared/dynamic library that can be loaded by Godot - containing the underlying C-Interop code and the necessary
 API initialization methods.

