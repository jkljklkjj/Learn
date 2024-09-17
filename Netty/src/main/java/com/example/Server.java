package com.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        // 创建两个EventLoopGroup对象，bossGroup用于处理连接请求，workerGroup用于处理IO请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            // 创建Netty服务端启动对象
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)// 绑定这两个对象
                .channel(NioServerSocketChannel.class)// 指定NIO模式
                // 设置处理器，处理连接请求
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch){
                        ch.pipeline().addLast(new StringDecoder());// 将ByteBuf转换为字符串
                        // 处理入站事件以及操作
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            // 读取到数据后的操作
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(port);// 绑定端口
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
