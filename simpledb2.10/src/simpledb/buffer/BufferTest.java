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
	public void testLRu(){
		Block blk1 = new Block("first",1);
		Block blk2 = new Block("second",2);
		Block blk3 = new Block("Third",3);
		Block blk4 = new Block("Fourth",4);
		Block blk5 = new Block("Fifth", 5);
		Block blk6 = new Block("Sixth", 6);
		BufferMgrLRU bmgr = new BufferMgrLRU(4);
		BufferLRU buff1 = bmgr.pin(blk1);
		BufferLRU buff2 = bmgr.pin(blk2);
		BufferLRU buff3 = bmgr.pin(blk3);
//		BufferLRU buff4 = bmgr.pin(blk4);
//		BufferLRU buff5 = bmgr.pin(blk5);
		bmgr.unpin(buff1);
		BufferLRU buff5 = bmgr.pin(blk5);		
		BufferLRU buff4 = bmgr.pin(blk4);
		bmgr.unpin(buff2);
		BufferLRU buff6 = bmgr.pin(blk6);
		System.out.println(bmgr.buffers.get(2));
		System.out.println(bmgr.buffers.size());
	}
}
