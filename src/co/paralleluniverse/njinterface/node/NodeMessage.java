/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.node;

import co.paralleluniverse.njinterface.Pid;
import co.paralleluniverse.njinterface.Reference;

/**
 *
 * @author pron
 */
public class NodeMessage {
    public static class LinkMessage extends NodeMessage {
        private final Pid from;
        private final Pid to;

        public LinkMessage(Pid from, Pid to) {
            this.from = from;
            this.to = to;
        }

        public Pid getFrom() {
            return from;
        }

        public Pid getTo() {
            return to;
        }
    }

    public static class SendMessage extends NodeMessage {
        private final Pid to;
        private final Object msg;

        public SendMessage(Pid to, Object msg) {
            this.to = to;
            this.msg = msg;
        }

        public Pid getTo() {
            return to;
        }

        public Object getMsg() {
            return msg;
        }
    }

    public static class ExitMessage extends NodeMessage {
        private final Pid from;
        private final Pid to;
        private final Object reason;

        public ExitMessage(Pid from, Pid to, Object reason) {
            this.from = from;
            this.to = to;
            this.reason = reason;
        }

        public Pid getFrom() {
            return from;
        }

        public Pid getTo() {
            return to;
        }

        public Object getReason() {
            return reason;
        }
    }

    public static class Exit2Message extends NodeMessage {
        private final Pid from;
        private final Pid to;
        private final Object reason;

        public Exit2Message(Pid from, Pid to, Object reason) {
            this.from = from;
            this.to = to;
            this.reason = reason;
        }

        public Pid getFrom() {
            return from;
        }

        public Pid getTo() {
            return to;
        }

        public Object getReason() {
            return reason;
        }
    }

    public static class UnlinkMessage extends NodeMessage {
        private final Pid from;
        private final Pid to;

        public UnlinkMessage(Pid from, Pid to) {
            this.from = from;
            this.to = to;
        }

        public Pid getFrom() {
            return from;
        }

        public Pid getTo() {
            return to;
        }
    }

    public static class RegSend extends NodeMessage {
        private final Pid from;
        private final String to;
        private final Object msg;

        public RegSend(Pid from, String to, Object msg) {
            this.from = from;
            this.to = to;
            this.msg = msg;
        }

        public Pid getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public Object getMsg() {
            return msg;
        }
    }

    public static class Tick extends NodeMessage {
    }

    public static class Tock extends NodeMessage {
    }

    public static class MonitorMessage extends NodeMessage {
        private final Pid monitoring;
        private final Object monitored;
        private final Reference ref;

        public MonitorMessage(Pid monitoring, Object monitored, Reference ref) {
            this.monitoring = monitoring;
            this.monitored = monitored;
            this.ref = ref;
        }

        public Pid getMonitoring() {
            return monitoring;
        }

        public Object getMonitored() {
            return monitored;
        }

        public Reference getRef() {
            return ref;
        }
    }

    public static class DemonitorMessage extends NodeMessage {
        private final Pid monitoring;
        private final Object monitored;
        private final Reference ref;

        public DemonitorMessage(Pid monitoring, Object monitored, Reference ref) {
            this.monitoring = monitoring;
            this.monitored = monitored;
            this.ref = ref;
        }

        public Pid getMonitoring() {
            return monitoring;
        }

        public Object getMonitored() {
            return monitored;
        }

        public Reference getRef() {
            return ref;
        }
    }

    public static class MonitorExit extends NodeMessage {
        private final Pid monitoring;
        private final Object monitored;
        private final Reference ref;
        private final Object reason;

        public MonitorExit(Object monitored, Pid monitoring, Reference ref, Object reason) {
            this.monitoring = monitoring;
            this.monitored = monitored;
            this.ref = ref;
            this.reason = reason;
        }

        public Pid getMonitoring() {
            return monitoring;
        }

        public Object getMonitored() {
            return monitored;
        }

        public Reference getRef() {
            return ref;
        }

        public Object getReason() {
            return reason;
        }
    }

    public static class DistributedProtocolException extends Exception {
        public DistributedProtocolException(String msg) {
            super(msg);
        }
    }
}
