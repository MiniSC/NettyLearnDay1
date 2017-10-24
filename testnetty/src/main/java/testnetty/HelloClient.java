package testnetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.sun.glass.ui.ClipboardAssistance;

public class HelloClient {
	public static void main(String[] args) {
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			public ChannelPipeline getPipeline() throws Exception {
				// TODO Auto-generated method stub
				
				return Channels.pipeline(
							new ObjectEncoder(),new ObjectClientHandler()
						);
			}
		});
		
		bootstrap.connect(new InetSocketAddress("111.0.13.65",8000));
	}
	
/*	private static class HelloClientHandler extends SimpleChannelHandler{
		public void channelConnected(
				ChannelHandlerContext ctx,
				ChannelStateEvent e
				) {
			System.out.println("Hello world,im client");
		}
	}*/
	
	public static class ObjectClientHandler extends SimpleChannelHandler{
		 public void channelConnected(ChannelHandlerContext ctx,ChannelStateEvent e) {
			 sendObject(e.getChannel());
		 }
		 private void sendObject(Channel channel) {
			 
			 Command command = new Command();
			 command.setActionName("链接成功");
			 channel.write(command);
		 }
		
	}

}
