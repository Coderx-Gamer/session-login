# Session Login
A simple Minecraft mod that allows you to share accounts with people without sharing your email and password, uses your session ID to do so, your session ID has no connection to your email or password (may not be compatible with macOS or Linux)
.



# THIS IS NOT A RAT IT IS FOR LOGGING IN USING SESSIONS, NOT STEALING THEM!

### How to use it:
---

### Basic tutorial:

Step 1. Open the multiplayer screen and click the "Session" button on the top left.
Step 2. You should see an external window open up called "Session", in that window, click the "Copy To Clipboard" button.
Step 3. Send the string on your clipboard to your friend or whoever you are sharing the account with.
Step 4. Have the person you are sharing your account with copy the string you sent them to their clipboard.
Step 5. Have the person you are sharing your account with follow step 1 (they need the mod installed too), click the "Load From Clipboard" button and then click the "Login" button.
Step 6. The person you are sharing your account with should now be logged onto your account temporarily, done.

### Other mod features:

The "Load Current Account" button in the external session window will reset all the fields in the window to the account you are currently on (compatible with mods that let you log onto a different account without restarting the game).

### Notes:

Sometimes the external account window will start minimized, so you need to alt-tab into it, or hover over the minecraft window and select it.

It is not recommended to manually change any of the fields in the external session window as in most cases you will just not be able to join any servers with a modified configuration like that.

The "XUID" and "Client ID" fields in the external session window are normally empty and don't really do anything, they are just there for accessibility since its still account data stored within your game client, it may have a more significant use in the future.

The "Copy To Clipboard" button when you click it you get a string copied to your clipboard, that string is encoded in hex and is decoded on your client with the "Load From Clipboard" button.

The mod has not been tested on MacOS or Linux.

### Requirements:

Fabric API 0.68.0 (or higher) 1.19.2 (link: https://www.curseforge.com/minecraft/mc-mods/fabric-api/files/4123950)
Fabric Loader Version: 0.14.11 (or higher) 1.19.2 (link to the installer: https://fabricmc.net/use/installer)
Minecraft: 1.19.2

### Downloading/Building:

Download a pre-compiled jar at: https://github.com/Coderx-Gamer/session-login/releases

Building the mod from src:

---

Step 1. Download the entire folder of a github branch for this mod on the main github page.
Step 2. Navigate to that folder and open a terminal window there in that directory.
Step 3. Run "./gradlew build" in the terminal and wait for it to finish building.
Step 4. Navigate to build/libs in the directory and the largest jar file should be your compiled jar file mod.

---

## Contact:
Coderx Gamer#9335 on Discord.

---
