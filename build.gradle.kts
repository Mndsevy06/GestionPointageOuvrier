plugins {
    id("com.android.application")  version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}

// Pour éviter le warning “Script plugin should return 'Unit'”
true
