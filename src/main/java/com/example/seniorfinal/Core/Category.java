package com.example.seniorfinal.Core;

public class Category
{
    private int id;
    private String name;

    public Category(int id)
    {
       this.id=id;
       setName();
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public void setName()
    {
        switch(id)
        {
            case 1:  name = String.valueOf(CategoryType.ELECTRONICS); break;
            case 2:  name = String.valueOf(CategoryType.FURNITURE); break;
            case 3:  name = String.valueOf(CategoryType.CLOTHING); break;
            case 4:  name = String.valueOf(CategoryType.BOOKS); break;
            case 5:  name = String.valueOf(CategoryType.HOME_APPLIANCES); break;
            case 6:  name = String.valueOf(CategoryType.SPORTS_EQUIPMENT); break;
            case 7:  name = String.valueOf(CategoryType.VIDEO_GAMES); break;
            case 8:  name = String.valueOf(CategoryType.AUTOMOTIVE); break;
            case 9:  name = String.valueOf(CategoryType.TOYS); break;
            case 10: name = String.valueOf(CategoryType.TOOLS); break;
            case 11: name = String.valueOf(CategoryType.OFFICE_SUPPLIES); break;
            case 12: name = String.valueOf(CategoryType.GARDEN); break;
            case 13: name = String.valueOf(CategoryType.PET_SUPPLIES); break;
            case 14: name = String.valueOf(CategoryType.HEALTH_AND_BEAUTY); break;
            case 15: name = String.valueOf(CategoryType.MUSIC_INSTRUMENTS); break;
            default: name = "UNKNOWN_CATEGORY"; break;
        }
    }

    public String getName()
    {
     return name;
    }
}
