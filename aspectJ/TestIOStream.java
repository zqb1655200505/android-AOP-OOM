package ml.puredark.hviewer.aspectJ;

import android.util.Log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;

/**
 * Created by zqb on 2017/5/20.
 * 统计inputStream和outputStream对象的新建与关闭数量是否匹配
 */
@Aspect
public class TestIOStream {
    private static final String TAG = "tag郑泉斌";
    private int openNum=0;
    private int closeNum=0;

    //统计新建
    @Pointcut("call(java.io.*InputStream.new(..))")
    public void countIO(){}

    @Before("countIO()")
    public void countIONum(JoinPoint joinPoint){
        openNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"IO.log");
        log4jConfig.configLog();
        Logger log= Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()+"\n"+"openNum="+openNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());

    }
    //统计关闭
    @Pointcut("call(* java.io.*InputStream.close(..)) ||" +
            "call(* java.io.*OutputStream.close(..))")
    public void countIOClose(){}

    @Before("countIOClose()")
    public void countIOCloseNum(JoinPoint joinPoint){
        closeNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"IO.log");
        log4jConfig.configLog();
        Logger log=Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()+"\n"+"closeNum="+closeNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());
    }
}
