# swe-webserver

## 1.1 Aufgabenstellung

Auf einem System soll ein **Messdaten-Erfassungssystem** implementiert werden. Seine Aufgabe ist es, mittels *Plugins* Messdaten zu erfassen und zu speichern. Die Messdaten können dann mittels einer *REST Schnittstelle* von der Sensor Cloud ausgelesen werden. Die Konfiguration des Systems erfolgt über ein Web Interface.

Aus dieser kurzen Angabe ergeben sich folgende konkreten Aufgabenstellungen:
* Implementierung eines WebServer
  - *Annahme*: Auf einem Embedded System sind die Ressourcen begrenzt, daher kann kein Windows bzw. LAMP Server ausgeführt werden.
  - *Annahme*: Jedoch ist das System in der Lage, ein Linuxsystem mit Java bzw. dem Mono-Framework auszuführen.
  - **Für die Übung**: Der WebServer ist selbst zu implementieren, einzig die Methoden URLEncode/URLDecode dürfen aus der BCL verwendet werden.

* Der WebServer kann über den Port 8080 erreicht werden.
* Der WebServer muss multiuserfähig sein.
* Der WebServer muss über ein Plugin-System verfügen. Diese Plugins führen den konkreten Request aus. Welche Plugins vom Webserver genutzt werden muss konfigurierbar sein. Weitere "Drittanbieter Plugins", müssen ohne neue Kompilierung des WebServers hinzugefügt werden können.
* Die Messdaten müssen in einer Datenbank Ihrer Wahl gespeichert werden. Um die Übung zu vereinfachen müssen die Plugins NICHT die Datenbank verwalten können. Sie dürfen sich auf ein korrektes Schema verlassen.

## 1.2 Plugins

**Temperatur-Messung**
* Ein eigener Thread liest laufend einen Sensor aus.
* Für die Übung: Es werden Zufallszahlen oder ein Sinus etc. berechnet.
* Diese Messwerte werden in der Datenbank Ihrer Wahl gespeichert. Implementieren Sie den DAL selbst, es sind keine OR-Mapper erlaubt.
* Auf der WebOberfläche können die Messdaten dargestellt werden.
* Für die Übung: Erzeugen Sie mindestens 10.000 Messwerte verteilt über die letzten 10 Jahre.
* Bei 10.000 Messwerten muss die Oberfläche zwangsläufig eine Suche bzw. Blättern unterstützen.
* Eine REST Abfrage ```http://localhost:8080/GetTemperature/2012/09/20``` soll alle Temperaturdaten des angegebenen Tages als XML zurückgeben. Das XML-Format ist frei wählbar.

**Statische Dateien**
* Dieses Plugin soll einfach nur Dateien aus dem Filesystem an den Browser zurücksenden.
* Diese Dateien sollen dazu genutzt werden, um im Browser HTML-Dateien, Bilder, Stylesheets, Scripts etc. in einem eigenen Feld einbinden zu können. Es soll explizit kein "Downloadmanager" oder "Filemanager" sein.

**Navi**
* Annahme: Das Embedded-System kann Speicherkarten aufnehmen.
* In einem Textfeld wird ein Straßenname eingegeben.
* Das Plugin gibt anschließend eine Liste aller Orte aus, in denen die Straße existert.
  * Zum Beispiel: Der Nutzer gibt "Arbeiter Strandbad Straße" ein und bekommt als Ergebnis "Wien".
* Eine Karte mit allen Straßen sowie Orten kann von *OpenStreetmap* bezogen werden.
  * Die Datei müsste ein ca. 4 GB großes XML-Dokument sein. ```http://download.geofabrik.de/osm/europe/```
  * Es genügt, wenn einer/eine die Datei herunterläd und allen anderen weitergibt.
* Eine eigene Seite im Navi-Plugin kann den Befehl "Straßenkarte neu aufbereiten" auslösen.
  * Dieser Befehl liest die OpenStreetmap-Karte (neu) ein und erstellt eine interne *Straßen <-> Ort* Zuordnung im Hauptspeicher (z.B. in einem Dictionary oder HashTable). Optional in einer Datenbank.
  * Dies ist mittels eine SAX-Parsers zu realisieren.
  * Es genügen die POI-Tags. Keine geografische Zuordnung durchführen!
  * XPath: ```//node|way/tag[@k, @v]```
  * Während dieser Aufbereitung darf das Plugin keine anderen Abfragen annehmen dürfen. Es muss stattdessen eine Warnmeldung ausgeben.
  
**ToLower**
* In einer textarea kann beliebig langer Text eingegeben werden.
* Ein Submit-Button sendet den Text mittels POST Request an den Server
* Am Server wird der Text in Kleinbuchstaben kovertiert.
* Das Ergebnis wird ein einem PRE-Tag unterhalb der textarea dargestellt.
  
## Zusätzliche Informationen

**20 Unit Tests**
Implementieren Sie 20 eigene Unit Tests. Testen Sie z.B. Ihre Plugins genauer.

**JavaDoc**
Versehen Sie alle *public* Klassen/Methoden/Felder mit Kommentaren, aus denen mittels Tools eine API Dokumentation erstellt werden kann. Führen Sie das Tool Ihrer Wahl aus und zeigen Sie uns das Ergebnis.

**Dokumentation**
Ausgedruckt circa eine DIN-A4 Seite lang.
* Benutzerhandbuch - Wie wird die Appliklation verwendet
* Lösungsbeschreibung - Wie wurde die Aufgabe gelöst
* Worauf bin ich stolz
* Was würde ich das nächste mal anders machen

**Codereview**
In der letzten Übung findet ein Code-Review statt, welches durch ein Noterechner bewertet wird.
Mizubringen sind:
* Ein lauffähiges Programm (.jar)
* SourceCode
* Dokumentation

