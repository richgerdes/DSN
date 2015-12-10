=431 Project=
==Distributed Social Network==

==Group: Three Way Handshake===
Richard Gerdes
Sangini Shah
Corey Wu

==About==
This project is for a distributed social network build around the Moses Middleware. 
For more information on Moses and LGI, see http://www.moses.rutgers.edu.


==Network Components==
Controller (1+) - host for Moses Controllers
Directory - LGI agent that stores a list of registered peers and their public information (name, username, controller, etc)
Client - user interface for the network. Stores personal information, Photos, Albums, Friends list,

==Connecting to the network==
Launch the client class with the hostname and port of the desired controller
On launch of the client, the user will be asked for a username and password.
> If first login, these will be saved as the user credentials
> Client will join desired controller using the username and password specified
	> If the password is incorrect, user is prompted again
User can enter commands detailed below to interact with the network.

==Commands===
===Profile Attributes===
set <private|public> <name>
	- sets a particular attribute of the users profile identified by <name> to <public|private>
add <name> <value>
	- adds a new attribute to the users profile identified by <name> with the value <value>
	- if entry exists its gets over written
update <name> <value>
	- updated attribute of users profile identified by <name> with value <value>
	- if entry does not exist, it gets added
remove <name>
	- clears attribute of users profile identified by <name>
	- if <name> does not exist, nothing happens.
request <identity> <name>
	- requests from <identity> the profile attribute <name>
	- if user is blocked by <identity>, no response is given
	- if attribute does not exist in the profile of <identity>, no response is given
	- if the attribute <name> is private on the profile of <identity>, and sender is not approved, no response is given
	- otherwise, the user receives the given piece of information.
	
===Directory Access==
directory <join|leave> <name>
	- <joins|leaves> the directory located at the address <name>
directory <list|unlist> <name>
	- <lists|unlists> the attribute identified by <name> from all directories that the user has joined
directory search <name> <criteria>
	- makes a request to directory located at the address <name> for users meetings the criteria specified in the json strings <criteria>

===Friendship and Access Control===
friend <identity>
	- send a friend request to <identity>
approve <identity>
	- add <identity> to the list of users who can access private content
	- effective accept of friend request
revoke <identity>
	- remove <identity> from the list of users who can access private content
block <identity>
	- block all requests from <identity>
unblock <identity>
	- remove block on requests from <identity>

===Converse===
message <identity> <value>
	- send the message <value> to <identity>
	- if <identity> has blocked the sender, message is not delivered