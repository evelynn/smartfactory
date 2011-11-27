/*
 * Communicator ..
 */

Ext.define('WMG.service.Communicator', {
	state : 'disconnected',
	context_path : '/smartfactory',
	cometd_path : '/cometd',
	public_channel : '/communicator/space/public', /* demo space */
	private_channel : '/communicator/private',
	member_channel : '/communicator/member/public', /* demo member list */
	service_channel : '/service/members',
	cookie_name : 'com.mesplus.smartfactory.communicator.state',
	alternate_server : 'xxx.yyy.zzz',

	constructor : function(config) {
		Ext.apply(this, config);
		/*
		 * Something to do.
		 */
		if (!config.username)
			throw new Exception('"username" should be configured.');
		
		var self = this;
		
		$.cometd.websocketEnabled = true;
		$.cometd.addListener('/meta/*', function(message) {
			console.log('meta : ');
			console.dir(message);
		});

		$.cometd.addListener('/meta/connect', function(message) {
			if (self.state === 'disconnecting') {
				self.state = 'disconnected';
				self.connectionClosed.apply(self, []);
			} else {
				var prestate = self.state;
				self.state = message.successful ? 'connected' : 'disconnected';

				if (prestate === 'disconnected' && self.state === 'connected') {
					self.connectionEstablished.apply(self, []);
				} else if (prestate === 'connected' && self.state === 'disconnected') {
					self.connectionBroken.apply(self, []);
				}
			}
			
			console.log("onMetaConnect : " + self.state);
		});
	
		$.cometd.addListener('/meta/handshake', function(message) {
			if (message.successful) {
				self.connectionInitialized.apply(self, []);
			}
			console.log("onMetaHandshake : " + message.successful);
		});
		
		$(window).unload(this.onWindowUnloaded);

//		this.restoreState();

		WMG.service.Communicator.superclass.constructor.apply(this, arguments);
	},

	restoreState : function() {
		var stateCookie = org.cometd.COOKIE ? org.cometd.COOKIE.get(this.cookie_name) : null;
		var state = stateCookie ? org.cometd.JSON.fromJSON(stateCookie) : null;
		var chat = new Chat(state);

		// restore some values
		if (state) {
			$('#username').val(state.username);
			$('#useServer').attr('checked', state.useServer);
			$('#altServer').val(state.altServer);
		}

		if (state) {
			setTimeout(function() {
				// This will perform the handshake
				this.join(state.username);
			}, 0);
		}
	},

	join : function() {
		var url = location.protocol + "//" + location.host + this.context_path + this.cometd_path;

		$.cometd.websocketEnabled = true;
		$.cometd.configure({
			url : url,
			logLevel : 'info'
		});
		$.cometd.handshake();
	},

	leave : function() {
		$.cometd.batch(function() {
			$.cometd.publish(this.public_channel, {
				user : this.username,
				membership : 'leave',
				chat : this.username + ' has left'
			});
			this.unsubscribe();
		});
		$.cometd.disconnect();

		this.username = null;
		this.last_peer = null;
		this.state = 'disconnecting';
	},

	send : function(text) {
		if (!text || !text.length)
			return;

		var colons = text.indexOf('::');
		if (colons > 0) {
			$.cometd.publish(this.private_channel, {
				space : this.public_channel,
				user : this.username,
				chat : text.substring(colons + 2),
				peer : text.substring(0, colons)
			});
		} else {
			$.cometd.publish(this.public_channel, {
				user : this.username,
				chat : text
			});
		}
		console.log('send : ');
		console.dir(text);
	},

	receive : function(message) {
		var sender = message.data.user;
		var membership = message.data.membership;
		var text = message.data.chat;

		console.log('receive : ');
		console.dir(message);
		
		if (!membership && sender == this.last_peer) {
			sender = '...';
		} else {
			this.last_peer = sender;
			sender += ':';
		}

		if (membership) {
			this.callback_public(sender, text);
			this.last_peer = null;
		} else if (message.data.scope == 'private') {
			this.callback_private(sender, text);
		} else {
			this.callback_extra(sender, text);
		}
	},

	members : function(message) {
		this.onMembers(message.data);
		console.log('members : ');
		console.dir(message);
	},

	subscribe : function() {
		this.public_subscription = $.cometd.subscribe(this.public_channel, this.receive);
		this.member_subscription = $.cometd.subscribe(this.member_channel, this.members);
		console.log('subscribe');
	},

	unsubscribe : function() {
		if (this.public_subscription) {
			$.cometd.unsubscribe(this.public_subscription);
		}
		this.public_subscription = null;
		if (this.member_subscription) {
			$.cometd.unsubscribe(this.member_subscription);
		}
		this.member_subscription = null;
		console.log('unsubscribe');
	},

	/*
	 * Callback functions
	 */
	connectionInitialized : function() {
		var self = this;
		// first time connection for this client, so subscribe tell everybody.
		try{
		$.cometd.batch(function() {
			self.subscribe();
			$.cometd.publish(self.public_channel, {
				user : self.username,
				membership : 'join',
				chat : self.username + ' has joined'
			});
		});
		} catch(e) {
			console.log("ERROR: " + e);
		}
		console.log("connectionInitialized : passed");
		if (this.callback_connectionInitialized)
			this.callback_connectionInitialized(this);

		console.log("connectionInitialized : " + this.state);
	},

	connectionEstablished : function() {
		console.log("connectionEstablished : " + this.state);
		
		// connection establish (maybe not for first time), so just
		// tell local user and update membership
		this.receive({
			data : {
				user : 'system',
				chat : 'Connection to Server Opened'
			}
		});
		$.cometd.publish(service_channel, {
			user : this.username,
			space : this.public_channel
		});
		if (this.callback_connectionEstablished)
			this.callback_connectionEstablished(this);
	},

	connectionBroken : function() {
		this.receive({
			data : {
				user : 'system',
				chat : 'Connection to Server Broken'
			}
		});
		if (this.callback_connectionBroken)
			this.callback_connectionBroken(this);
	},

	connectionClosed : function() {
		this.receive({
			data : {
				user : 'system',
				chat : 'Connection to Server Closed'
			}
		});
		if (this.callback_connectionClosed)
			this.callback_connectionClosed(this);

		console.log("connectionClosed : " + this.state);
	},

	onWindowUnloaded : function() {
		if ($.cometd.reload) {
			$.cometd.reload();
			// Save the application state only if the user was chatting
			if (this.state === 'connected' && this.username) {
				var expires = new Date();
				expires.setTime(expires.getTime() + 5 * 1000);
				org.cometd.COOKIE.set(this.cookie_name, org.cometd.JSON.toJSON({
					username : this.username,
					useServer : true, // or false
					altServer : this.alternate_server
				}), {
					'max-age' : 5,
					expires : expires
				});
			}
		} else {
			$.cometd.disconnect();
		}
	}
});