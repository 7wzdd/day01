package cn.jci.xxgc;/*
 * @Author: 张梦楠
 * @Date: 2019/3/7 11:09
 * 简书：https://www.jianshu.com/u/d611be10d1a6
 * 码云：https://gitee.com/zhangqiye
 * @Description:
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BirdGame extends JPanel //继承面板类
{
    //创建对象
    Bird bird;
    Column column1,column2;//创建对象
    Ground ground;//创建对象
    //设置游戏屏幕宽度为440，高度为670
    public static final int WIDTH =440;
    public static final int HEIGHT =670;
    public static BufferedImage background;
    public static BufferedImage gameOver;
    public static BufferedImage start;
    public static BufferedImage column;
    //图片缓存，保存这五张图片
    public static BufferedImage groundImage;

    /*静态变量birdi用于存储8个不同鸟的图片，实现鸟的动画效果*/
    public static BufferedImage bird0;
    public static BufferedImage bird1;
    public static BufferedImage bird2;
    public static BufferedImage bird3;
    public static BufferedImage bird4;
    public static BufferedImage bird5;
    public static BufferedImage bird6;
    public static BufferedImage bird7;

    public static AudioClip clip= null;
    //初始分数为0
    public int score = 0;
//	public boolean GameOver = false;//该标记用于检测游戏是否结束

    /*游戏状态参数*/
    int state;
    //游戏开始状态
    public static final int START = 0;
    //游戏运行状态
    public static final int RUNNING = 1;
    //游戏结束状态
    public static final int GAME_OVER = 2;

    static//静态块加载资源
    {
        try
        {//加载所有图片资源
            background = ImageIO.read(BirdGame.class.getResource("bg.png"));
            gameOver = ImageIO.read(BirdGame.class.getResource("gameover.png"));
            start = ImageIO.read(BirdGame.class.getResource("start.png"));
            column =ImageIO.read(BirdGame.class.getResource("column.png"));
            groundImage = ImageIO.read(BirdGame.class.getResource("ground.png"));
            bird0 = ImageIO.read(BirdGame.class.getResource("0.png"));
            bird1 = ImageIO.read(BirdGame.class.getResource("1.png"));
            bird2 = ImageIO.read(BirdGame.class.getResource("2.png"));
            bird3 = ImageIO.read(BirdGame.class.getResource("3.png"));
            bird4 = ImageIO.read(BirdGame.class.getResource("4.png"));
            bird5 = ImageIO.read(BirdGame.class.getResource("5.png"));
            bird6 = ImageIO.read(BirdGame.class.getResource("6.png"));
            bird7 = ImageIO.read(BirdGame.class.getResource("7.png"));

            clip = Applet.newAudioClip(new File("1.wav").toURI().toURL());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//捕捉异常

    /*构造方法 初始化各个变量*/
    BirdGame()
    {
        bird = new Bird();
        column1 = new Column(1);
        column2 = new Column(2);
        ground = new Ground();
        state = START;
    }

    /*主函数*/
    public static void main(String[] args) throws IOException, InterruptedException//捕获抛出异常
    {
        JFrame frame = new JFrame("飞翔的小鸟");//框架标题
        BirdGame game = new BirdGame(); // 面板对象
        frame.add(game); // 将面板添加到JFrame中
        frame.setSize(WIDTH, HEIGHT); // 设置大小
        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setVisible(true); // 尽快调用paint方法
//        AudioClip clip=Applet.newAudioClip(BirdGame.class.getResource("test.mp3"));
//        System.out.println(BirdGame.class.getResource("test.mp3"));
//        clip.play();
        game.action();	//调用动作方法
    }

    /*重写paint方法*/
    @Override
    public void paint(Graphics g)
    {/*绘制地面，柱子，鸟，背景的图片*/
        g.drawImage(background,0,0,null);
        g.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
        g.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);
        g.drawImage(ground.image,ground.x,ground.y,null);
        g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
        /*以下代码用于画鸟的倾斜（2D效果）*/
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
        g2.rotate(bird.alpha,bird.x,bird.y);

        paintScore(g);//画分数
        /*控制游戏 开始与结束*/
        switch(state)
        {
            case GAME_OVER:g.drawImage(gameOver,0,0,null);
                break;
            case START : g.drawImage(start,0,0,null);
                break;
        }

    }

    void Mu(){
        clip.play();
    }
    /*动作方法*/
    public void action() throws InterruptedException
    {


        MouseListener l=new MouseAdapter()
        {//鼠标按下事件
            @Override
            public void mousePressed(MouseEvent e)
            {
                Mu();
                //小鸟上飞
                try
                {//控制游戏开始，运行和结束三个状态
                    switch(state)
                    {
                        case GAME_OVER:
                            bird = new Bird();//创建bird对象
                            column1 = new Column(1);
                            column2 = new Column(2);//创建两个柱子对象并赋初值
                            state = START;//当前状态为重新开始
                            score = 0;//分数置为0
                            break;
                        case START:
                            state=RUNNING;//当前状态为游戏运行状态
                            break;
                        case RUNNING:
                            //小鸟上飞
                            bird.flappy();
                    }

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();//捕捉异常
                }
            }
        };
        addMouseListener(l);//将l挂在面板上


        while(true)
        {
            switch(state)
            {
                case START:
                    bird.fly();//小鸟飞行方法
                    ground.step();//地面走步方法
                    break;
                case RUNNING:
                    column1.step();
                    column2.step();//两个柱子走步方法
                    bird.step();//小鸟走步方法
                    bird.fly();//小鸟飞行方法
                    ground.step();//地面走步方法
                    Score();//计分方法
                    birdHit();//检测是否碰撞
                    break;
            }
            repaint();//重新绘制图像
            Thread.sleep(220/30);//实现0.22秒30次 throws InterruptedException解决异常
        }
    }

    /*画分数*/
    public void paintScore(Graphics g)
    {
        int x=40,y=40;//设置计分栏在游戏屏幕中的位置
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 25);//设置字体
        g.setColor(new Color(0xFFFFFF));//设置颜色为白色
        g.setFont(f); // 设置字体
        g.drawString("分数:" + score, x, y); // 画分数
    }

    /*计分方法*/
    public void Score()
    {
        if(bird.x==column1.x|| bird.x==column2.x)
        {
            score++;//小鸟的x坐标等于柱子1的横坐标或者等于柱子2的横坐标时分数加1
        }
    }

    /*检测是否碰撞*/
    public void birdHit()
    {
        if(bird.hit(column1) || bird.hit(column2) || bird.hit(ground))
        {
            state = GAME_OVER;//当小鸟撞击柱子1或柱子二或地面时，游戏为结束状态
        }
    }
}
