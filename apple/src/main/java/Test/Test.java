package Test;

import com.recognition.software.jdeskew.ImageDeskew;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

    public class Test {
    static final double MINIMUM_DESKEW_THRESHOLD = 0.05d;
    static ITesseract instance;
    public static void main(String[] args) throws Exception{
        Date data1=new Date();
          //testEn();
         testZh();
        //Wordbyword_extraction();
        Date data2=new Date();
        System.out.println((data2.getTime()-data1.getTime())/1000);
    }

    //使用英文字库 - 识别图片
    public static void testEn() throws Exception {
        File imageFile = new File("pictures/10.png");
        BufferedImage image = ImageIO.read(imageFile);
        //对图片进行处理
        image = flipImage(image);
        image = convertImage(image);
        instance = new Tesseract();//JNA Interface Mapping
        //划定区域
        // x,y是以左上角为原点，width和height是以x,y为基础
        //Rectangle rect = new Rectangle(20, 20, 1500, 200);
        String result = instance.doOCR(image);
        System.out.println(result);
    }

    //使用中文字库 - 识别图片
    public static void testZh() throws Exception {
        File imageFile = new File("pictures/10.png");
        BufferedImage image = ImageIO.read(imageFile);
        //对图片进行处理
        image = flipImage(image);
        image = convertImage(image);
        instance = new Tesseract();//JNA Interface Mapping
        instance.setLanguage("chi_sim");//使用中文字库
        //划定区域
        // x,y是以左上角为原点，width和height是以x,y为基础
        //Rectangle rect = new Rectangle(20, 20, 800, 200);
        String result = instance.doOCR(image);
        System.out.println(result);
    }

    //逐词提取
    public static void Wordbyword_extraction() throws Exception {
        //按照每个字取词
        int pageIteratorLevel = ITessAPI.TessPageIteratorLevel.RIL_SYMBOL;
        File imageFile = new File("pictures/10.png");
        BufferedImage image = ImageIO.read(imageFile);
        //对图片进行处理
        image = flipImage(image);
        image = convertImage(image);
        instance = new Tesseract();//JNA Interface Mapping
        instance.setLanguage("chi_sim");//使用中文字库
        List<Word> result = instance.getWords(image, pageIteratorLevel);
        for (Word word : result) {
            System.out.print(word.toString());
        }
    }

    //对图片进行处理 - 提高识别度
    public static BufferedImage convertImage(BufferedImage image) throws Exception {
        //按指定宽高创建一个图像副本
        image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3,   image.getHeight() * 3);
        return image;
    }

    //处理倾斜
    public static BufferedImage flipImage(BufferedImage image) throws Exception {
        //按指定宽高创建一个图像副本
        image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
             ImageDeskew id = new ImageDeskew(image);
             double imageSkewAngle = id.getSkewAngle(); //获取倾斜角度
               if ((imageSkewAngle > MINIMUM_DESKEW_THRESHOLD || imageSkewAngle < -(MINIMUM_DESKEW_THRESHOLD))) {
                   image = ImageHelper.rotateImage(image, -imageSkewAngle); //纠偏图像
        }
        return image;
        }
        }