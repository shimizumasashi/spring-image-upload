package com.example.demo;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImageController {

    @GetMapping("/")
    public String show() {
        return "image";
    }
    
    /** 画像アップロード＆表示 */
    @PostMapping("/upload")
    public ModelAndView uploadImage(
            @RequestParam("file") MultipartFile file,
            ModelAndView mv) {

        try {
            // ファイル名
            String filename = file.getOriginalFilename();
            // 保存先パス
            String filePath = "static/image/" + filename;
            // ファイルをバイナリデータとして取得
            byte[] content = file.getBytes();
            // 保存
            Files.write(Paths.get(filePath), content);
            
            // アップロードした画像のURLを画面に渡す
            String imageUrl = "/image/" + filename;
            mv.addObject("imageUrl", imageUrl);
            
        } catch (Exception e) {
            // エラー時
            e.printStackTrace();
        }

        // image.htmlを描画
        mv.setViewName("image");
        return mv;
    }
}
