package ml.puredark.hviewer.aspectJ;

import android.util.Log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;

/**
 * Created by zqb on 2017/5/25.
 */
@Aspect
public class TestCursor {
    private static final String TAG = "tag郑泉斌";
    private int openNum=0;
    private int closeNum=0;

    //统计新建
    @Pointcut("call(* android.database.sqlite.SQLiteDatabase.query*(..)) ||" +
            "call(* android.database.sqlite.SQLiteDatabase.*Query*(..))")
    public void countCursorOpen(){}

    @Before("countCursorOpen()")
    public void countCursorOpenNum(JoinPoint joinPoint){
        openNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"cursor.log");
        log4jConfig.configLog();
        Logger log= Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()
                +"\n"+"openNum="+openNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());
        Log.e(TAG+"countOpen", joinPoint.toShortString());
    }
    //统计关闭
    @Pointcut("call(* android.database.Cursor.close(..))")
    public void countCursorClose(){}

    @Before("countCursorClose()")
    public void countCursorCloseNum(JoinPoint joinPoint){
        closeNum++;
        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"cursor.log");
        log4jConfig.configLog();
        Logger log=Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()
                +"\n"+"closeNum="+closeNum
                +"\n"+"调用位置为："
                +joinPoint.getSourceLocation().getFileName());
        Log.e(TAG+"countClose", joinPoint.toShortString());
    }
}
