package kr.momjobgo.eyou.web.common;

import kr.momjobgo.eyou.web.jpa.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class FileService {

    public FileService(@Value("${save-file-path}") String environmentFilePath){
        this.FIX_PATH = environmentFilePath + File.separator + "repository";
        log.info("****** File Save Repository = {}", FIX_PATH);
    }

    private final String FIX_PATH;

    public List<FileEntity> upload(HttpServletRequest req) {

        List<FileEntity> returnFileList = new ArrayList<>();

        MultipartHttpServletRequest mpReq = (MultipartHttpServletRequest) req;

        List<MultipartFile> fileList = mpReq.getFiles("file");

        for (MultipartFile mf : fileList) {

            if(mf.getSize() > 0) {

                FileEntity returnfile = new FileEntity();

                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
                String day = sdf.format(now);

                String originFileName = mf.getOriginalFilename();
                String contentType = mf.getContentType();
                String filePath = File.separator + day;
                String saveFileName = System.currentTimeMillis() + "_" + originFileName;

                returnfile.setFileRealName(originFileName);
                returnfile.setFileSaveName(saveFileName);
                returnfile.setPath(filePath);
                returnfile.setContentType(contentType);

                returnFileList.add(returnfile);

                log.info("===> FILE SAVE TO {}", FIX_PATH + filePath+ File.separator +saveFileName);

                File file = new File(FIX_PATH + filePath + File.separator + saveFileName);

                try {
                    if(!file.getParentFile().exists()) {
                        file.mkdir();
                    }
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    mf.transferTo(file);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return returnFileList;
    }

    public void download(HttpServletRequest request, HttpServletResponse response, FileEntity fileInfo) {

        try {

            File file = new File(FIX_PATH + fileInfo.getPath() + File.separator + fileInfo.getFileSaveName());

            String user_agent = request.getHeader("User-Agent");
            String fileName = encodingFileNameForBrowser(user_agent, file.getName());

            response.setContentType(fileInfo.getContentType()+"; UTF-8");
            response.setContentLength((int)file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            OutputStream out = response.getOutputStream();
            FileInputStream fis = null;

            //파일 카피 후 마무리
            try {
                fis = new FileInputStream(file);
                FileCopyUtils.copy(fis, out);
            } catch(Exception e){
                e.printStackTrace();
            }finally{
                if(fis != null){
                    try{
                        fis.close();
                    }catch(Exception e){}
                }
            }// try end;
            out.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //브라우저 정보에 따라 utf-8변경
    private String encodingFileNameForBrowser(String userAgent, String fileName) {
        String encodingFileName = null;

        try {

            if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1){
                encodingFileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
            } else if(userAgent.indexOf("Firefox") > -1) {
                encodingFileName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
            } else if(userAgent.indexOf("Opera") > -1) {
                encodingFileName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
            } else if(userAgent.indexOf("Chrome") > -1) {

                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < fileName.length(); i++) {

                    char c = fileName.charAt(i);

                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }

                encodingFileName = sb.toString();
            }else if(userAgent.indexOf("Edge") > -1) {
                encodingFileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
            }else {
                encodingFileName = "\"" + new String(fileName.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodingFileName;
    }

}
