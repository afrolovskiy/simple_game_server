package ru.afrolovskiy.vfs.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

import ru.afrolovskiy.base.VFS;
import ru.afrolovskiy.vfs.VFSImpl;

public class VFSImplTests {

	@Test
	public void test() {
		VFS vfs = new VFSImpl("src/ru/afrolovskiy/vfs/tests/");
		assertTrue(vfs.getAbsolutePath("test.txt")
				      .endsWith("src\\ru\\afrolovskiy\\vfs\\tests\\test.txt"));
		assertFalse(vfs.isDirectory("test.txt"));
		assertTrue(vfs.isExist("test.txt"));
		assertEquals(vfs.getBytes("test.txt").length, 
				(new File(vfs.getAbsolutePath("test.txt"))).length());
		assertEquals(vfs.getUTF8Text("test.txt"), "Test File\nTest File");
	}
	
	@Test
	public void testFileIterator() {
		VFS vfs = new VFSImpl("src/ru/afrolovskiy/vfs/tests/");
		Iterator<String> iter = vfs.getIterator("");
		int count = 0;
		while (iter.hasNext()) {			
			iter.next();
			count++;
		}
		assertEquals(count, 8);
	}

}
