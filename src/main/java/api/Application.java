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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }
}