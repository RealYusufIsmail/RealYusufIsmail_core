# RealYusufIsmail-Core
The core stuff for my mods


To use RealYusufIsmail core in your project, you need to add dependencies for Realyusufismail core. Add the following to your build.gradle.

You alse need to generate a GitHub token and add it along with your GitHub username to your personal `gradle.properties` file in `C:\Users\YOUR_USERNAME\.gradle` or `~/.gradle/gradle.properties`. This file may not exist, and you would have to create it yourself.

GitHub tokens can be generated [here](https://github.com/settings/tokens). Click _Generate New Token_ and click the checkmark for _read:packages_

Example of `gradle.properties` file in windows `C:\Users\YOUR_USERNAME\.gradle` or `~/.gradle/gradle.properties`
Example of `gradle.properties` file in mac`/Users/YOUR_USERNAME/.gradle/gradle.properties` or `~/.gradle/gradle.properties`


```gradle
//Your GitHub username
gpr.username=RealYusufIsmail

// Your GitHub generated token (bunch of hex digits) with read permission
gpr.token=paste_your_token_here
```

-----------------------------------

Code to add to `build.gradle`
```gradle
def gpr_creds = {
    username = property('gpr.username')
    password = property('gpr.token')
}
```
Undeer this.

```gradle
repositories {
     maven {
        url = uri("https://maven.pkg.github.com/realyusufismail/realyusufismail-core")
        credentials gpr_creds
    }
}
```

```gradle
dependencies {
    // Replace VERSION with the version you need, in the form of "MC_VERSION-MOD_VERSION"
    // Example: compile fg.deobf("net.yusuf:realyusufismailcore::1.16.5-2") {
    // Available builds can be found here: https://github.com/RealYusufIsmail/RealYusufIsmail-Core/packages/866444
    compile fg.deobf("net.yusuf:realyusufismailcore::1.16.5-2") {
    exclude module: "forge"
   }
}
```
