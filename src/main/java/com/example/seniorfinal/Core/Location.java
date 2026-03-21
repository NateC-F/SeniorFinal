package com.example.seniorfinal.Core;

import java.util.ArrayList;

public class Location
{

    public ArrayList<String> setStates()
    {
        return new ArrayList<>(java.util.List.of(
                "AK","AL","AR","AZ","CA","CO","CT","DE","FL","GA",
                "HI","IA","ID","IL","IN","KS","KY","LA","MA","MD",
                "ME","MI","MN","MO","MS","MT","NC","ND","NE","NH",
                "NJ","NM","NV","NY","OH","OK","OR","PA","RI","SC",
                "SD","TN","TX","UT","VA","VT","WA","WI","WV","WY"
        ));
    }
}
