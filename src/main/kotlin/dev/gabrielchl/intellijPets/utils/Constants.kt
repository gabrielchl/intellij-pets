package dev.gabrielchl.intellijPets.utils

object Constants {
    val PET_TYPES = arrayListOf(
        "axolotl",
        "bunny",
        "cat-1",
        "cat-2",
        "cat-3",
        "cat-4",
        "cat-5",
        "chicken",
        "dog-1",
        "dog-2",
        "dog-3",
        "dog-4",
        "dog-5",
        "hedgehog"
    )

    val PET_TO_SPRITE_SIZE: Map<String, Int> = java.util.Map.ofEntries(
        java.util.Map.entry("axolotl", 32),
        java.util.Map.entry("bunny", 40),
        java.util.Map.entry("cat-1", 40),
        java.util.Map.entry("cat-2", 40),
        java.util.Map.entry("cat-3", 40),
        java.util.Map.entry("cat-4", 40),
        java.util.Map.entry("cat-5", 40),
        java.util.Map.entry("chicken", 40),
        java.util.Map.entry("dog-1", 32),
        java.util.Map.entry("dog-2", 32),
        java.util.Map.entry("dog-3", 32),
        java.util.Map.entry("dog-4", 32),
        java.util.Map.entry("dog-5", 32),
        java.util.Map.entry("hedgehog", 32)
    )
}