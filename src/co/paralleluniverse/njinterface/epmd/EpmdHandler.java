/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pron
 */
public class EpmdHandler extends SimpleChannelUpstreamHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EpmdHandler.class);
    private final Queue<EpmdResponse> queue = new ConcurrentLinkedQueue<EpmdResponse>();

    EpmdResponse response() {
        final EpmdResponse call = new EpmdResponse();
        queue.add(call);
        return call;
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LOG.debug("Oh snap channel closed.");
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LOG.debug("Uh oh disconnect.");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        for (EpmdResponse rsp = queue.poll(); rsp != null; rsp = queue.poll())
            rsp.setError(e.getCause());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        final EpmdMessage response = (EpmdMessage)e.getMessage();
        for (EpmdResponse rsp = queue.poll(); rsp != null; rsp = queue.poll())
            rsp.set(response);
    }

    class EpmdResponse implements Callable<EpmdMessage> {

        private final AtomicReference<EpmdMessage> response = new AtomicReference<EpmdMessage>();
        private final AtomicReference<Throwable> error = new AtomicReference<Throwable>();
        private final CountDownLatch lock = new CountDownLatch(1);

        public void setError(Throwable t) {
            error.set(t);
            lock.countDown();
        }

        public void set(EpmdMessage v) {
            response.set(v);
            lock.countDown();
        }

        @Override
        public EpmdMessage call() throws InterruptedException {
            if (lock.await(5000, TimeUnit.MILLISECONDS)) {
                if (error.get() != null)
                    throw new RuntimeException("EPMD Registration failed.", error.get());
                else
                    return response.get();
            } else
                throw new RuntimeException("EPMD Registration timed out.");
        }

    }

}
