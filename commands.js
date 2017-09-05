module.exports = {
	register: function(client, msg, guildID) {
		//Registration command
		var user = client.guilds.get(guildID).members.get(msg.author.id);
		var flag = false;
		user.roles.forEach(function (role) {
			if (role.name != "@everyone") { flag = true; }
		});
		if (flag == false) {
			msg.reply("Let's get you registered, " + user.displayName + ".");
			//Do register
			var dispName = user.displayName;
			var jsonSend = {
				"cmd": "register",
				"user": dispName,
				"time": msg.createdTimestamp
			};
			//netSend(JSON.stringify(jsonSend));
		} else {
			//Don't register, remind user they're good to go
			msg.reply("You're already registered on the server, " + user.displayName + "!");
		}
	}
}