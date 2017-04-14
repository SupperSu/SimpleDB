package simpledb.buffer;

import simpledb.file.*;

import java.util.LinkedHashMap;
import java.util.Map;

class FIFO extends AbstractBufferMgr{
   private static Map<Block, BufferLRU> bufferpool = new LinkedHashMap<Block, BufferLRU>();
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
   protected synchronized void flushAll(int txnum) {
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
   protected synchronized Buffer pin(Block blk) {
      BufferLRU buff = findExistingBuffer(blk);
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         System.out.println(buff);
         if (buff == null)
            return null;
         bufferpool.remove(buff.block());
         long timestamp = System.currentTimeMillis();
         buff.assignToBlock(blk);
         buff.setPinnedTime(timestamp);
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
   protected synchronized BufferLRU pinNew(String filename, PageFormatter fmtr) {
      BufferLRU buff = chooseUnpinnedBuffer();
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
   protected void unpin(BufferLRU buff) {
      buff.unpin();
      long timestamp = System.currentTimeMillis();
      buff.setUnPinnedTime(timestamp);
      if (!buff.isPinned())
         numAvailable++;
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   protected int available() {
      return numAvailable;
   }
   
   protected BufferLRU findExistingBuffer(Block blk) {
	  if (bufferpool.containsKey(blk)) {
		  return bufferpool.get(blk);
	  }
      return null;
   }
   
   protected BufferLRU chooseUnpinnedBuffer() {
	   if (numAvailable > 0) {
		   if (bufferpool.size() < bufferPoolSize) {
			   BufferLRU buff = new BufferLRU();
			   return buff;
		   }
		   for (Map.Entry<Block, BufferLRU> entry : bufferpool.entrySet()) {
			   if (!entry.getValue().isPinned()) {
				   return entry.getValue();
			   } 
	       }
	   }
	   return null;
   }

@Override
protected void unpin(Buffer buff) {
	// TODO Auto-generated method stub
	
}
}
