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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnyxAPIController {	    
	
	/* https://onyxfx-api.herokuapp.com/nbaBasicStatBean?firstName=Kobe&surname=Bryant&season=2006 */
    @RequestMapping(method=RequestMethod.GET, value="/nbaBasicStatBean")
    public NBAStatBean nbaBasicStatBean (@RequestParam(value="firstName", defaultValue="NBA") String firstName, 
    									@RequestParam(value="surname", defaultValue="Player") String surname, 
    									@RequestParam(value="season", defaultValue="0000") int season) {
        return new NBAStatBean(firstName, surname, season);
    }
}