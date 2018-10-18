Friends - 3 new types (Shield, Spray Gun, Targeted Missile)
Shield- Shield is enabled when player presses H and surrounds Falcon  (dull halogen noise buzzes).
        Shield absorbs 10 hit points and fades to a lighter blue each time it is hit.
        Shield has power bar at bottom right of screen to tell player how much current shield is left and
        how many more shields player can turn on. (Shield:lines 38-45)
        Shield is incremented every time player scores 5,000 points. (Game:Lines 494-499)

Spray Gun- SprayGun is a new object that runs method spray() when players presses 'Y'.
        Method spray() creates five new bullets and fires them.
        Expiration time for each bullet is half that of a normal bullet, 10 vs 20.

Targeted Missile- Targeted missile was extended off of cruise missile.
        Targeted missiles are fired when the player presses 'F' and can be controlled as long as player
        continues to hold down the 'F' key. (Targeted Missiles: Lines 32-76)
        Player loses control of the Falcon when they are controlling a targeted missile.
        Targeted missiles take away 3 points from Special Foes like the Titanium Asteroid and UFO and are
        especially useful in attacking the Orb on the UFO.
        Targeted Missiles have a pretty long life but expire quickly after the 'F' key is released.


Foes - 2 new Special Foes (Titanium Asteroid, UFO)
Titanium Asteroid- Titanium Asteroid extends off a regular Asteroid.  Unlike regular Asteroids, Titanium
        Asteroids start off smaller and respawn into larger Asteroids each time you kill one.  They are filled in
        with random colors and depending on how big they are can take multiple hits to destroy.
        They continue to Respawn 3 times until they reach size 3 and have a radius of 150.
         Each TAsteroid flies around with a defense level bar floating over it to tell the player how many more hits
          are required to kill it. (TitaniumAsteroid:lines79-95).  The power bar scales in length with the size of the
          respawned Asteroid.
          Respawning TAsteroids are protected for a few frames(players cant hurt them and vice versa).

UFO and UFO Orb-  UFOs are spawned at the end of each odd level starting at level 3 and appear only after every other
        foe has been killed.(Game:lines 143-157) Falcon is momentarily protected when the UFO appears.
        UFOs have a life based on the level in the game and the defense level is displayed in a RED power bar
        that appears at the bottom left corner of the Screen. (UFO:Lines 170-180)
        UFOs continuously reorient themselves to face the Falcon and fire bullets every 50 frames directly at
        the Falcon. (UFO:Lines 104-117)
        UFOOrbs are its own class.  Each UFO depends on its UFOOrb and vice versa.
        UFOOrbs hover directly on top of the UFO and because of the UFOs orientation toward the falcon are protected
        from direct fire from the falcon.  UFOOrbs have significantly less defense than the UFO.
        For this reason it might be advantageous for a player to try to hit the UfoOrb with a targeted missile.
        (I envisioned Star Wars when I created the Orb)
        UFOOrbs have their own Blue power bar at the lower left corner of the screen.  (UfoORB:Lines 83-93)
        UFOs have a significantly longer life of 19,999 frames but eventually expire and die.

        UFOs start with a life of 30 in round 3 and score of 3000.  The UfoOrb in level 3 starts with a life of 2
        and initially only requires one hit from a targeted missile to die.  I decided that players will be awarded
        2x the score of killing a UFO if they do it by destroying its orb because I personally found it pretty difficult
        to actually hit the orb.


Debris - 3 types(Scattering Shards, Colored Spots, Broken Edges)
Shards exploding in a circle away from foe- Created in Class- the scatteredDebris() Method  creates shard objects that
        explode from the center of a foe when it is killed.
         This debris is implemented when a Special Foe is defeated.

Colored spots - Created in Class - creates a few circles of random color when a hit takes place.
        Implemented each time a Titanium asteroid takes a hit but not necessarily defeated.

Edges of Foe are broken and float away-  Created a class line that takes the coordinates of each point of a Foe and
        uses these lines/edges to give the appearance of scattered debris with Random Colors floating in random directions.
        Debris has an overloaded constructor and this type of confetti debris is formed if you supply the class with a foe.
        (Debris:Lines 23-38)
        Implemented each time you hit a regular Asteroid.



Miscellaneous-
Cruise missile still exists but are now red in color and they also take away 3 points from special foes.
All new Special foes implement Scorable and have scores that change based on how difficult they are.  (How many hits
        it takes to kill them which advances in each level.  )
Titanium Asteroids are only available in Even number levels and Count=level/2 TAsteroids appear.
Nuke all button created in class also exisits

Game min Requirements - have all been implemented
Debris
HyperSpace
Life Floater increments life and is reflected at bottom of Screen
Score works


