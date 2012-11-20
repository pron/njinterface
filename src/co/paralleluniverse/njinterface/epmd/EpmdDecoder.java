/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class EpmdDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 1)
            return null;
        final byte header = buffer.getByte(0);
        switch (header) {
            case 121: {//decode alive2 resp
                if (buffer.readableBytes() < 4)
                    return null;
                final byte result = buffer.getByte(1);
                final int creation = buffer.getUnsignedShort(2);
                buffer.skipBytes(4);
                return new EpmdMessage.AliveResp(result, creation);
            }
            case 119: { //decode port2 resp
                final byte result = buffer.getByte(1);
                if(result > 0) {
                    buffer.skipBytes(2);
                    return new EpmdMessage.PortPleaseError(result);
                } else {
                    if(buffer.readableBytes() < 12)
                        return null;
                    final int nlen = buffer.getUnsignedShort(10);
                    if(buffer.readableBytes() < (14 + nlen))
                        return null;
                    final int elen = buffer.getUnsignedShort(12 + nlen);
                    if(buffer.readableBytes() < (14 + nlen + elen))
                        return null;
                    final int portNo = buffer.getUnsignedShort(2);
                    final byte[] bytes = new byte[nlen];
                    buffer.getBytes(12, bytes);
                    final String nodeName = new String(bytes);
                    buffer.skipBytes(14 + nlen + elen);
                    return new EpmdMessage.PortPleaseResp(portNo, nodeName);
                }
            }
            default:
                return null;
        }
    }

}
