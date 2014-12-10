package org.xlet.upgrader.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

/**
 * Creator: JimmyLin
 * DateTime: 14-11-6 下午4:59
 * Summary:
 */
public class CommonTest {

    @Test
    public void listFiles(){
        Collection<File> files = FileUtils.listFiles(new File("E://work/products/"), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        for(File f: files){
            System.out.println(f.getAbsolutePath()+"\t"+f.lastModified()+"\t"+f.length());
        }
    }

    @Test
    public void test(){
        System.out.println(2&4);

    }
}
