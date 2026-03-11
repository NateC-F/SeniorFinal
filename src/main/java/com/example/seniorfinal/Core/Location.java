package com.example.seniorfinal.Core;

import java.util.ArrayList;

public class Location
{

    public ArrayList<String> setStates()
    {
        return new ArrayList<>(java.util.List.of(
                "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA",
                "HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
                "MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
                "NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
                "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"
        ));
    }
}
