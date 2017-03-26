package simpledb.buffer;
import java.util.*;
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
	/**
	    * Apply the same constructor with the BasicBufferMgr
	    * Construct a buffer with defined number of buffers.
	    * @param number of buffers in the buffer poll.
	    */
	public BasicBufferMgrLRU(int numBuffs){
		super(numBuffs);
		bufferPool = super.bufferpool;
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
		Arrays.sort(bufferPool, Buffer.BufferUnpinedTimeComparator);
		int i = 0;
		for (Buffer buff : bufferPool){
			
			if (buff.isNeverUsed()){
				return buff;
			} 
			
			if (!bufferPool[i].isPinned()){
					return bufferPool[i];
				}
			i++;
			}
		
		
		return null;
	}
		
		
	
	      
	
	
	
}
