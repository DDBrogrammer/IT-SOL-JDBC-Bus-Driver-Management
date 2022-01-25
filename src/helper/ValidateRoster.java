package helper;

import entity.Route;
import repository.RosterDAO;

import java.util.Map;

public class ValidateRoster {
private RosterDAO rosterDAO=new RosterDAO();

    public boolean validateChosenSubject(Map<Route,Integer>  routeList, int routeId){
        boolean checkRouteSubject=false;
        for (Map.Entry<Route,Integer> entry : routeList.entrySet())
           if(entry.getKey().getId()==routeId){
               checkRouteSubject= true;
           }
        return checkRouteSubject;
    }


}
