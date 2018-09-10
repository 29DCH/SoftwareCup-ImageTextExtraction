package Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Screenshots {
    private String srcpath ;
    private String subpath ;
    private   int x ;
    private   int y ;
    private   int width ;
    private   int height ;
    public  Screenshots() {

    }
    public  Screenshots( int x, int y, int width, int height) {
        this.x = x ;
        this.y = y ;
        this.width = width ;
        this.height = height ;
    }

    public void cut()throws IOException {
        FileInputStream is =   null ;
        ImageInputStream iis = null ;
        try {
            is =new FileInputStream(srcpath);
            Iterator < ImageReader > it=ImageIO.getImageReadersByFormatName("png");
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true ) ;
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi=reader.read(0,param);
            ImageIO.write(bi,"png",new File(subpath));
        } finally {
            if (is != null )
                is.close() ;
            if (iis != null )
                iis.close();
        }
    }

    public static void main(String[] args) {
        Screenshots screenshots = new Screenshots(0, 0, 493, 80);
        screenshots.srcpath = "pictures/1.png";
        screenshots.subpath = "pictures/1.png";
        try {
            screenshots.cut();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

