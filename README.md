# MusicPlayerAndroidApp-ClientPlayer

This is of one the two Android App (togethet together in sync) which will act a client between both. The first app, named *ServerAudio*
stores a number of audio clips, such as songs or other recordings. The clips are numbered 1 through n, where n is the total number of clips.
The app contains a service intended to be bound (as opposed to started), which exposes an API for clients
to use. The API supports such functionality as playing one of the audio clips, pausing the clip, resuming
the clip and stopping the playing of the clip altogether. The application should include at least 3 audio clips
of variable duration. However, the duration of Clip #1 should be at least 30 seconds but no more than 3
minutes.<br />
<br/>
The second app, *ClientPlayer* consists of an activity that exposes functionality for using the *ServerAudio*
and binds to the service for playing desired audio clips. Your interface should mininally include appropriate
View elements for the following functionality: (1) Playing a given clip (by number), (2) Pausing the
playback, (3) Resuming the playback, and (4) Stopping the player. When the client activity is stopped,
the service should continue playing; however, the service should be unbound and stopped if the activity is
destroyed.<br />
<br/>
In addition the *ClientPlayer* app keeps track of all requests that an interactive user made (e.g., play
Clip 1, pause Clip 1, stop player, etc.). The requests are displayed in a scrollable list view maintained by the
app.</br/>
<br/>
Tested by using Nexus 5 virtual device running the Android platform (API 23—Marshmallow). No backward compactibilty provided.

PS: Built by using Android’s built-in *MediaPlayerService*.
