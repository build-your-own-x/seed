package com.techzealot.collection.tree.trie;

import java.util.Map;

public class VariantCharTrie<V> implements CharTrie<V> {

    private class Node {
        //结尾次数
        int end;
        //经过次数
        int pass;
        V v;
        //more fields...
        Map<Character, Node> next;
    }
}
