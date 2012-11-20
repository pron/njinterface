/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

import co.paralleluniverse.njinterface.epmd.EpmdMessage.AliveReq;
import co.paralleluniverse.njinterface.epmd.EpmdMessage.AliveResp;
import co.paralleluniverse.njinterface.epmd.EpmdMessage.PortPleaseError;
import co.paralleluniverse.njinterface.epmd.EpmdMessage.PortPleaseReq;
import co.paralleluniverse.njinterface.epmd.EpmdMessage.PortPleaseResp;
import com.google.common.base.Throwables;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class Epmd {

    public static final int DEFAULT_PORT = 4369;
    private final String host;
    private final int port;
    private final ClientBootstrap bootstrap;
    private final EpmdHandler handler;
    private final Channel channel;

    public Epmd(String host) {
        this(host, Integer.parseInt(System.getenv("ERL_EPMD_PORT")));
    }

    public Epmd(String host, int port) {
        this.host = host;
        this.port = port;

        this.bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        this.handler = new EpmdHandler();

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EpmdEncoder(), new EpmdDecoder(), handler);
            }

        });

        final ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(host, port));
        this.channel = connectFuture.awaitUninterruptibly().getChannel();
        if (!connectFuture.isSuccess())
            throw Throwables.propagate(connectFuture.getCause());
    }

    public void close() {
        channel.close();
    }

    public int alive(int portNo, String nodeName) throws InterruptedException {
        channel.write(new AliveReq(portNo, nodeName));
        final AliveResp resp = (AliveResp) handler.response().call();
        if (resp.getResult() == 0)
            return resp.getCreation();
        else
            throw new RuntimeException("Epmd response: " + resp.getResult());
    }

    public int lookupPort(String nodeName) throws InterruptedException {
        channel.write(new PortPleaseReq(nodeName));
        final EpmdMessage resp = handler.response().call();
        if (resp instanceof PortPleaseResp)
            return ((PortPleaseResp) resp).getPortNo();
        if (resp instanceof PortPleaseError)
            throw new RuntimeException("Epmd error: " + ((PortPleaseError) resp).getResult());
        throw new RuntimeException("Unknown response: " + resp);
    }

}
