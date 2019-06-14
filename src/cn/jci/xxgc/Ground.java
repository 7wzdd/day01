package cn.jci.xxgc;/*
 * @Author: 张梦楠
 * @Date: 2019/3/7 11:08
 * 简书：https://www.jianshu.com/u/d611be10d1a6
 * 码云：https://gitee.com/zhangqiye
 * @Description:
 */

import java.awt.image.BufferedImage;

public class Ground {
    BufferedImage image;
    //图片长短
    int width;
    //高度
    int height;
    //ground图片在游戏屏幕中的水平位置和垂直位置
    int x;
    int y;

    /*构造方法*/
    Ground() {
        //加载ground图片
        image = BirdGame.groundImage;
        //获得ground图片的宽度和高度
        width = image.getWidth();
        height = image.getHeight();
        x = 0;
        //初始ground图片在游戏屏幕的位置
        y = 500;
    }

    /*运动方法 */
    public void step() {
        //ground图片向左运动
        x--;
        //ground图片向左移小于109个像素时，使其水平位置又变为0
        if (x < -109)
        {
            x = 0;
        }
    }
}
