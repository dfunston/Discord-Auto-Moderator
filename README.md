# Discord-Auto-Moderator

This project has two distinct parts:
* A Minecraft plugin
* A Discord bot

The goal of this project was to create an automated moderation method for the Discord chat server run by a family-friendly Minecraft server.  While the plugin has not been fully developed, the moderation bot works without a hitch.  A Discord server was set up to disallow new users from sending or reading chat messages until they were approved.  A command given from within Minecraft would generate a randomized code for the player and send the same code to the bot, along with the user's name.  In the server, if a user of the same name gave the correct code, they would then be moved to a rank that could talk in chat and read what is sent.  The bot will also monitor chat, and if an inappropriate word was found, the message would be deleted and, after 3 strikes, the user would be kicked from the server.
