package Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class RemoveImageWatermark {
    public static void binaryImage(String frompath,String topath) throws IOException {
        File file1 = new File(frompath);
        BufferedImage image = ImageIO.read(file1);
        int w=image.getWidth();
        int h=image.getHeight();
        double[][] zuobiao = new double[w][h];
        for(int i=0;i<w;i++)
        {
            for(int j=0;j<h;j++)
            {
                int pixel = image.getRGB(i, j);
                int red = (pixel & 0xff0000) >> 16;
                int green = (pixel & 0xff00) >> 8;
                int blue = (pixel & 0xff);
                //System.out.println(red+" "+green+" "+blue);
                zuobiao[i][j]=(red+green+blue)/3.0;
                if(red>=229&&green>=229&&blue>=229)
                {
                    int white=new Color(255,255,255).getRGB();
                    image.setRGB(i,j,white);
                }
//                if(red>=10&&red<=20&&green>=10&&green<=20&&blue>=10&&blue<=20)
//                {
//                    int cover=new Color(red,green,blue).getRGB();
//                    image.setRGB(i,j,cover);
//                }
//                else
//                {
//                    int white=new Color(255,255,255).getRGB();
//                    image.setRGB(i,j,white);
//                }
            }
        }
        File file2=new File(topath);
        ImageIO.write(image,"png",file2);
    }
    public static void main(String[] args) throws IOException
    {
            String frompath = "D:\\pictures\\haha.png";
            String topath = "D:\\pictures\\haha2.png";
            binaryImage(frompath,topath);
    }
}