package testnetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

public class HollowServer {
	
	public static void main(String[] args) {
		ServerBootstrap bootstrap=new ServerBootstrap();
		NioServerSocketChannelFactory niofactory=new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		bootstrap.setFactory(niofactory);
		ChannelPipelineFactory channelfactory = new ChannelPipelineFactory() {
			
			public ChannelPipeline getPipeline() throws Exception {
				// TODO Auto-generated method stub
				return Channels.pipeline(
							new ObjectDecoder(ClassResolvers.cacheDisabled(
									this.getClass().getClassLoader()
									)),
							new ObjectServerHandler()
						);
			}
		};
			
		
		
		bootstrap.setPipelineFactory(channelfactory);
		bootstrap.bind(new InetSocketAddress(8000));
	}
/*	private static class HelloServerHandler extends SimpleChannelHandler{
		
		public void channelConnected(
				ChannelHandlerContext ctx,
				ChannelStateEvent e
				) {
			System.out.println("Hello world,I'm server");
		}
	}*/
	
	private static class ObjectServerHandler extends SimpleChannelHandler{
		public void messageReceived(ChannelHandlerContext ctx,MessageEvent e)
				throws Exception{
					Command command=(Command) e.getMessage();
					System.out.println(command.getActionName());
				}
	}
	

}
