package com.longxinwu.service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @Author wang bo
 * @Date 2021/3/31 下午5:56
 */
@Service
public class FileService {

    public BufferedImage changeImgType(BufferedImage srcImage, int width, int height) {
        Image image = srcImage.getScaledInstance(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImage.createGraphics();
        //newImage = g2d.getDeviceConfiguration().createCompatibleImage(width,height,Transparency.TRANSLUCENT);
        //g2d = newImage.createGraphics();
        //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        //newImage.setRGB(0,0,0);
        g2d.drawImage(image, 0, 0, Color.WHITE, null);
        return newImage;
    }

    public byte[] process(BufferedImage image, int dpi) throws IOException {
        String formatName = "jpeg";
        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext(); ) {
            ImageWriter writer = iw.next();

            ImageWriteParam writeParams = writer.getDefaultWriteParam();
            writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            //调整图片质量
            writeParams.setCompressionQuality(1f);

            IIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), writeParams);
            Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", dpi + "");
            jfif.setAttribute("Ydensity", dpi + "");
            jfif.setAttribute("resUnits", "1"); // density is dots per inch

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageOutputStream stream = null;
            try {
                stream = ImageIO.createImageOutputStream(out);
                writer.setOutput(stream);
                writer.write(data, new IIOImage(image, null, null), writeParams);
            } finally {
                stream.close();
            }

            return out.toByteArray();
        }
        return null;

    }

    public void setImgDpi(File inFile, File outFile) throws Exception {
        String formatName = "jpeg";
        ImageReader imgReader = ImageIO.getImageReadersByFormatName(formatName).next();
        imgReader.setInput(new FileImageInputStream(inFile), true, false);
        IIOMetadata data = imgReader.getImageMetadata(0);
        BufferedImage image = ImageIO.read(inFile);
        //int w = 2550, h = -1;
        //Image rescaled = image.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
        //BufferedImage output = toBufferedImage(rescaled, BufferedImage.TYPE_INT_RGB);

        Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
        for (int i = 0; i < jfif.getAttributes().getLength(); i++) {
            Node attribute = jfif.getAttributes().item(i);
            System.out.println(attribute.getNodeName() + "=" + attribute.getNodeValue());
        }
        FileOutputStream fos = new FileOutputStream(outFile);
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
        JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
        jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
        jpegEncodeParam.setXDensity(300);
        jpegEncodeParam.setYDensity(300);
        jpegEncoder.encode(image, jpegEncodeParam);
        fos.close();
    }
    public static BufferedImage toBufferedImage(Image image, int type) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage result = new BufferedImage(w, h, type);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    public File multipartFiletoFile(MultipartFile file) throws Exception{
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
