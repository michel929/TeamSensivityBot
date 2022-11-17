package functions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreateImage {
    public static void createImage(List<String> image) throws IOException {
        List<BufferedImage> bufferedImages = new ArrayList<>();

        for(int i = 0; i < image.size(); i++) {
            bufferedImages.add(ImageIO.read(new URL(image.get(i))));
        }

        int width = bufferedImages.get(1).getWidth() / 4;
        int height = bufferedImages.get(1).getHeight() / 4;

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();

        Image tmp1 = bufferedImages.get(1).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Image tmp5 = bufferedImages.get(4).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Image tmp9 = bufferedImages.get(8).getScaledInstance(bufferedImages.get(8).getWidth() / 7, bufferedImages.get(8).getHeight() / 7, Image.SCALE_SMOOTH);
        Image tmp8 = bufferedImages.get(7).getScaledInstance(bufferedImages.get(7).getWidth() / 5, bufferedImages.get(7).getHeight() / 5, Image.SCALE_SMOOTH);
        Image tmp2 = bufferedImages.get(0).getScaledInstance(bufferedImages.get(0).getWidth() / 2, bufferedImages.get(0).getHeight() / 2, Image.SCALE_SMOOTH);
        Image tmp3 = bufferedImages.get(2).getScaledInstance(bufferedImages.get(2).getWidth() / 5, bufferedImages.get(2).getHeight() / 5, Image.SCALE_SMOOTH);
        Image tmp4 = bufferedImages.get(3).getScaledInstance(bufferedImages.get(3).getWidth() / 5, bufferedImages.get(3).getHeight() / 5, Image.SCALE_SMOOTH);
        Image tmp6 = bufferedImages.get(5).getScaledInstance(bufferedImages.get(5).getWidth() / 5, bufferedImages.get(5).getHeight() / 5, Image.SCALE_SMOOTH);
        Image tmp7 = bufferedImages.get(6).getScaledInstance(bufferedImages.get(6).getWidth() / 5, bufferedImages.get(6).getHeight() / 5, Image.SCALE_SMOOTH);


        g.drawImage(tmp1, 0, 0, null);
        int mitte = height / 2;
        int links = 100 + width / 2 - tmp7.getWidth(null) / 2 - 200 - tmp6.getWidth(null);

        //char1 rechts
        g.drawImage(tmp3, links + tmp7.getWidth(null) + tmp6.getWidth(null) + tmp4.getWidth(null), height / 2 - tmp3.getHeight(null) + 80, null);

        //char3 links
        g.drawImage(tmp6, links + tmp7.getWidth(null), height / 2 - tmp6.getHeight(null) + 80, null);

        g.drawImage(tmp7, links, height / 2 - tmp6.getHeight(null) + 80, null);

        //char2 Mitte
        g.drawImage(tmp4, links + tmp7.getWidth(null) + tmp6.getWidth(null), height / 2 - tmp4.getHeight(null) + 80, null);

        //grau
        g.drawImage(tmp5, 0, 0, null);

        //logo
        g.drawImage(tmp2, width / 2 - tmp2.getWidth(null) / 2 - 240, mitte -tmp2.getHeight(null) / 2 + 66, null);
        g.drawImage(tmp8, width / 2 - tmp8.getWidth(null) / 2 + 250, mitte - tmp8.getHeight(null) / 2 + 63, null);
        g.drawImage(tmp9, width / 2 - tmp9.getWidth(null) / 2 - 5, mitte - tmp9.getHeight(null) / 2 + 66, null);
        g.dispose();

        ImageIO.write(combinedImage, "PNG", new File("/home/michel929/TeamSensivity/findyourswf.png"));
    }
}
