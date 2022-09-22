package com.techzealot.collection.tree.trie;

import java.util.Map;

public class GenericCharTrie<E> {

    private class Node {
        //结尾次数
        int end;
        //经过次数
        int pass;
        //more fields...
        Map<Character, Node> next;
    }
}
