package simpledb.buffer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import simpledb.file.Block;

public class BufferMgrLRU extends AbstractBufferMgr {
	protected Map<Block, BufferLRU> buffers;
	
	protected BufferMgrLRU(int numOfBuffers) {
		super(numOfBuffers);
		buffers = new LinkedHashMap<Block, BufferLRU>();
	}

	@Override
	protected void flushAll(int txnm) {
		for (Block blk : buffers.keySet()){
			buffers.get(blk).flush();
		}
	}

	@Override
	protected synchronized BufferLRU pin(Block blk) {
		BufferLRU buff = findExistingBuffer(blk);
		if (buff == null){
			buff = chooseUnpinnedBuffer();
			if (buff == null){
				return null;
			}
			buffers.remove(buff.block());
			buff.assignToBlock(blk);	
			buffers.put(blk, buff);
		}
		if (!buff.isPinned())
	         numAvailable--;
		  //why pin several times?
	      buff.pin();
	      return buff;
	}

	@Override
	protected synchronized BufferLRU pinNew(String filename, PageFormatter fmtr) {
		BufferLRU buff = chooseUnpinnedBuffer();
	      if (buff == null)
	         return null;
	      buff.assignToNew(filename, fmtr);
	      buffers.put(buff.block(), buff);
	      numAvailable--;
	      buff.pin();
	      return buff;
	}
	
	@Override
	protected synchronized void unpin(Buffer buff) {
		buff.unpin();
	    if (!buff.isPinned()){
	    	numAvailable++;
	    }
	}
	
	protected synchronized void unpin(BufferLRU buff){
		buff.unpin();
		long time = System.currentTimeMillis();
		buff.setUnPinnedTime(time);
		if (!buff.isPinned()){
			numAvailable++;
		}
	}

	@Override
	protected int available() {
		return numAvailable;
	}

	@Override
	protected BufferLRU findExistingBuffer(Block blk) {
		if (buffers.containsKey(blk)){
			return buffers.get(blk);
		}
		return null;
	}

	@Override
	protected BufferLRU chooseUnpinnedBuffer() {
		long earlyTime = Long.MAX_VALUE;
		Block mark = null;
		if (buffers.size() < super.poolCapacity){
			BufferLRU temp = new BufferLRU();
			return temp;
		}
		
		for (Block blk : buffers.keySet()){
			if (!buffers.get(blk).isPinned()) {
				if (buffers.get(blk).getUnPinnedTime() < earlyTime){
					earlyTime = buffers.get(blk).getUnPinnedTime();
					mark = blk;
				}
			}
		}
		if (buffers.containsKey(mark)){
			return buffers.get(mark);
		}else{
			return null;
		}
	}
	
	private boolean hasFreeBuffer(){
		if (this.buffers.size() < super.poolCapacity){
			return true;
		}else{
			return false;
		}
	}
}
