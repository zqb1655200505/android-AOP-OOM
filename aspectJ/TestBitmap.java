package ml.puredark.hviewer.aspectJ;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;

/**
 * Created by zqb on 2017/6/26.
 */
/*
        获取bitmap对象的2种方式
        1、BitmapFactory.decodeStream(fis);
        2、BitmapDrawable  bmpDraw = new BitmapDrawable(is);Bitmap bmp = bmpDraw.getBitmap();
 */
@Aspect
public class TestBitmap {
    private static final String TAG = "tag郑泉斌";
    private int createNum=0;
    private int recycleNum=0;
    //统计生成
    @Pointcut("call(* android.graphics.BitmapFactory.decode*(..)) ||" +
            "call(* android.graphics.drawable.BitmapDrawable.getBitmap(..))")
    public void countBitmapCreate(){}


    @Before("countBitmapCreate()")
    public void countBitmapCreateNum(JoinPoint joinPoint)
    {
        createNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"bitmap.log");
        log4jConfig.configLog();
        Logger log=Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()
                +"\n"+"createNum="+createNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());
    }

    //统计回收
    @Pointcut("call(* android.graphics.Bitmap.recycle(..))")
    public void countBitmapRecycle(){}

    @Before("countBitmapCreate()")
    public void countBitmapRecyclenum(JoinPoint joinPoint)
    {
        recycleNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"bitmap.log");
        log4jConfig.configLog();
        Logger log=Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()
                +"\n"+"closeNum="+recycleNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());
    }
}
