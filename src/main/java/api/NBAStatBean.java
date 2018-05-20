/*
=================================================================================
LICENSE: GNU GPL V2 (https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

OnyxFX, an app to query NBA statistical data.
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
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.URISyntaxException;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse.BodyHandler;


public class NBAStatBean {

    public static void main (String[] args) {
        var bean = new NBAStatBean("Terry", "Rozier", 2016);
        System.out.println(bean.getPPG());
        System.out.println(bean.getRPG());
        System.out.println(bean.getAPG());
    }

    //properties
    private double PPG;
    private double APG;
    private double RPG;

    //property getters
    public double getPPG() {
        return PPG;
    }
    public double getRPG() {
        return RPG;
    }
    public double getAPG() {
        return APG;
    }
        
    //property setters
    private void setPPG(double ppg) {
        this.PPG = ppg;
    }
    private void setRPG(double rpg) {
        this.RPG = rpg;
    }
    private void setAPG(double apg) {
        this.APG = apg;
    }
    
    //constructors
    public NBAStatBean(String firstName, String surname, int season) {
        try {
            //Webpage to scrape
            var URI = getURI(firstName, surname);

            //HTTP GET RESPONSE
            var HTTP_RESPONSE = HttpClient.newHttpClient().send (
                HttpRequest
                    .newBuilder(URI)
                    .headers("Foo", "foovalue", "Bar", "barvalue")
                    .GET()
                    .build(),
                BodyHandler.asInputStream()
            );

            //Status Code
            var statusCode = HTTP_RESPONSE.statusCode();

            //Success
            if (statusCode == 200 || statusCode == 201)
                setBasicStatLine(HTTP_RESPONSE.body(), season);
            //Otherwise
            else { 
                badInstance();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) { badInstance(); }
    }


    //utility methods
    private void badInstance (){
        PPG= -1.0;
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
                .append("01.html#")
            .toString()
        );
        /* For example: https://www.basketball-reference.com/players/b/birdla01.html# */
    }
    private String getSurnameSubstring(String surname) {
        var length = surname.length();
        switch (length) {
            case 1:
                return surname.substring(0,1);
            case 2:
                return surname.substring(0,2);
            case 3:
                return surname.substring(0,3);
            case 4:
                return surname.substring(0,4);
            case 5: 
                return surname.substring(0,5);
            default:
                return "";
        }
    }
    private void setBasicStatLine(InputStream in, int year) {
        var scanner = new Scanner(in);
        var html_sb = new StringBuilder();
        var record = false;
        var begin = new StringBuilder("id=\"per_game.").append(year).append("\"");
        var end = "/tr>";

        //Grab HTML content to analyze -- from "<tr id=\"per_game." to "</td></tr>"
        while (scanner.hasNext()) {
            var next = scanner.next();
            //from here
            if (next.equals(begin)){
                record = true;
                continue;
            }
            //to here
            else if (record == true && next.contains(end))
                break;
            //apend 
            else if (record == true) 
                html_sb.append(next);
        }

        //RPG
        html_sb.delete(0, html_sb.indexOf("trb_per_g\" >") );
        setRPG(Double.parseDouble(html_sb.substring(0, html_sb.indexOf("<"))));

        //APG
        html_sb.delete(0, html_sb.indexOf("ast_per_g\" >") );
        setAPG(Double.parseDouble(html_sb.substring(0, html_sb.indexOf("<"))));
        
        //PPG
        html_sb.delete(0, html_sb.indexOf("pts_per_g\" >") );
        setPPG(Double.parseDouble(html_sb.substring(0, html_sb.indexOf("<"))));
    }
}

// javac NBAStatBean.java --add-modules jdk.incubator.httpclient 