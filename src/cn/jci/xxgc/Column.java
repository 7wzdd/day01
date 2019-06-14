package cn.jci.xxgc;/*
 * @Author: 张梦楠
 * @Date: 2019/3/7 11:08
 * 简书：https://www.jianshu.com/u/d611be10d1a6
 * 码云：https://gitee.com/zhangqiye
 * @Description:
 */

import java.awt.image.BufferedImage;
import java.util.Random;

public class Column {
    BufferedImage image;
    //宽度和高度
    int width, height;
    //初始化柱子中心点的x,y坐标
    int x, y;
    //柱子中间的缝隙
    int gap;
    //两根柱子之间的距离
    int distance;

    //Random类创建对象
    Random rand = new Random();

    /*构造方法*/
    Column(int n) {
        //加载柱子图片
        image = BirdGame.column;
        //获得图片宽度和高度
        width = image.getWidth();
        height = image.getHeight();
        //柱子中间的缝隙为144
        gap = 144;
        //两根柱子之间的距离为245
        distance = 245;
        //柱子在屏幕中的初始水平位置
        x = 550 + (n - 1) * distance;
        //柱子在游戏屏幕中的竖直随机位置132~382
        y = rand.nextInt(250) + 132;
    }

    /*走步方法*/
    public void step() {
        x--;//柱子图片向左移动
        if (x == -width / 2) {
            //柱子水平位置
            x = distance * 2 - width / 2;
            //柱子随机竖直位置为132~382
            y = rand.nextInt(250) + 132;
        }
    }
}
