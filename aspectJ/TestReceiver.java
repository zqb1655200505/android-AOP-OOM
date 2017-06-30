package ml.puredark.hviewer.aspectJ;

import android.util.Log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;

/**
 * Created by zqb on 2017/5/19.
 * 统计接收器的注册以及取消注册
 */
@Aspect
public class TestReceiver {
    private static final String TAG = "tag郑泉斌";
    private int registerNum=0;
    private int unRegisterNum=0;
    @Pointcut("call(* android.content.ContextWrapper.registerReceiver(..))")
    public void countRegisterReceiver(){}
    @Before("countRegisterReceiver()")
    public void countRegister(JoinPoint joinPoint){
        registerNum++;
        System.out.println("registerNum="+registerNum);
        Log.e(TAG+"countRegister", joinPoint.toShortString());

        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"receiver.log");
        log4jConfig.configLog();
        Logger log= Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()+"\n"+"registerNum="+registerNum);
    }

    @Pointcut("call(* android.content.ContextWrapper.unregisterReceiver(..))")
    public void countUnRegisterReceiver(){}
    @Before("countUnRegisterReceiver()")
    public void countUnRegister(JoinPoint joinPoint){
        unRegisterNum++;
        System.out.println("unRegisterNum="+unRegisterNum);
        Log.e(TAG+"countUnRegister", joinPoint.toShortString());

        //输出到文件
        Log4jConfig log4jConfig=new Log4jConfig("H-Viewer"+ File.separator+"receiver.log");
        log4jConfig.configLog();
        Logger log=Logger.getLogger(this.getClass());
        log.info("当前切点："+joinPoint.toShortString()+"\n"+"unRegisterNum="+unRegisterNum);
    }
}
