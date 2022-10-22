package com.ccreanga.various.tries;

import static com.ccreanga.various.util.FormatUtil.readableSize;

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.bits.TransformationStrategy;
import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.io.FastBufferedReader;
import it.unimi.dsi.io.LineIterator;
import it.unimi.dsi.lang.MutableString;
import it.unimi.dsi.logging.ProgressLogger;
import it.unimi.dsi.sux4j.util.ZFastTrie;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.ehcache.sizeof.SizeOf;

public class TriesSizeBenchmark {

    public static void main(String[] args) throws Exception {
        SizeOf sizeOf = SizeOf.newInstance();

        final InputStream inputStream = TriesSizeBenchmark.class.getClassLoader().getResourceAsStream("words.txt");
        Iterator<MutableString> lineIterator = new LineIterator(new FastBufferedReader(new InputStreamReader(inputStream, "UTF-8")));
        //if (jsapResult.userSpecified("loadAll"))
        lineIterator = ((LineIterator) lineIterator).allLines().iterator();

        final TransformationStrategy<CharSequence> transformationStrategy = TransformationStrategies.prefixFreeIso();

        final ProgressLogger pl = new ProgressLogger();
        pl.displayLocalSpeed = true;
        pl.displayFreeMemory = true;
        pl.itemsName = "keys";
        pl.start("Adding keys...");

        final ZFastTrie<CharSequence> zFastTrie = new ZFastTrie<>(transformationStrategy);
        while (lineIterator.hasNext()) {
            zFastTrie.add(lineIterator.next().copy());
            pl.lightUpdate();
        }
        pl.done();
        long compressedSize = sizeOf.deepSizeOf(zFastTrie);
        System.out.println(readableSize(compressedSize));

        BinIO.storeObject(zFastTrie, "/home/cornel/tree.bin");

    }

}
