package simpledb.buffer;
import java.util.PriorityQueue;
import java.util.Queue;
import simpledb.file.*;
import simpledb.server.SimpleDB;
/**
 * Manages the pinning and unpinning of buffers to blocks
 * by applying the Least Recently Used algorithm(LRU)
 * @author Yupeng Su
 *
 */

public class BasicBufferMgrLRU extends BasicBufferMgr {
	private Buffer[] bufferPool;
	private int numAvailable;
	private int neverUsed;
	public Queue<Buffer> buffers;
	/**
	    * Apply the same constructor with the BasicBufferMgr
	    * Construct a buffer with defined number of buffers.
	    * @param number of buffers in the buffer poll.
	    */
	public BasicBufferMgrLRU(int numBuffs){
		super(numBuffs);
		bufferPool = super.bufferpool;
		buffers = new PriorityQueue<Buffer>(numBuffs, Buffer.BufferUnpinedTimeComparator);
		for (int i = 0; i < numBuffs; i++){
			buffers.add(new Buffer());
		}
	}
	
	@Override
	synchronized Buffer pin(Block blk) {
		  
	      Buffer buff = findExistingBuffer(blk);
	      if (buff == null) {
	         buff = chooseUnpinnedBuffer();
	         System.out.println(buff);
	         if (buff == null)
	            return null;
	         long timestamp = System.currentTimeMillis();
	         buff.assignToBlock(blk);
	         buff.tagPinedTimeStamp(timestamp);
	      }
	      
	      if (!buff.isPinned())
	         numAvailable--;
	      buff.pin();
	      return buff;
	   }
	
	@Override
	synchronized void unpin(Buffer buff) {
	      buff.unpin();
	      long timestamp = System.currentTimeMillis();
	      buff.tagUnPinedTimeStamp(timestamp);
	      
	      if (!buff.isPinned())
	         numAvailable++;
	   }
  
	private Buffer findExistingBuffer(Block blk) {
		  
	      for (Buffer buff : bufferPool) {
	         Block b = buff.block();
	         if (b != null && b.equals(blk))
	            return buff;
	      }
	      return null;
	   }
	
	
	private Buffer chooseUnpinnedBuffer() {
		// check free buffer first
		for (Buffer buff : buffers){
			if (buff.isNeverUsed()){
				return buff;
			}else{
				if(buff.isPinned()){
					continue;
				}else{
					return buff;
				}
			}
		}
		return null;
	}
}
