package kr.momjobgo.eyou.web.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThumbnailMaker {

    public static void makeThumbnail(File imageFile, String imgFormat, String targetPath) throws IOException {

        int newWidth = 300;                                  // 변경 할 넓이
        int newHeight = 200;                                 // 변경 할 높이
        String mainPosition = "W";

        Image image;
        int imageWidth;
        int imageHeight;
        double ratio;
        int w;
        int h;

        // 원본 이미지 가져오기
        image = ImageIO.read(imageFile);

        // 원본 이미지 사이즈 가져오기
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        if(mainPosition.equals("W")){    // 넓이기준

            ratio = (double)newWidth/(double)imageWidth;
            w = (int)(imageWidth * ratio);
            h = (int)(imageHeight * ratio);

        }else if(mainPosition.equals("H")){ // 높이기준

            ratio = (double)newHeight/(double)imageHeight;
            w = (int)(imageWidth * ratio);
            h = (int)(imageHeight * ratio);

        }else{ //설정값 (비율무시)

            w = newWidth;
            h = newHeight;
        }

        // 이미지 리사이즈
        // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
        // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
        // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
        // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
        // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
        Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        // 새 이미지  저장하기
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(resizeImage, 0, 0, null);
        g.dispose();

        File targetFile = new File(targetPath);
        if(!targetFile.getParentFile().exists()) {
            targetFile.mkdir();
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        ImageIO.write(newImage, imgFormat, targetFile);
    }



}
