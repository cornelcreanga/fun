Simulate airplanes landing

Configuration File

The input of the program is a configuration file where one can specify the airplanes ready to land.

For each airplane, one specifies if it is large or regular
For each airplane one specifies if it is a normal or an emergency landing.
For each airplane one specifies after how many seconds it should initiate the dialog with a traffic controller (as not all the planes are ready to land in the same time )

A configuration file will look like this:

PlaneName(String), Enum(Regular/Large), Enum(Normal/Emergency), NoOfSeconds(Int)

Example:
Plane-1, Regular, Normal, 3
Plane-2, Regular, Normal, 4
Plane-3, Large, Normal, 4
Plane-4, Large, Emergency, 4

In the example above:
Plane-1 is a regular size plane, it's a normal landing, it will initiate the dialogue with the traffic controller after 3 seconds from the start of the program.
Plane-4  is a large plane, it's an emergency landing,  it will initiate the dialogue with the traffic controller after 4 seconds from the start of the program.

What is given:
a. There are 2 runways. 1 long runway and 1 short runway.
b. There are 2 traffic controllers.
c. It takes 5 sec for a regular plane to land. It takes 7 sec for a large plane to land.

Rules
a. Large airplanes can land only on the long runway.
b. The regular airplanes can land on both runways.
c. No two airplanes can land in the same time from the same runway.

Exchanged messages

Airplanes and the traffic controllers exchange messages.

The airplane can send the following messages to the traffic controller (you can use a message dispatcher or whatever you want).
a. Ready to land.
b. Landed.
c. Mayday.

The traffic controller can send the following messages to the airplane:
a. Please land on runway X.
b. Please circle around the airport.

A traffic controller can send the following message to the other traffic controller:
a. I have an emergency landing for an airplane with size regular/large.

If you need more messages, you can add them.

Example:
Airplane: Ready to land.
Traffic controller 1: Please circle around the airport.
Traffic controller 1: Please land on runway X.
Airplane: Landed (this message should be sent 5/7 seconds after the traffic controller sent the previous 'Please land' message)

Implementation.
a. Each airplane is represented by a thread.
b. Each traffic controller is represented by a thread.
c. The airplane initiates the dialogue with the traffic controller X seconds after its thread is started. (as specified in the configuration file)
d. All threads are started when the program starts.


Output:
You have to log all the exchanged messages. The format of the output should be:

Time, Message Sender -> Message Receiver, Message.


10:00, Plane1 -> Traffic Controller 1, ReadyToLand
10:01, Plane2 -> Traffic Controller 2, ReadyToLand
10:01, TrafficController -> Plane1, PleaseLandOnRunwayX
.....

Goals:
a. Don't crash the airplanes!
b. Think of an optimal way to manage the arrivals. (this is, all airplanes land in the shortest possible time).

Have a nice flight!