/*
=================================================================================
LICENSE: GNU GPL V2 (https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

OnyxFX, an app to query NBA® statistical data.
Copyright (C) <2018>  ADRIAN D. FINLAY.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

Adrian D. Finlay, hereby disclaims all copyright interest in the program
`OnyxFX' (which makes passes at compilers) written by Oracle Corporation.

Adrian D. Finlay, May 19, 2018
Adrian D. Finlay, Founder
www.adriandavid.me
Contact: adf5152@live.com
=================================================================================
**/

package api;

import java.net.URI;
import java.util.Scanner;
/* Java 11 APIs */
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
import java.net.URISyntaxException;


public class NBAStatBean {

    /*
    public static void main (String[] args) {
        var bean = new NBAStatBean ( args[0], args[1], (Integer.parseInt(args[2])) );
        System.out.println( (args[0] +" " + args[1] + ":\t " ) + bean.getPPG() + "\t" + bean.getRPG() + "\t" + bean.getAPG() );

        var bean2 = new NBAStatBean("Terry", "Rozier", 2016);
        System.out.println( ("Terry" +" " + "Rozier" + ":\t " ) + bean2.getPPG() + "\t" + bean2.getRPG() + "\t" + bean2.getAPG() );

        var bean3 = new NBAStatBean("Kobe", "Bryant", 2005);
        System.out.println( ("Kobe" +" " + "Bryant" + ":\t " ) + bean3.getPPG() + "\t" + bean3.getRPG() + "\t" + bean3.getAPG() );

        var bean4 = new NBAStatBean("Michael", "Jordan", 1988);
        System.out.println( ("Michael" +" " + "Jordan" + ":\t " ) + bean4.getPPG() + "\t" + bean4.getRPG() + "\t" + bean4.getAPG() );
    } */

    //properties
    private double PPG;
    private double APG;
    private double RPG;

    //property getters
    public double getPPG() {
        return PPG;
    };
    public double getRPG() {
        return RPG;
    };
    public double getAPG() {
        return APG;
    };
        
    //property setters
    private void setPPG(double ppg) {
        this.PPG = ppg;
    };
    private void setRPG(double rpg) {
        this.RPG = rpg;
    };
    private void setAPG(double apg) {
        this.APG = apg;
    };
    
    //constructor
    public NBAStatBean(String firstName, String surname, int season) {
        try {
            //Webpage to scrape
            var URI = getURI(firstName, surname);

            /* HTTP GET Request, Response (Java 11 APIs) */
            // var request = HttpRequest.newBuilder().uri(URI).build();
            // var HTTP_RESPONSE = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());

            //HTTP GET Request, Response (Pre-Java 11)
            var con = (java.net.HttpURLConnection) URI.toURL().openConnection();
            con.setRequestMethod("GET");
            var HTTP_RESPONSE = con.getInputStream();

            //Status Code
            // var statusCode = HTTP_RESPONSE.statusCode(); (Java 11 API)
            var statusCode = con.getResponseCode(); //(Pre- Java 11)
                        
            //Success
            if (statusCode == 200 || statusCode == 201) {
                // setBasicStatLine(HTTP_RESPONSE.body(), season+1); (Java 11 API)
                setBasicStatLine(HTTP_RESPONSE, season+1); //(Pre-Java 11 )
            }
            //Otherwise
            else { 
                badInstance();
            }
        // } catch (URISyntaxException | java.io.IOException | java.lang.InterruptedException e) { badInstance(); } (Java 11 API throws InterruptedException)
        } catch (URISyntaxException | java.io.IOException e) { System.out.println("Exception: " + e.getCause()); badInstance(); }
    };

    //utility methods
    private void badInstance (){
        PPG = -1.0;
        RPG = -1.0;
        APG = -1.0;
    }; 
    private URI getURI(String firstName, String surname) throws URISyntaxException {
        return new URI (
            new StringBuilder("https://www.basketball-reference.com/players/")
                .append(surname.substring(0,1).toLowerCase() )
                .append("/")
                .append(getSurnameSubstring(surname))
                .append(firstName.substring(0,2).toLowerCase())
                .append("01.html")
            .toString()
        );
        /* For example: https://www.basketball-reference.com/players/b/birdla01.html# */
    };
    private String getSurnameSubstring(String surname) {
        var length = surname.length();
        switch (length) {
            case 1:
                return surname.substring(0,1).toLowerCase();
            case 2:
                return surname.substring(0,2).toLowerCase();
            case 3:
                return surname.substring(0,3).toLowerCase();
            case 4:
                return surname.substring(0,4).toLowerCase();
            case 5: 
                return surname.substring(0,5).toLowerCase();
            //greater then 5
            default:
                return surname.substring(0,5).toLowerCase();
        }
    };
    private void setBasicStatLine(java.io.InputStream in, int year) {

        //consider jsoup for the future.

        var scanner = new Scanner(in);
        var html = new StringBuilder();
        var record = false;
        var begin = new StringBuilder("id=\"per_game.").append(year).append("\"");
        var end = new StringBuilder("id=\"per_game.").append(year+1).append("\"");

        //Grab HTML content to analyze -- from "<tr id=\"per_game." to "</td></tr>"
        while (scanner.hasNext()) {
            var next = scanner.next();

            //from here
            if (next.contains(begin)){
                record = true;
                continue;
            }
            //to here
            else if (record == true && next.contains(end)) {
                break;
            }
            //apend 
            else if ( !next.contains(begin) && record == true) {
                html.append(next);
            }
            else 
                continue;
        }

        System.gc();

        //season not found
        if (!record) {
            badInstance();
            return;
        }  

        //RPG
        html.delete(0, (html.indexOf("trb_per_g\">")+("trb_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setRPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));

        //APG
        html.delete(0, (html.indexOf("ast_per_g\">")+("ast_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setAPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));
        
        //PPG
        html.delete(0, (html.indexOf("pts_per_g\">")+("pts_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setPPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));
    };
}

// javac NBAStatBean.java --add-modules java.net.httpclient 
// /usr/java/jdk11/bin/javac api/NBAStatBean.java
// /usr/java/jdk11/bin/java api.NBAStatBean