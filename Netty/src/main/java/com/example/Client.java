package com.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public void start() {
        // 创建EventLoopGroup对象
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();// 创建Netty客户端启动对象
        try{
            b.group(group)
                .channel(NioSocketChannel.class)// 指定NIO模式
                // 设置处理器，处理连接请求
                .handler(new ChannelInitializer<Channel>() {
                    // 将ByteBuf转换为字符串
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                    }
                })
                .connect("localhost", 8080)
                .sync()// 同步等待连接成功
                .channel()
                .writeAndFlush("Hello, world");// 发送数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
