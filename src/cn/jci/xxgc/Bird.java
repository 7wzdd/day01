package cn.jci.xxgc;/*
 * @Author: 张梦楠
 * @Date: 2019/3/7 11:08
 * 简书：https://www.jianshu.com/u/d611be10d1a6
 * 码云：https://gitee.com/zhangqiye
 * @Description:
 */

import java.awt.image.BufferedImage;

public class Bird {

    BufferedImage image;
    //鸟图片的长度和宽度
    int width, height;
    //小鸟初始在游戏屏幕中初始的位置
    int x, y;
    //鸟的大小
    int size;
    //重力加速度
    double g;
    //时间间隔t
    double t;
    //初始上抛速度
    double v0;
    //当前上抛速度
    double speed;
    //时间t后的竖直位移
    double h;
    //时间t后的水平位移
    double s;
    //倾角
    double alpha;
    //鸟的动画数组
    BufferedImage[] images = new BufferedImage[]
            {BirdGame.bird0, BirdGame.bird1, BirdGame.bird2, BirdGame.bird3,
                    BirdGame.bird4, BirdGame.bird5, BirdGame.bird6, BirdGame.bird7};
    //图片变换计数
    int index;

    /*构造方法*/
    Bird() {
        //加载第一帧鸟的图片
        image = BirdGame.bird0;
        //获得鸟图片的宽度
        width = image.getWidth();
        //获得鸟图片的高度
        height = image.getHeight();
        x = 132;
        //鸟在游戏屏幕中初始时的宽度为132和高度280
        y = 280;
        //鸟的大小为10
        size = 10;
        //重力加速度为4
        g = 4;
        //初始上抛速度为20
        v0 = 20;
        //初始化时间间隔为0.20s
        t = 0.20;
        //初始化当前上抛速度速度等于初始上抛速度
        speed = v0;
        h = 0;
        //初始化时间t后的竖直位移为0，倾角为0度
        alpha = 0;
        //初始化时间t后的水平位移为0
        s = 0;
        //初始化计数为0
        index = 0;
    }

    /*走步*/
    public void step() {
        //初始上抛速度等于当前上抛速度
        double v0 = speed;
        //小鸟的竖直位移
        h = v0 * t - g * t * t / 2;
        //小鸟的水平位移
        s = v0 * t;
        //小鸟往屏幕下方的位移
        y = y - (int) h;
        //小鸟往屏幕下方做匀加速运动
        double v = v0 - g * t;
        //当前上抛速度等于小鸟匀加速运动的速度
        speed = v;
        //计算倾角8
        alpha = Math.atan(h / 8);
        //如果超过上边界，让小鸟固定不出界
        if (y < size)
        {
            y = size;
        }
    }

    /*飞行*/
    public void fly() {
        //增加小鸟的图片变换计数，让小鸟呈现飞行的效果
        index++;
        //实现图片切换
        image = images[(index / 12) % 8];
    }

    public void flappy() {
        //重置速度 重新向上飞
        speed = v0;
    }

    /*碰撞方法(小鸟撞地面)*/
    public boolean hit(Ground ground) {
        boolean hit = y + size / 2 > ground.y;
        if (hit) {
            //将小鸟落在地面上
            y = ground.y - size / 2;
            //实现小鸟撞地栽头效果（角度偏移）
            alpha = 3.141592653 / 2;
        }
        return hit;
    }

    /*碰撞方法（小鸟撞柱子）*/
    public boolean hit(Column column) {
        int x1 = column.x - column.width / 2 - size / 2;
        int x2 = column.x + column.width / 2 + size / 2;
        int y1 = column.y - column.gap / 2 + size / 2;
        int y2 = column.y + column.gap / 2 - size / 2;
        //先检测是否在柱子范围内
        if (x > x1 && x < x2)
        {
            //再检测是否在缝隙内
            if (y > y1 && y < y2)
            {
                return false;
            }
            return true;
        }
        return false;
    }



}
