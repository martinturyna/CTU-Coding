class Homework4 extends MessageVisitor {

    public Homework4(Peer peer) {
        super(peer);
    }

    @Override
    boolean visitHaveMessage(HaveMessage message) {
        peer.peers2BlocksMap.get(message.sender)[message.blockIndex] = true;
        return false;
    }

    @Override
    boolean visitRequestMessage(RequestMessage message) {
        if (peer.data[message.blockIndex] != null) {
            message.sender.piece(peer, message.blockIndex, peer.data[message.blockIndex]);
        }
        return true;
    }

    @Override
    boolean visitPieceMessage(PieceMessage message) {
        peer.data[message.blockIndex] = message.data;
        peer.downloadedBlocksCount++;
        for (PeerInterface p : peer.peers2BlocksMap.keySet()) {
            p.have(peer, message.blockIndex);
        }
        if (peer.downloadedBlocksCount == peer.totalBlocksCount) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    boolean visitIdleMessage(IdleMessage message) {
        PeerInterface[] peers = new PeerInterface[peer.totalBlocksCount];
        int[] frequency = new int[peer.totalBlocksCount];
        for (PeerInterface p: peer.peers2BlocksMap.keySet()) {
            boolean[] boolArr = peer.peers2BlocksMap.get(p);
            int i = 0;
            for (boolean b: boolArr) {
                if(b){
                    frequency[i]++;
                    peers[i] = p;
                }
                i++;
            }
        }
        int x = 0, minVal = 2000000000;
        for(int i = 0; i < frequency.length; i++) {
            if (frequency[i] < minVal) {
                minVal = frequency[i];
                x = i;
            }
        }
        peers[x].request(peer, x);
        return false;
    }
}
