import java.util.*
import kotlin.collections.ArrayList

class Song (val titel : String, val interpret : String, val spieldauer: Int, var bewertung : Int = 0 ) {

    fun abspielen () {

        var angefangeneMin = (spieldauer / 60) + 1  // Spieldauer wird in Sekunden umgerechnet und + 1 gerechnet.
        for (i in 1..angefangeneMin) {
            println("Spiele: $titel von $interpret (Bewertung: $bewertung Punkte)")
        }
    }

    // Ändert die Bewertung
    fun bewertungAendern (value : Int) {
        if(value > 100) bewertung = 100
        if(value < 0) bewertung = 0
        if(value <= 100 && value >= 0) bewertung = value
    }

    fun searchCheck ( string : String) : Boolean {
        if(titel.contains(string)) {
            return true
        }
        if(interpret.contains(string)) {
            return true
        }
        return false
    }
}

class Playlist (val songlist : ArrayList<Song>) {

    fun gesamtSpielDauer () : Int{
        var gesamtSpielzeit = 0
        for (song in songlist) {
            gesamtSpielzeit += song.spieldauer
        }
        return gesamtSpielzeit
    }

    fun playAll() {
        for (song in songlist) {
            song.abspielen()
        }
    }
}

class Musikverwaltung () {
    val liedlist = arrayListOf<Song>()

    fun addSong (song : Song) {
        liedlist.add(song)
    }

    fun searchSong (searchString : String) : Song {
        for(lied in liedlist) {
            if(lied.searchCheck(searchString)) return lied
        }
        return liedlist[0]
    }

    fun bestSong() : Song{
        var besterSong = liedlist[0]

        for (lied in liedlist) {
            if (lied.bewertung > besterSong.bewertung) {
                besterSong = lied
            }
        }

        return besterSong
    }

    fun shufflePlaylist () : ArrayList<Song> {
        val shuffleList = arrayListOf<Song>()
        val zufallsGenerator = Random ()


        for (i in 1..3) {   // es werden drei zufällige Lieder der ShuffleList hinzugefügt
            val zufallsZahl = zufallsGenerator.nextInt(liedlist.size)
            shuffleList.add(liedlist[zufallsZahl])
        }

        return shuffleList
    }
}

fun main() {
    // Musikverwaltung erstellen
    val musik = Musikverwaltung ()

    // Songs erstellen
    val s1 = Song ("Döner","David", 300,60)
    val s2 = Song ("Pizza","Peter", 640,100)
    val s3 = Song ("Salat","Sabrina", 30,99)
    val s4 = Song ("Nudel","Noel", 720,90)
    val s5 = Song ("Brot","Ben", 460,77)
    val s6 = Song ("Käse","Karl", 220,40)
    val s7 = Song ("Wurst","Wolfgang", 335,20)
    val s8 = Song ("Milch","Marc Kevin", 565,13)

    // Songs der Musikverwaltung hinzufügen
    musik.addSong(s1)
    musik.addSong(s2)
    musik.addSong(s3)
    musik.addSong(s4)
    musik.addSong(s5)
    musik.addSong(s6)
    musik.addSong(s7)
    musik.addSong(s8)

    // Playlist mit drei Liedern erstellt
    val liste1 = arrayListOf<Song>()
    liste1.add(s1)
    liste1.add(s2)
    liste1.add(s3)
    val bestOfZenska = Playlist(liste1)

    // Playlist mit der shufflefunktion erstellt
    val shufflePlaylist = Playlist(musik.shufflePlaylist())

    // Playlisten abspielen
    bestOfZenska.playAll()
    shufflePlaylist.playAll()

    // Besten Song ausgeben
    println("Der Beste Song in der Musikverwaltung ist ${musik.bestSong().titel} von ${musik.bestSong().interpret} mit einer Bewertung von ${musik.bestSong().bewertung}")

    // Suche nach Song und spiele ab
    do {
        println("Bitte geben Sie einen Suchbegriff ein:")
        val eingabe = readLine()
        val lied = musik.searchSong(eingabe.toString())
        lied.abspielen()

    } while (eingabe != "stopp")
}