package com.klzan.p2p.util;

import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PostsTemplateType;
import com.klzan.p2p.vo.posts.PostsTemplateVo;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sue on 2017/6/4.
 */
public class PostsTemplateUtils {

    public static List<PostsTemplateVo> getTemplate(PostsTemplateType templateType) {
        List<PostsTemplateVo> list = new ArrayList<>();
        String path = PostsTemplateUtils.class.getResource("/").getPath() + templateType.getPath();
        File file=new File(path);
        File[] tempList = file.listFiles();
        System.out.println("该目录下对象个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                System.out.println("文件夹：" + tempList[i]);
            }
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(tempList[i]), Charset.forName("UTF-8")));


//                String firstLine = new String(reader.readLine().getBytes(), Charset.forName("UTF-8"));
                String firstLine = reader.readLine();
                System.out.println("第一行：" + firstLine);
                String tplDes = StringUtils.substringBetween(firstLine, " {{", "}} ");
                System.out.println("第一行：" + tplDes);
                PostsTemplateVo templateVo = JsonUtils.toObject(tplDes, PostsTemplateVo.class);
                list.add(templateVo);
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
