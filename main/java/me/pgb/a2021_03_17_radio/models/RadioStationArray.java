package me.pgb.a2021_03_17_radio.models;

public class RadioStationArray {

    private static String[] stringList;
    public static RadioStation[] radioStations = {
            new RadioStation("https://antenaone.crossradio.com.br/stream/1;?1616938247039", "Antena 1 Porto Alegre", "Brazil", "" ),
            new RadioStation("http://fmstudio.fairfield.edu:8000/listen", "WVOF 88.5 FM Fairfield, CT", "USA", ""),
            new RadioStation("http://www.antenaweb.pt/globalplayer/bomumplugged.m3u", "Best Of Música Umplugged  ", "Portugal", ""),
            new RadioStation("http://ais-sa2.cdnstream1.com/2320_64.mp3", "WLFR 91.7 FM Pomona, NJ", "USA", "" )
    };

    public static String[] getArrayOfRadioLinks() {
        stringList = new String[radioStations.length];
        for (int i = 0; i < radioStations.length; i++) {
            stringList[i] = radioStations[i].getStreamLink();
        }
        return stringList;
    }

    public static String[] getArrayOfRadioNames() {
        stringList = new String[radioStations.length];
        for (int i = 0; i < radioStations.length; i++) {
            stringList[i] = radioStations[i].getName();
        }
        return stringList;
    }
}
