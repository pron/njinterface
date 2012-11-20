/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

public class EpmdMessage {

    public static class AliveReq extends EpmdMessage {

        private final int portNo;
        private final String nodeName;

        public AliveReq(int portNo, String nodeName) {
            this.portNo = portNo;
            this.nodeName = nodeName;
        }

        public int getPortNo() {
            return portNo;
        }

        public String getNodeName() {
            return nodeName;
        }

    }

    public static class AliveResp extends EpmdMessage {

        private final int result;
        private final int creation;

        public AliveResp(int result, int creation) {
            this.result = result;
            this.creation = creation;
        }

        public int getResult() {
            return result;
        }

        public int getCreation() {
            return creation;
        }

    }

    public static class PortPleaseReq extends EpmdMessage {

        private final String nodeName;

        public PortPleaseReq(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getNodeName() {
            return nodeName;
        }

    }

    public static class PortPleaseError extends EpmdMessage {

        private final int result;

        public PortPleaseError(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }

    }

    public static class PortPleaseResp extends EpmdMessage {

        private final int portNo;
        private final String nodeName;

        public PortPleaseResp(int portNo, String nodeName) {
            this.portNo = portNo;
            this.nodeName = nodeName;
        }

        public int getPortNo() {
            return portNo;
        }

        public String getNodeName() {
            return nodeName;
        }

    }

}
