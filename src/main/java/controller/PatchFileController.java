package controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import servers.DiffApkUtils;
import utils.Contacts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

/**
 * Created by sxshi on 2017-12-27.
 * 此文件主要用于apk 拆分文件下载和上传
 */

//http://localhost:8080/patchController/uploadPage
//http://192.168.1.6:8080/patchController/uploadPage
@Controller
@RequestMapping(value = "/patchController")
public class PatchFileController {
    private static Logger log = LoggerFactory.getLogger(PatchFileController.class);

    @RequestMapping(value = "/uploadPage", method = RequestMethod.GET)
    public String showUploadPage() {
        return "uploadMultifile";
    }

    /**
     * 文件上传
     *
     * @param multiRequest
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doMultiUpload", method = RequestMethod.POST)
    public String multiUpload(MultipartHttpServletRequest multiRequest) throws IOException {
        Iterator<String> filesNames = multiRequest.getFileNames();
        while (filesNames.hasNext()) {
            String fileName = filesNames.next();
            MultipartFile file = multiRequest.getFile(fileName);
            if (!file.isEmpty()) {
                log.debug("Process file: {}", file.getOriginalFilename());
                file.getName();
                System.out.println("文件名称：" + fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:\\bsdiff\\",
                        file.getOriginalFilename()));
            }

        }
        return "success";
    }

    /**
     * 拆分文件
     * @return
     */
    @RequestMapping(value = "/doDiff",method = RequestMethod.GET)
    public String bsdiff(){
        DiffApkUtils.diff(Contacts.OLD_FILE,Contacts.NEW_FILE,Contacts.PATCH_FILE);
        return null;
    }
    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @return
     * @Description:
     */
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {

        String patchPath = Contacts.PATCH_FILE;
        String fileName = "apk.patch";
        File file = new File(patchPath);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
