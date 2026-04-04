package com.example.seniorfinal.Core;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

public class ImageBlob
{

    public ImageBlob()
    {}


    public static Blob imageToBlob(Image image,Connection connection) throws SQLException {
        if (image == null) return null;

        try {
            // Convert JavaFX Image to BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Write BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);  // Use "png" if you prefer PNG
            byte[] imageBytes = baos.toByteArray();

            // Create Blob from byte array
            Blob blob = connection.createBlob();
            blob.setBytes(1, imageBytes);
            return blob;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image blobToImage(Blob blob)
    {
        if (blob == null) return null;

        try (InputStream image = blob.getBinaryStream()) {
            return new Image(image);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
