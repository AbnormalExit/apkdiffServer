package controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.IdGenertor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sxshi on 2018-3-12.
 * 图片上传
 * http://localhost:8080/picController/doUploadImage
 */
@Controller
@RequestMapping(value = "/picController")
public class PictureController {
    private static Logger log = LoggerFactory.getLogger(PictureController.class);

    // 上传图片文件
    @RequestMapping(value = "/doUploadImage", method = RequestMethod.POST)
    private void uploadImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        try{
            DiskFileItemFactory dff = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dff);
            List<FileItem> items = sfu.parseRequest(request);
            // 获取上传字段
            FileItem fileItem = items.get(0);
            // 更改文件名为唯一的
            String filename = fileItem.getName();
            if (filename != null) {
                filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
            }
            // 生成存储路径
            String storeDirectory =request.getSession().getServletContext().getRealPath("/files/images");
            System.out.println("文件路径：" + storeDirectory);
            File file = new File(storeDirectory);
            if (!file.exists()) {
                file.mkdir();
            }
            String path = genericPath(filename, storeDirectory);
            // 处理文件的上传
            try {
                fileItem.write(new File(storeDirectory + path, filename));
                String filePath = "/files/images" + path + "/" + filename;
                message = filePath;
            } catch (Exception e) {
                message = "上传图片失败";
            }
        } catch (Exception e) {
            message = "上传图片失败";
        } finally {
            response.getWriter().write(message);
        }
    }

    /**
     * 生成文件路径
     * @param filename
     * @param storeDirectory
     * @return
     */
    private String genericPath(String filename, String storeDirectory) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode&0xf;
        int dir2 = (hashCode&0xf0)>>4;

        String dir = "/"+dir1+"/"+dir2;

        File file = new File(storeDirectory,dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testParamers(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            String name=request.getParameter("name");
            String age=request.getParameter("age");
            System.out.println("name:"+name);
            System.out.println("name:"+age);
    }
}
