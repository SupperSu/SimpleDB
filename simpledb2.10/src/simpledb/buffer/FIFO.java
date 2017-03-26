package simpledb.buffer;

import simpledb.file.*;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
class FIFO extends BasicBufferMgr{
   private static Map<Block, Buffer> bufferpool = new LinkedHashMap<Block, Buffer>();
   private int numAvailable;
   private int bufferPoolSize;
   
   /**
    * Creates a buffer manager having the specified number 
    * of buffer slots.
    * This constructor depends on both the {@link FileMgr} and
    * {@link simpledb.log.LogMgr LogMgr} objects 
    * that it gets from the class
    * {@link simpledb.server.SimpleDB}.
    * Those objects are created during system initialization.
    * Thus this constructor cannot be called until 
    * {@link simpledb.server.SimpleDB#initFileAndLogMgr(String)} or
    * is called first.
    * @param numbuffs the number of buffer slots to allocate
    */
   FIFO(int numbuffs) {
      super(numbuffs);
      numAvailable = numbuffs;
      bufferPoolSize = numbuffs;
   }
   
   /**
    * Flushes the dirty buffers modified by the specified transaction.
    * @param txnum the transaction's id number
    */
   synchronized void flushAll(int txnum) {
      for (Buffer buff : bufferpool.values())
         if (buff.isModifiedBy(txnum))
         buff.flush();
   }
   
   /**
    * Pins a buffer to the specified block. 
    * If there is already a buffer assigned to that block
    * then that buffer is used;  
    * otherwise, an unpinned buffer from the pool is chosen.
    * Returns a null value if there are no available buffers.
    * @param blk a reference to a disk block
    * @return the pinned buffer
    */
   synchronized Buffer pin(Block blk) {
      Buffer buff = findExistingBuffer(blk);
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         System.out.println(buff);
         if (buff == null)
            return null;
         bufferpool.remove(buff.block());
         long timestamp = System.currentTimeMillis();
         buff.assignToBlock(blk);
         buff.tagPinedTimeStamp(timestamp);
      }
      if (!buff.isPinned())
         numAvailable--;
      buff.pin();
      bufferpool.put(blk, buff);
      return buff;
   }
   
   /**
    * Allocates a new block in the specified file, and
    * pins a buffer to it. 
    * Returns null (without allocating the block) if 
    * there are no available buffers.
    * @param filename the name of the file
    * @param fmtr a pageformatter object, used to format the new block
    * @return the pinned buffer
    */
   synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
      Buffer buff = chooseUnpinnedBuffer();
      if (buff == null)
         return null;
      bufferpool.remove(buff.block());
      buff.assignToNew(filename, fmtr);
      numAvailable--;
      buff.pin();
      bufferpool.put(buff.block(), buff);
      return buff;
   }
   
   /**
    * Unpins the specified buffer.
    * @param buff the buffer to be unpinned
    */
   synchronized void unpin(Buffer buff) {
      buff.unpin();
      long timestamp = System.currentTimeMillis();
      buff.tagUnPinedTimeStamp(timestamp);
      if (!buff.isPinned())
         numAvailable++;
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   int available() {
      return numAvailable;
   }
   
   private Buffer findExistingBuffer(Block blk) {
	  if (bufferpool.containsKey(blk)) {
		  return bufferpool.get(blk);
	  }
      return null;
   }
   
   private Buffer chooseUnpinnedBuffer() {
	   if (numAvailable > 0) {
		   if (bufferpool.size() < bufferPoolSize) {
			   Buffer buff = new Buffer();
			   return buff;
		   }
		   for (Map.Entry<Block, Buffer> entry : bufferpool.entrySet()) {
			   if (!entry.getValue().isPinned()) {
				   return entry.getValue();
			   } 
	       }
	   }
	   return null;
   }
   
}
