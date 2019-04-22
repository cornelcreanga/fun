package com.ccreanga.various.tries;

import static com.ccreanga.various.util.FormatUtil.readableSize;

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.sux4j.util.ZFastTrie;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import org.ehcache.sizeof.SizeOf;

public class TriesSizeBenchmark {

    public static void main(String[] args) throws Exception {
        SizeOf sizeOf = SizeOf.newInstance();
        List<String> lines = java.nio.file.Files.readAllLines(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()), Charset.forName("utf-8"));
        ZFastTrie<String> trie = new ZFastTrie<>(TransformationStrategies.utf16());
        trie.addAll(lines);
        long compressedSize = sizeOf.deepSizeOf(trie);
        System.out.println(readableSize(compressedSize));


    }

}
