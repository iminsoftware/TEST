package com.jack.customhandler.demo;

import com.jack.customhandler.javahandler.Handler;
import com.jack.customhandler.javahandler.Looper;
import com.jack.customhandler.javahandler.Message;

import java.util.UUID;




/**
 * 测试自定义Handle
 */
public class HandlerTest {

    public static void main(String[] args){
        System.out.println("hello world");

        Looper.prepare();

        //主线程
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("Thread:" + Thread.currentThread().getName() +
                        "msg receive:" + msg.toString());
            }
        };

        for (int i = 0; i < 10; i++) {
            //子线程
            new Thread(){
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message msg = new Message();
                        msg.what = 1;
                        synchronized (UUID.class){  //静态方法需要同步锁
                            msg.obj = UUID.randomUUID().toString();
                        }
                        System.out.println("Thread" + Thread.currentThread().getName() +
                                " send : " + msg);
                        handler.sendMessage(msg);
                    }

                }
            }.start();
        }

        //Looper轮询
        Looper.loop();
    }

}
