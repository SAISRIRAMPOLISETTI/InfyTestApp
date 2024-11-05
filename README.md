# InfyTestApp

A car rental company wants notifications if the renter of a car drives at a speed above a given
limit.
The limit shall be set by the fleet company and it shall be possible for them to set the maximum
permitted speed before a rental period starts.
It shall also be possible for different customers to have different maximum speeds set.
If the set speed is exceeded, the rental company shall be notified and the user given a warning
alert.
The first communication channel will happen through Firebase. It is possible that we will need an
alternative communication channel to AWS as well through.

#CARManager.kt
The CARManager class manages car-related operations for a fleet management system.
It handles tasks such as switching communication channels for speed limit updates, authenticating
car renters, accessing car profiles, retrieving vehicle speed limits, and notifying the fleet API
when a car exceeds the speed limit. This class acts as an intermediary between the car management
system and external communication services like Firebase and AWS.

#CarSpeedService.kt
The CarSpeedService class is an Android Service that provides real-time car speed data to client
applications via an AIDL interface.
It uses the ISpeedAidlInterface.Stub() binder to handle client connections and facilitate
inter-process communication (IPC).
The getCarSpeed() method retrieves the car speed by calling getCarSpeedFromCAN(), which interacts
with the car's systems to get the speed data.
The onBind() method returns the binder to enable client access.

#ISpeedAidlInterface.aidl
This AIDL file defines an interface that allows clients to access the getCarSpeed() method in the
CarSpeedService class. It facilitates inter-process communication (IPC) so that different
applications or components can interact with the service and retrieve car speed data.

#SpeedLimitExporterFactory.kt
The SpeedLimitExporterFactory class manages and updates communication channels related to speed
limit notifications. It provides functionality for updating the communication channel and sending
notifications when a speed limit is exceeded for a given car ID.
setSpeedLimitCommunicationChannel: Updates the current communication channel.
updateFleetAPIForSpeedLimitExceeds: Sends a notification to the communication channel if a speed
limit is exceeded for a specified car ID.


