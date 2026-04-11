package com.example.seniorfinal.Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InvoiceCreator
{
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();




    public static void makeReceipt(ArrayList<ListingReceipt> receipts, String receiptName)
    {
        String jsonOutput = gson.toJson(receipts);

        try
        {
            String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop", receiptName).toString();
            try (FileWriter writer = new FileWriter(desktopPath))
            {
                writer.write(jsonOutput);
            }

            System.out.println("Receipt saved to: " + desktopPath);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to write receipt file", e);
        }

    }
}
