package util.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.quick.common.file.FileUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

public class QRCodeUtil {
    private static final String CHARSET = "UTF-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 1000;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;
    //白边大小，取值范围0~4
    private static int margin = 1;


    /**
     * user: Rex
     * date: 2016年12月29日  上午12:31:29
     * @param content 二维码内容
     * @param logoImgPath Logo
     * @param needCompress 是否压缩Logo
     * @return 返回二维码图片
     * @throws WriterException
     * @throws IOException
     * BufferedImage
     * TODO 创建二维码图片
     */
    private static BufferedImage createImage(String content, String logoImgPath, boolean needCompress) throws WriterException, IOException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        //hints.put(EncodeHintType.QR_VERSION,60 );

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoImgPath == null || "".equals(logoImgPath)) {
            return image;
        }

        // 插入图片
        QRCodeUtil.insertImage(image, logoImgPath, needCompress);
        return image;
    }

    private static BufferedImage createImage(String content, String logoImgPath, boolean needCompress,Boolean isColor) throws WriterException, IOException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        //hints.put(EncodeHintType.QR_VERSION,60 );

        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        // 根据左上角定位方块坐标，确定左上角定位方块范围
        int[] topLeftPoint = matrix.getTopLeftOnBit();
        int leftTopX = topLeftPoint[0];// 左上角方块左上角X坐标
        int leftTopY = topLeftPoint[1];// 左上角方块左上角Y坐标
        int rightBottomX = 0;// 左上角方块右下角X坐标
        int rightBottomY = 0;// 左上角方块右下角Y坐标
        for (int x = topLeftPoint[0]; x < matrix.getWidth(); x++) {
            if (!matrix.get(x, topLeftPoint[1])) {
                rightBottomX = x;
                break;
            }
        }
        for (int y = topLeftPoint[1]; y < matrix.getHeight(); y++) {
            if (!matrix.get(topLeftPoint[0], y)) {
                rightBottomY = y;
                break;
            }
        }
        // LOGO生成
        int logoWidth = rightBottomX - leftTopX;// LOGO宽度
        int logoHeight = rightBottomY - leftTopY;// LOGO高度
        int logoHalfWidth = logoWidth / 2;// LOGO宽度一半
        int logoFrameWidth = 1;// LOGO边框宽度
        BufferedImage logoImage = genLogo(logoImgPath, logoWidth, logoHeight,
                true);
        int[][] logoPixels = new int[logoWidth][logoHeight];
        if (logoImage != null) {
            for (int i = 0; i < logoImage.getWidth(); i++) {
                for (int j = 0; j < logoImage.getHeight(); j++) {
                    logoPixels[i][j] = logoImage.getRGB(i, j);
                }
            }
        }
        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[QRCODE_SIZE * QRCODE_SIZE];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                if (x > 0 && x < rightBottomX && y > 0 && y < rightBottomY) {// 左上角定位方块颜色,根据自己需要调整颜色范围和空白填充颜色
                    Color color = new Color(0, 0, 0);// 黑色
                    if (isColor == null || isColor) {// 彩色二维码
                        color = new Color(231, 144, 56);// 此处颜色固化，如果需要可扩展
                    }
                    int colorInt = color.getRGB();
                    pixels[y * QRCODE_SIZE + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                } else if (logoImage != null && x > halfW - logoHalfWidth
                        && x < halfW + logoHalfWidth
                        && y > halfH - logoHalfWidth
                        && y < halfH + logoHalfWidth) {// 添加LOGO(如果存在)
                    pixels[y * QRCODE_SIZE + x] = logoPixels[x - halfW
                            + logoHalfWidth][y - halfH + logoHalfWidth];
                } else if (logoImage != null
                        && ((x > halfW - logoHalfWidth - logoFrameWidth
                        && x < halfW - logoHalfWidth
                        + logoFrameWidth
                        && y > halfH - logoHalfWidth
                        - logoFrameWidth && y < halfH
                        + logoHalfWidth + logoFrameWidth)
                        || (x > halfW + logoHalfWidth
                        - logoFrameWidth
                        && x < halfW + logoHalfWidth
                        + logoFrameWidth
                        && y > halfH - logoHalfWidth
                        - logoFrameWidth && y < halfH
                        + logoHalfWidth + logoFrameWidth)
                        || (x > halfW - logoHalfWidth
                        - logoFrameWidth
                        && x < halfW + logoHalfWidth
                        + logoFrameWidth
                        && y > halfH - logoHalfWidth
                        - logoFrameWidth && y < halfH
                        - logoHalfWidth + logoFrameWidth) || (x > halfW
                        - logoHalfWidth - logoFrameWidth
                        && x < halfW + logoHalfWidth
                        + logoFrameWidth
                        && y > halfH + logoHalfWidth
                        - logoFrameWidth && y < halfH
                        + logoHalfWidth + logoFrameWidth))) {// 添加LOGO四周边框(如果存在)
                    Color color = new Color(0, 0, 0);// 黑色
                    if (isColor == null || isColor) {// 彩色二维码
                        // 此处固化为渐变颜色，如有需要可扩展
                        int R = (int) (50 - (50.0 - 13.0)
                                / matrix.getHeight() * (y + 1));
                        int G = (int) (165 - (165.0 - 72.0)
                                / matrix.getHeight() * (y + 1));
                        int B = (int) (162 - (162.0 - 107.0)
                                / matrix.getHeight() * (y + 1));
                        color = new Color(R, G, B);
                    }
                    int colorInt = color.getRGB();
                    pixels[y * QRCODE_SIZE + x] = colorInt;
                } else {// 其他部分二维码颜色
                    Color color = new Color(0, 0, 0);// 黑色
                    if (isColor == null || isColor) {// 彩色二维码
                        // 此处固化为渐变颜色，如有需要可扩展
                        int R = (int) (50 - (50.0 - 13.0)
                                / matrix.getHeight() * (y + 1));
                        int G = (int) (165 - (165.0 - 72.0)
                                / matrix.getHeight() * (y + 1));
                        int B = (int) (162 - (162.0 - 107.0)
                                / matrix.getHeight() * (y + 1));
                        color = new Color(R, G, B);
                    }
                    int colorInt = color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * QRCODE_SIZE + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                }
            }
        }
        BufferedImage image = new BufferedImage(QRCODE_SIZE, QRCODE_SIZE,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, QRCODE_SIZE, QRCODE_SIZE, pixels);

        return image;
        // 插入图片
    }

    /**
     * 把传入的原始LOGO图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param logoPath
     *            logo路径
     * @param height
     *            目标高度
     * @param width
     *            目标宽度
     * @param hasFiller
     *            比例不对时是否需要补白：true为补白; false为不补白;
     */
    private static BufferedImage genLogo(String logoPath, int height,
                                         int width, boolean hasFiller) {
        if (logoPath == null || logoPath.equals("")) {
            return null;
        }
        try {
            double ratio = 0.0; // 缩放比例
            File file = new File(logoPath);
            BufferedImage srcImage = ImageIO.read(file);
            Image destImage = srcImage.getScaledInstance(width, height,
                    BufferedImage.SCALE_SMOOTH);
            // 计算比例
            if ((srcImage.getHeight() > height)
                    || (srcImage.getWidth() > width)) {
                if (srcImage.getHeight() > srcImage.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / srcImage.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue()
                            / srcImage.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                destImage = op.filter(srcImage, null);
            }
            if (hasFiller) {// 补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D graphic = image.createGraphics();
                graphic.setColor(Color.white);
                graphic.fillRect(0, 0, width, height);
                if (width == destImage.getWidth(null))
                    graphic.drawImage(destImage, 0,
                            (height - destImage.getHeight(null)) / 2,
                            destImage.getWidth(null),
                            destImage.getHeight(null), Color.white, null);
                else
                    graphic.drawImage(destImage,
                            (width - destImage.getWidth(null)) / 2, 0,
                            destImage.getWidth(null),
                            destImage.getHeight(null), Color.white, null);
                graphic.dispose();
                destImage = image;
            }
            return (BufferedImage) destImage;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:30:09
     * @param source 二维码图片
     * @param logoImgPath Logo
     * @param needCompress 是否压缩Logo
     * @throws IOException
     * void
     * TODO 添加Logo
     */
    private static void insertImage(BufferedImage source, String logoImgPath, boolean needCompress) throws IOException{
        File file = new File(logoImgPath);
        if (!file.exists()) {
            return;
        }

        Image src = ImageIO.read(new File(logoImgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }

            if (height > HEIGHT) {
                height = HEIGHT;
            }

            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }

        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:32:32
     * @param content 二维码内容
     * @param logoImgPath Logo
     * @param destPath 二维码输出路径
     * @param needCompress 是否压缩Logo
     * @throws Exception
     * void
     * TODO 生成带Logo的二维码
     */
    public static void encode(String content, String logoImgPath, String destPath, boolean needCompress,Boolean isColor) throws Exception {
        BufferedImage image = null;
        if(isColor == null) {
             image = QRCodeUtil.createImage(content, logoImgPath, needCompress);
        }else {
             image = QRCodeUtil.createImage(content, logoImgPath, needCompress, isColor);
        }
        FileUtils.touch(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:35:44
     * @param content 二维码内容
     * @param destPath 二维码输出路径
     * @throws Exception
     * void
     * TODO 生成不带Logo的二维码
     */
    public static void encode(String content, String destPath,Boolean isColor) throws Exception {
        QRCodeUtil.encode(content, null, destPath, false,isColor);
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:36:58
     * @param content 二维码内容
     * @param logoImgPath Logo
     * @param output 输出流
     * @param needCompress 是否压缩Logo
     * @throws Exception
     * void
     * TODO 生成带Logo的二维码，并输出到指定的输出流
     */
    public static void encode(String content, String logoImgPath, OutputStream output, boolean needCompress,Boolean isColor) throws Exception {
        BufferedImage image = null;
        if(isColor == null) {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress);
        }else {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress,isColor);
        }
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static String putImage(String content, String logoImgPath, boolean needCompress,Boolean isColor) throws Exception {

        BufferedImage image = null;
        if(isColor == null) {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress);
        }else {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress,isColor);
        }

        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        //InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray());

        // 输出

          return "<img src= \"data:image/png;base64," + base64Img + "\"/>";
    }

    public static InputStream encodeIS(String content, String logoImgPath, boolean needCompress,Boolean isColor) throws Exception {

        BufferedImage image = null;
        if(isColor == null) {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress);
        }else {
            image =  QRCodeUtil.createImage(content, logoImgPath, needCompress,isColor);
        }

        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
        return is;
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:38:02
     * @param content 二维码内容
     * @param output 输出流
     * @throws Exception
     * void
     * TODO 生成不带Logo的二维码，并输出到指定的输出流
     */
    public static void encode(String content, OutputStream output,Boolean isColor) throws Exception {
        QRCodeUtil.encode(content, null, output, false,isColor);
    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:39:10
     * @param file 二维码
     * @return 返回解析得到的二维码内容
     * @throws Exception
     * String
     * TODO 二维码解析
     */
//    public static String decode(File file) throws Exception {
//        BufferedImage image;
//        image = ImageIO.read(file);
//        if (image == null) {
//            return null;
//        }
//        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Result result;
//        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
//        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
//        result = new MultiFormatReader().decode(bitmap, hints);
//        String resultStr = result.getText();
//        return resultStr;
//    }

    /**
     * user: Rex
     * date: 2016年12月29日  上午12:39:48
     * @param path 二维码存储位置
     * @return 返回解析得到的二维码内容
     * @throws Exception
     * String
     * TODO 二维码解析
     */
//    public static String decode(String path) throws Exception {
//        return QRCodeUtil.decode(new File(path));
//    }
}
