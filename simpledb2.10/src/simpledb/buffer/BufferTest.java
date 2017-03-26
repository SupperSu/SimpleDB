package simpledb.buffer;
import static org.junit.Assert.*;

import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import simpledb.file.Block;
import simpledb.file.FileMgr;
import simpledb.file.Page;
import simpledb.server.SimpleDB;

public class BufferTest {

	@Test
	public void testSortBuffer (){
		
		Buffer[] bufferPool = new Buffer[3];
		for (int i = 0; i < 3; i++){
			bufferPool[i] = new Buffer();
		}
		bufferPool[0].tagUnPinedTimeStamp(System.currentTimeMillis());
		bufferPool[1].tagUnPinedTimeStamp(96);
		for (Buffer buff : bufferPool){
			System.out.println(buff.getUnPinedTime());
		}
		Arrays.sort(bufferPool, Buffer.BufferUnpinedTimeComparator);

		for (Buffer buff : bufferPool){
			System.out.println(buff.getUnPinedTime());
		}
	}

	@Test
	public void testFIFO(){
		Block blk1 = new Block("first",0);
		Block blk2 = new Block("second",1);
		Block blk3 = new Block("Third",2);
		Block blk4 = new Block("Fourth",3);
		Block blk5 = new Block("Fifth", 4);

		BasicBufferMgrLRU bmgr = new BasicBufferMgrLRU(4);
		Buffer buff1 = bmgr.pin(blk1);
		Buffer buff2 = bmgr.pin(blk2);
		Buffer buff3 = bmgr.pin(blk3);
		Buffer buff4 = bmgr.pin(blk4);
		
		System.out.println("*****************");
		bmgr.unpin(buff2);
		bmgr.unpin(buff1);
		bmgr.unpin(buff4);
		bmgr.pin(blk5);
		for(int i = 0; i < 4; i++){
			System.out.println(bmgr.bufferpool[i].getUnPinedTime());
		}
		

	}
}
