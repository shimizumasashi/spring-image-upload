# spring-image-upload
Spring Bootによる画像ファイルアップロードと表示

## 準備

resourcesフォルダ内のファイルはビルド（Spring Bootアプリケーションの起動）時にクラスパスに含まれ、
実行可能JARやWARファイルにパッケージングされるため、デプロイ後もアップロードしたファイルが必ず保持されるわけではありません。

アップロードされたファイルを永続的に保存する必要がある場合は、ファイルシステムやクラウドストレージなどのresources以外の別の場所に保存することが望ましいです。

この例では、**Spring Bootプロジェクトの直下にstaticディレクトリを作成して利用しています。**
今回はstatic内に更にimageディレクトリを作成。

![image](https://user-images.githubusercontent.com/47343094/174580946-7a384acf-13ab-46ed-9e07-6965b080c023.png)

## ソースコード

### image.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>画像アップロードと表示</title>
</head>
<body>

	<h1>画像アップロード</h1>
	<form action="/upload" method="post" enctype="multipart/form-data">
		<input name="file" type="file">
		<input type="submit" value="送信">
	</form>
	
	<h1>アップロードした画像の表示</h1>
	<img alt="" th:src="${imageUrl}" style="width: 300px;">
</body>
</html>
```

### ImageController.java

```java
...

@Controller
public class ImageController {

    @GetMapping("/")
    public String show() {
        return "image";
    }
    
    /** 画像アップロード＆表示 */
    @PostMapping("/upload")
    public String uploadImage(
            @RequestParam("file") MultipartFile file,
            Model model) {

        try {
            // ファイル名（別名をつけても良い）
            String filename = file.getOriginalFilename();
            // 保存先パス
            String filePath = "static/image/" + filename;
            // ファイルをバイナリデータとして取得
            byte[] content = file.getBytes();
            // 保存
            Files.write(Paths.get(filePath), content);
            
            // アップロードした画像のURLを画面に渡す
            String imageUrl = "/image/" + filename;
            model.addAttribute("imageUrl", imageUrl);
            
        } catch (Exception e) {
            // エラー時
            e.printStackTrace();
        }

        // image.htmlを描画
        return "image";
    }
}
```



## ファイルアップロード画面

![image](https://user-images.githubusercontent.com/47343094/174579671-5703326e-67a9-418f-bf18-5145ed6ac891.png)

## アップロード（送信）

![image](https://user-images.githubusercontent.com/47343094/174579820-a1054654-34cc-4c84-9ae2-7d4708a54f1f.png)

## アップロード後のディレクトリ内

![image](https://user-images.githubusercontent.com/47343094/174580156-af8686ae-8b6e-4ee1-ae55-6ffd1f38b7e9.png)
