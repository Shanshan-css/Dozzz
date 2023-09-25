package com.example.snoozy22.utils;

public class StringUtils {
    String receive = "";
    RegexSearch rs = new RegexSearch();
    public static String[][] sSendTo = new String[][]{
            {"Ik begrijp het niet", "Herhaal het nog eens", "Ik kan het niet begrijpen", "Zeg het nog eens", "sorry"},
            {"Ja", "Goed", "Geweldig", "Prima", "okay", "Leuk","Klaar"},
            {"Nee","Niet zo goed","Helemaal niet", "Verdrietig", "Boos", "Niet zo geweldig", "Niet echt", "Niet goed", "Niet zoveel", "Iets anders", "Ik weet het niet"},
            {"Klaar","Nu","Tijd hebben"},
            {"Geen tijd","Druk"},
            {"Ouder","Mama","Papa","Ouders","Broer","Zus","Eigen bed","Oma","Opa","Moeder","Vader"},
            {"\\d{1,}"},
            {"Tot ziens","Dag"},
            {"Hallo","morning"}
    };
    public static String[][] sSendReceived = new String[][]{
            {"Hoi vriend/vriendin! Met Emma!", "Heb je nu even tijd om over je slaap te praten?"},
            {"Heb je goed geslapen?"},
            {"Heb je gisteren veel televisie gekeken of videogames gespeeld?"},
            {"Weet je waarom je slecht hebt geslapen?"},
            {"Wat heb je gisteren gegeten en gedronken?"},

    };
    public static String[][] sSendReceivedYes = new String[][]{
            {"Leuk om je te leren kennen!","Hoe voel je je vandaag?"},
            {"Goed om te horen!","In welk bed heb je afgelopen nacht geslapen?"},
            {"Leuk om te horen!", "Weet je waarom je goed hebt geslapen?"},
            {"Goed om te horen!", "Heb je gisteren veel televisie gekeken of videogames gespeeld?"},
            {"","Hoeveel minuten heb je gisteren televisie gekeken of videogames gespeeld?"},
            {"Klinkt goed!","Ga je vandaag iets leuks en spannends doen?"},
            {"Klinkt goed!","Waar moet je vandaag naartoe?"},
            {"Goed om te horen!","Wat heb je gepland voor vandaag?"},
            {"","Oké! Veel succes vandaag en veel plezier! Ik zie je morgen!"}
    };
    public static String[][] sSendReceivedNo = new String[][]{
            {"","Oké! Dan zie ik je snel! Laat het me alsjeblieft weten wanneer je klaar bent."},
            {"Oh nee! Ik hoop dat je je snel beter voelt!","In welk bed heb je afgelopen nacht geslapen?"},
            {"Oh nee!","Weet je waarom je slecht hebt geslapen?"},
            {"Oh nee!", "Heb je gisteren veel televisie gekeken of videogames gespeeld?"},
            {"","What heb je gisteren gegeten en gedronken?"},
            {""},
            {"Oh nee!","Wat is iets wat je graag doet?"},
            {"Dat maakt me verdrietig!","Is er misschien iets gebeurd gisteren? Of moet je vandaag iets doen wat niet zo leuk is?"},
            {"Oh nee!","Wat is iets wat je graag doet?"},
            {"","Oké! Veel succes en veel plezier vandaag! Ik zie je morgen!"}
    };


    public String question(int k) {
        String receive = "";
        for (int i = 0; i < sSendReceived[k].length; i++) {
            receive += sSendReceived[k][i];
        }
        return receive;
    }

    public String questionYes(int k) {
        String receive = "";
        for (int i = 0; i < sSendReceivedYes[k].length; i++) {
            receive += sSendReceivedYes[k][i];
        }
        return receive;
    }

    public String questionNo(int k) {
        String receive = "";
        for (int i = 0; i < sSendReceivedNo[k].length; i++) {
            receive += sSendReceivedNo[k][i];
        }
        return receive;
    }


    public boolean questionNoTime(String answers){
        boolean search5 = false;
        for (int j = 0; j < sSendTo[4].length; j++) {
            String pattern5 = sSendTo[4][j];
            search5 = rs.searchOrNot(answers, pattern5);
            if (search5) {
                break;
            }
        }
        return search5;
    }

    public boolean questionHasTime(String answers){
        boolean search4 = false;
        for (int j = 0; j < sSendTo[3].length; j++) {
            String pattern5 = sSendTo[3][j];
            search4 = rs.searchOrNot(answers, pattern5);
            if (search4) {
                break;
            }
        }
        return search4;
    }


    public boolean searchMatchSorry(String answers) {
        boolean search1 = false;
        for (int j = 0; j < sSendTo[0].length; j++) {
            String pattern1 = sSendTo[0][j];
            search1 = rs.searchOrNot(answers, pattern1);
            if (search1) {
                break;
            }
        }
        return search1;
    }

    public boolean searchMatchPeo(String answers) {
        boolean search6 = false;
        for (int j = 0; j < sSendTo[5].length; j++) {
            String pattern5 = sSendTo[5][j];
            search6 = rs.searchOrNot(answers, pattern5);
            if (search6) {
                break;
            }
        }
        return search6;
    }

    public boolean searchMatchNum(String answers) {
        boolean search7 = false;
        for (int j = 0; j < sSendTo[6].length; j++) {
            String pattern6 = sSendTo[6][j];
            search7 = rs.searchOrNot(answers, pattern6);
            if (search7) {
                break;
            }
        }
        return search7;
    }

    public boolean searchMatchEnd(String answers) {
        boolean search8 = false;
        for (int j = 0; j < sSendTo[7].length; j++) {
            String pattern7 = sSendTo[7][j];
            search8 = rs.searchOrNot(answers, pattern7);
            if (search8) {
                break;
            }
        }
        return search8;
    }

    public boolean searchMatchStart(String answers) {
        boolean search9 = false;
        for (int j = 0; j < sSendTo[8].length; j++) {
            String pattern8 = sSendTo[8][j];
            search9 = rs.searchOrNot(answers, pattern8);
            if (search9) {
                break;
            }
        }
        return search9;
    }

    public boolean searchMatchYes(String answers) {
        boolean search2 = false;
        for (int j = 0; j < sSendTo[1].length; j++) {
            String pattern2 = sSendTo[1][j];
            search2 = rs.searchOrNot(answers, pattern2);
            if (search2) {
                break;
            }
        }
        return search2;
    }


    public boolean searchMatchNo(String answers) {
        boolean search3 = false;
        for (int j = 0; j < sSendTo[2].length; j++) {
            String pattern3 = sSendTo[2][j];
            search3 = rs.searchOrNot(answers, pattern3);
            if (search3) {
                break;
            }
        }
        return search3;
    }


    public String lastQuestion(int l) {
        String receive = "";
        receive = sSendReceived[l][sSendReceived[l].length - 1];
        return receive;
    }
}
