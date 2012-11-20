/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class EpmdEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        final ChannelBufferOutputStream bout = new ChannelBufferOutputStream(ChannelBuffers.dynamicBuffer(24, ctx.getChannel().getConfig().getBufferFactory()));
        bout.writeShort(0); //length placeholder
        if (msg instanceof EpmdMessage.AliveReq) {
            final EpmdMessage.AliveReq m = (EpmdMessage.AliveReq) msg;
            bout.writeByte(120);
            bout.writeShort(m.getPortNo());
            bout.writeByte(EpmdConst.ntypeR6.value); //node type
            bout.writeByte(0); //protocol
            bout.writeShort(5); // highest version
            bout.writeShort(5); // lowest version
            bout.writeShort(m.getNodeName().length()); // name length
            bout.writeBytes(m.getNodeName()); // name
            bout.writeShort(0); //extra len
        } else if (msg instanceof EpmdMessage.PortPleaseReq) {
            final EpmdMessage.PortPleaseReq m = (EpmdMessage.PortPleaseReq) msg;
            bout.writeByte(122);
            bout.writeBytes(m.getNodeName());
        }
        final ChannelBuffer encoded = bout.buffer();
        encoded.setShort(0, encoded.writerIndex() - 2);
        return encoded;
    }

}
