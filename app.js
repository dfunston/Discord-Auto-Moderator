'use strict';

//Node imports
const dapi = require('discord.js');
const keypress = require('keypress');
const commands = require('./commands.js');
var jsonfile = require('jsonfile');

//Get options
var obj = jsonfile.readFileSync("./options.json");
var token = obj["token"];
var host = obj["host"];
var sendPort = obj["sendPort"];
var listenPort = obj["listenPort"];
var guildID = obj["guildID"];

//Set up the language filter (WIP)
const BadWords = require('bad-words');
var Filter = new BadWords();

//Client object
const DClient = new dapi.Client();

//Set up socket information
var net = require('net');


//Process key commands (command line only)
keypress(process.stdin);


//Runs when bot logs in
DClient.on('ready', () => {
	console.log('Logged in as', DClient.user.username);
});

//Message handler (seems to do everything at the moment?)
DClient.on('message', msg => {
	var cont = msg.content;
	//If message is Direct Message (DM), check for commands
	if (msg.channel.type == "dm") {
		//Command character will be !
		if (cont.charAt(0) == "!") {
			//Commands go here
			//Split the contents, get only the first part
			var arCont = cont.split(" ", 1);
			//switch based on 
			switch (arCont[0].toLowerCase()) {
				case "!register":
					commands.register(DClient, msg, guildID);
					break;
			}
		}
	} else if (msg.channel.type == "text") {
		//Filter profanity in regular channels
		//Expand the following to be better
		if (Filter.isProfane(msg.content)) {
			//msg.reply("Uh oh!  That contains a bad word!");

			console.log(Date(msg.createdTimestamp), msg.author.username, msg.content);
			msg.delete();

		}
	}
});

//Function to kick user.
//Not currently implemented, waiting for more input.
function kickUser(msg) {
	var member = msg.member;
	member.kick().then((member) => {
		//Success
		msg.channel.send(member.displayName + " has been kicked.  Reason: Profanity.");
	}).catch(() => {
		console.log("Error");
	});
}


//Log in command
DClient.login(token)

function netSend(msg) {
	//Net transfer handler (?)
	var client = net.connect(sendPort, host);
	client.write(msg);
	client.end();
}

//General Functions
//May be split into another file in the future

//Shutdown command
function shutdown() {
	process.exit();
}

process.stdin.on('keypress', function (ch, key) {
	//Handle key input (for now, only shutdown command)
	if (key) {
		switch (key.name) {
			case 'q':
				//exit process
				shutdown();
				break;
			case 'h':
				//Print help text
				console.log('Current key commands:');
				console.log('q: quit the application');
				console.log('p: send debug packet through Websocket');
				console.log('h: show help dialogue (this message)');
				break;
		}
	}
});
//Necessary for key input commands
process.stdin.setRawMode(true);
process.stdin.resume();

//Create socket listener
net.createServer(function (sock) {
	//Receive a connection
	console.log('Connected: ' + sock.remoteAddress + ':' + sock.remotePort);
	//Add a data handler
	sock.on('data', function (data) {
		//Data was received in the socket
		//Handle data here
		console.log(data.toString());
	});

	//Add a Close event handler
	sock.on('close', function (data) {
		//Closed connection
		console.log('Closed: ' + sock.remoteAddress + ' ' + sock.remotePort);
	});
}).listen(listenPort, host);
console.log('Server listening on ' + host + ':' + listenPort);