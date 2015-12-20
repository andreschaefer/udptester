# UDP Tester

## Features

- Simple command line UDP receiver
- Simple command line UDP sender
- Advanced Webserver with websocket based UDP sender / receiver
 
## Run binaries
 
### Command line receiver

Arguments

- `[port]` Port to listen for UDP packets

General 
```
java -jar udptester.jar ch.aschaefer.udp.UdpReceiver [port]
``` 

Windows (with included `receive.bat`)
```
receive [port]
```


### Command line sender

Arguments

- `[host]` Host / IP to send UDP packet to
- `[port]` Port to send UDP packet to
- `[message]` Hex String representation of message payload

```
java -jar udptester.jar ch.aschaefer.udp.UdpSender [host] [port] [message]
``` 

Windows (with included `send.bat`)
```
send [host] [port] [message]
```

### Web-Server

General
```
java -jar udptester.jar
```

Windows (with included `server.bat`)
```
server
```

Open in Browser

http://127.0.0.1:8080

If your running server on differnt machine, replace IP accordingly.



# Kurzanleitung Konsolen-Anwendungen

Hier wird die Inbetriebnahme des UDPtesters erläutert!
Dieser UDPtester ist zur Übertragungsüberwachung für UDP erstellt.


## Voraussetzungen

JAVA 8 muss installiert sein
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Folgende Dateien müssen im selben Ordner liegen damit die Software funktioniert:

- `console.bat`
- `receive.bat`
- `send.bat`
- `udptester.jar`


## zum empfangen von Daten:
(default Port 10000)

1. `receive.bat` öffen 
2. keine weiteren Schritte notwendig
3. Schließen ctrl. + c oder Fenster schließen

## zum senden von Daten:

1. `console.bat` öffnen
2. eingabe: `send.bat <IP> <Port> <Hex>`
3. enter
	Beispieleingabe für Broadcast-Message:
	`send.bat 192.168.178.255 10000 AA:BB:CC`
4. Schließen mit Fenster schließen


